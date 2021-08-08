package com.tsukiseele.potofu.utils

import com.tsukiseele.potofu.helper.Token
import org.springframework.util.StringUtils
import javax.servlet.http.HttpServletRequest

object TokenUtil {
    /**
     * 生成token放入session
     * @param request
     * @param tokenServerkey
     */
    fun createToken(request: HttpServletRequest, tokenServerkey: String?, token: Token) {
        request.session.setAttribute(tokenServerkey, token.toString())
    }

    /**
     * 移除token
     * @param request
     * @param tokenServerkey
     */
    fun removeToken(request: HttpServletRequest, tokenServerkey: String?) {
        request.session.removeAttribute(tokenServerkey)
    }

    /**
     * 判断请求参数中的token是否和session中一致
     * @param request
     * @param tokenClientkey
     * @param tokenServerkey
     * @return
     */
    fun judgeTokenIsEqual(request: HttpServletRequest, tokenClientkey: String?, tokenServerkey: String?): Boolean {
        val token_client = request.getParameter(tokenClientkey)
        if (StringUtils.isEmpty(token_client)) {
            return false
        }
        val token_server = request.session.getAttribute(tokenServerkey) as String
        if (StringUtils.isEmpty(token_server)) {
            return false
        }
        return if (token_server != token_client) {
            false
        } else true
    }
}