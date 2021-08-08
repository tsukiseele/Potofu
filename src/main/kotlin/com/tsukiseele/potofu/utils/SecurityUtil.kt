package com.tsukiseele.potofu.utils

import org.apache.commons.codec.digest.DigestUtils
import java.util.*

object SecurityUtil {
    fun getPasswordHash(password: String?): String {
        return DigestUtils.md5Hex(Base64.getEncoder().encode(password!!.toByteArray()))
    }
}