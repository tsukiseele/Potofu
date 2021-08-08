package com.tsukiseele.potofu.controllers

import com.tsukiseele.potofu.helper.R
import com.tsukiseele.potofu.models.User
import com.tsukiseele.potofu.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class UserController
@Autowired constructor(private val userService: UserService) {
    @PostMapping("/login")
    fun login(@RequestBody user: User, request: HttpServletRequest): ResponseEntity<*> {
        userService.login(user, request).also {
            if (it is HttpStatus) {
                return when (it) {
                    HttpStatus.BAD_REQUEST -> R.badRequest().message("登录失败：用户名和密码不能为空")
                    HttpStatus.UNAUTHORIZED -> R.unauth().message("登录失败：用户名或密码错误")
                    else -> R.badRequest().message("登录失败")
                }
            } else {
                return R.ok().data(it)
            }
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody user: User, request: HttpServletRequest?): ResponseEntity<*> {
        try {
            if (userService.register(user, request) == 1) return R.ok().message("注册成功")
        } catch (e: Exception) {
            return R.badRequest().message("注册失败")
        }
        return R.notFound().message("暂不开通注册功能")
    }

    @PostMapping("/auth")
    fun auth(request: HttpServletRequest): ResponseEntity<*> {
        return userService.auth(request).let {
            if (it != null) {
                R.ok().data(it)
            } else {
                R.unauth().message("失效的登录信息，请重新登录")
            }
        }
    }
}