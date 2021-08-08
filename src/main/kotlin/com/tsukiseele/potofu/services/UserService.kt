package com.tsukiseele.potofu.services

import com.tsukiseele.potofu.models.User
import javax.servlet.http.HttpServletRequest

interface UserService {
    fun register(user: User, request: HttpServletRequest?): Any?
    fun login(user: User, request: HttpServletRequest): Any?
    fun auth(request: HttpServletRequest): Any?
}