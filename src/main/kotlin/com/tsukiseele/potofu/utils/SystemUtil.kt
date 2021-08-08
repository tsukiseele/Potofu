package com.tsukiseele.potofu.utils

object SystemUtil {
    fun isLinux(): Boolean {
        return System.getProperty("os.name").toLowerCase().contains("linux")
    }
}