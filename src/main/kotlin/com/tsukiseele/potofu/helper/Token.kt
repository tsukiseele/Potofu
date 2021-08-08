package com.tsukiseele.potofu.helper

import com.google.gson.Gson
import com.tsukiseele.potofu.models.User
import org.apache.commons.codec.digest.DigestUtils
import java.util.*

/**
 * Token实体类
 * @author TsukiSeele
 * @since 2019-08-02 10:40:54
 */
class Token private constructor(val user: User?, val token: String, private val keepTimeMinute: Int) {
    var expiredDate: Date? = null
        private set

    /**
     * 检查令牌是否合法
     * @param isRefreshTime 是否刷新令牌的过期时间
     * @return 合法性
     */
    @JvmOverloads
    fun check(isRefreshTime: Boolean = true): Boolean {
        // 是否过期
        if (expiredDate!!.before(Date())) return false
        // 更新过期时间
        if (isRefreshTime) resetTime()
        return true
    }

    /**
     * 以设定的时间重置Token的过期日期
     */
    fun resetTime() {
        val calendar = Calendar.getInstance()
        if (keepTimeMinute < 0) calendar.add(Calendar.YEAR, 1) else calendar.add(Calendar.MINUTE, keepTimeMinute)
        expiredDate = calendar.time
    }

    override fun toString(): String {
        return token
    }

    class Builder {
        private val tokenMap: MutableMap<String, String?> = HashMap()
        private var keepTimeMinute = 0
        private var user: User? = null
        fun bind(user: User?): Builder {
            this.user = user
            return this
        }

        /**
         * 添加数据
         * @param key 键
         * @param value 值
         * @return this
         */
        fun add(key: String, value: String?): Builder {
            tokenMap[key] = value
            return this
        }

        /**
         * Token失效的等待时间
         * @param keepTimeMinute
         * @return this
         */
        fun setKeepTimeMinute(keepTimeMinute: Int): Builder {
            this.keepTimeMinute = keepTimeMinute
            return this
        }

        /**
         * 构建并返回一个Token
         * @return Token
         */
        fun build(): Token {
            return Token(user, makeToken(Gson().toJson(tokenMap)), keepTimeMinute)
        }

        /**
         * 加密Token
         * @return
         */
        private fun makeToken(token: String): String {
            return DigestUtils.md5Hex(Base64.getEncoder().encode(token.toByteArray()))
        }
    }

    /**
     *
     * @param token 令牌
     * @param keepTimeMinute 保持时间，以分钟计算，如果该值小于0，则为1年
     */
    init {
        resetTime()
    }
}