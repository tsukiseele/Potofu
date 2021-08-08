package com.tsukiseele.potofu.services.impl

import com.tsukiseele.potofu.helper.Token
import com.tsukiseele.potofu.helper.TokenPool
import com.tsukiseele.potofu.mappers.UserMapper
import com.tsukiseele.potofu.models.User
import com.tsukiseele.potofu.utils.CookieUtil
import com.tsukiseele.potofu.utils.SecurityUtil
import com.tsukiseele.potofu.services.UserService
import com.tsukiseele.utils.IpUtil
import com.tsukiseele.utils.TextUtil
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class UserServiceImpl
@Autowired constructor(private val userMapper: UserMapper) : UserService {
    override fun register(user: User, request: HttpServletRequest?): Int {
        if (TextUtil.isNullOrEmptyOfAll(user.userName, user.userEmail, user.userPassword)) return 0
        // 对密码的Base64进行Md5加密
        val passwordHash = SecurityUtil.getPasswordHash(user.userPassword)
        user.userPassword = passwordHash
        user.userLastLoginIp = request?.let { IpUtil.clientIp(it) }
        user.userLastLoginTime = Date()
        return userMapper.insert(user)
    }

    override fun login(loginUser: User, request: HttpServletRequest): Any {
        val loginEmail = loginUser.userEmail
        val loginPassword = loginUser.userPassword
        if (loginEmail.isNullOrEmpty() || loginPassword.isNullOrEmpty())
            return HttpStatus.BAD_REQUEST
        // 对密码的Base64进行Md5加密
        val password = DigestUtils.md5Hex(Base64.getEncoder().encode(loginPassword.toByteArray()))
        // 验证用户
        val user = userMapper.queryOne(loginEmail, password)
        if (user == null)
            return HttpStatus.UNAUTHORIZED

        return user.let {
            it.userLastLoginIp = IpUtil.clientIp(request)
            it.userLastLoginTime = Date()
            userMapper.update(it)
            // 移除旧 token
            val oldToken = CookieUtil.getCookie(request, "token")
            if (TextUtil.isNotNullOrEmpty(oldToken)) TokenPool.remove(oldToken)
            // 生成并添加新 token
            val token = Token.Builder()
                    .bind(user)
                    .add("date", System.currentTimeMillis().toString())
                    .add("email", it.userEmail)
                    .add("password", password)
                    .setKeepTimeMinute(720)
                    .build()
            TokenPool.add(token)
            // 响应
            mutableMapOf(Pair("user", user), Pair("token", token.toString()))
        }
    }

    override fun auth(request: HttpServletRequest): Any? {
        val token = CookieUtil.getCookie(request, "token")
        if (TokenPool.exists(token)) {
            val tk: Token = TokenPool.get(token) ?: return null
            // 如果需要更高的安全性，建议查询数据库对比，这里优化性能直接返回数据
            return tk.user
        } else {
            return null
        }
    }
}