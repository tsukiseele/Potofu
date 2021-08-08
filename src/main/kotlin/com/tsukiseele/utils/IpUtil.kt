package com.tsukiseele.utils

import org.slf4j.LoggerFactory
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*
import javax.servlet.http.HttpServletRequest

object IpUtil {
    private val logger = LoggerFactory.getLogger(IpUtil::class.java)

    /**
     * 获取的是本地的IP地址
     *
     * @return
     */
    fun serviceIp(): String {
        var result = ""
        try {
            val address = InetAddress.getLocalHost()
            result = address.hostAddress
        } catch (e: UnknownHostException) {
            logger.error("------>调用 IpUtil.servicerIp，错误信息如下")
            logger.error(e.message, e)
        }
        return result
    }

    /**
     * 获取的是该网站的ip地址，比如我们所有的请求都通过nginx的，所以这里获取到的其实是nginx服务器的IP地址
     *
     * @param domain
     * @return
     */
    fun serviceIp(domain: String?): String {
        var result = ""
        if (TextUtil.isNotNullOrEmpty(domain)) {
            try {
                val inetAddress = InetAddress.getByName(domain)
                result = inetAddress.hostAddress
            } catch (e: UnknownHostException) {
                logger.error("------>调用 IpUtil.servicerIp，错误信息如下")
                logger.error(e.message, e)
            }
        }
        return result
    }

    /**
     * 根据主机名返回其可能的所有Ip地址
     *
     * @param domain
     * @return
     */
    fun originalServiceIp(domain: String?): List<String> {
        val result: MutableList<String> = ArrayList()
        if (TextUtil.isNotNullOrEmpty(domain)) {
            try {
                val addresses = InetAddress.getAllByName(domain)
                for (addr in addresses) {
                    result.add(addr.hostAddress)
                }
            } catch (e: UnknownHostException) {
                logger.error("------>调用 IpUtil.originalServicerIp，错误信息如下")
                logger.error(e.message, e)
            }
        }
        return result
    }

    /**
     * @param request
     * @return
     * @description: 如果通过了多级反向代理的话，
     * X-Forwarded-For的值并不止一个， 而是一串IP值，
     * 究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取 X-Forwarded-For中第一个非unknown的有效IP字符串。
     */
    fun clientIp(request: HttpServletRequest): String? {
        var ip = request.getHeader("x-forwarded-for")
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
        }
        return ip
    }
}