package com.tsukiseele.potofu.services.impl

import com.tsukiseele.potofu.mappers.VisitorMapper
import com.tsukiseele.potofu.models.Visitor
import com.tsukiseele.potofu.services.VisitorService
import com.tsukiseele.utils.IpUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class VisitorServiceImpl
@Autowired constructor(private val visitorMapper: VisitorMapper) : VisitorService {
    override fun getVisitorById(visitorId: Int): Visitor {
        return visitorMapper.queryVisitorById(visitorId)
    }

    override fun create(visitor: Visitor, request: HttpServletRequest?, response: HttpServletResponse): Int {
        visitor.visitorIp = request?.let { IpUtil.clientIp(request) }
        visitor.visitorDatetime = Date()
        response.addCookie(Cookie("visitor_nickname", visitor.visitorName))
        response.addCookie(Cookie("visitor_email", visitor.visitorEmail))
        return visitorMapper.insert(visitor)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(VisitorServiceImpl::class.java)
    }
}