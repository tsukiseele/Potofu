package com.tsukiseele.potofu.controllers

import com.tsukiseele.potofu.helper.*
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
                    HttpStatus.BAD_REQUEST -> badRequest("登录失败：用户名和密码不能为空")
                    HttpStatus.UNAUTHORIZED -> unauthorized("登录失败：用户名或密码错误")
                    else -> badRequest("登录失败")
                }
            } else {
                return it.ok()
            }
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody user: User, request: HttpServletRequest?): ResponseEntity<*> {
        try {
            if (userService.register(user, request) == 1) return ok("注册成功")
        } catch (e: Exception) {
            return badRequest("注册失败")
        }
        return notFound("暂不开通注册功能")
    }

    @PostMapping("/auth")
    fun auth(request: HttpServletRequest): ResponseEntity<*> {
        return userService.auth(request).let {
            it?.ok() ?: unauthorized("失效的登录信息，请重新登录")
        }
    }
}