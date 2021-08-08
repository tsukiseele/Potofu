package com.tsukiseele.utils

object TextUtil {
    fun isNullOrEmpty(text: String?): Boolean {
        return null == text || text.trim().isEmpty()
    }

    fun isNullOrEmptyOfAll(vararg texts: String?): Boolean {
        for (text in texts) if (isNullOrEmpty(text)) return true
        return false
    }

    fun isNotNullOrEmpty(text: String?): Boolean {
        return null != text && !text.trim().isEmpty()
    }

    fun isNotNullOrEmptyOfAll(vararg texts: String?): Boolean {
        return !isNullOrEmptyOfAll(*texts)
    }

    fun toInt(text: String): Int {
        var num = 0
        try {
            num = text.toInt()
        } catch (e: Exception) {
        }
        return num
    }

    // 利用反射生成一个包含对象所有字段信息的字符串
    fun toString(obj: Any): String {
        val sb = StringBuilder()
        sb.append('[')
        try {
            var type: Class<*> = obj.javaClass
            while (type != Any::class.java) {
                val fields = type.declaredFields
                for (field in fields) {
                    field.isAccessible = true
                    sb.append(String.format("%s = %s, ", field.name, field[obj]))
                }
                type = type.superclass
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb.substring(0, sb.length - 2) + ']'
    }
}