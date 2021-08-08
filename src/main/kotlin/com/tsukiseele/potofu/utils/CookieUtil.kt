package com.tsukiseele.potofu.utils

import javax.servlet.http.HttpServletRequest

object CookieUtil {
    fun getCookie(request: HttpServletRequest, key: String): String? {
        val cookies = request.cookies
        if (cookies != null) for (cookie in cookies) if (cookie.name == key) return cookie.value
        return null
    }
}