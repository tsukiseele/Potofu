package com.tsukiseele.potofu.services

import com.tsukiseele.potofu.models.Visitor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface VisitorService {
    fun create(visitor: Visitor, request: HttpServletRequest?, response: HttpServletResponse): Int
    fun getVisitorById(visitorId: Int): Visitor
}