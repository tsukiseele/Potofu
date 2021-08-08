package com.tsukiseele.potofu.helper

import java.util.*

class TokenPool private constructor() {
    override fun toString(): String {
        return tokens.toString()
    }

    companion object {
        private val tokens: MutableMap<String?, Token?> = HashMap()
        operator fun get(token: String?): Token? {
            return tokens[token]
        }

        fun add(token: Token) {
            tokens[token.token] = token
        }

        fun remove(token: String?): Token? {
            return tokens.remove(token)
        }

        operator fun contains(token: String?): Boolean {
            return tokens.containsKey(token)
        }

        fun exists(token: String?, isRefreshTime: Boolean = true): Boolean {
            return if (tokens.containsKey(token)) {
                tokens[token]!!.check(isRefreshTime)
            } else false
        }

        fun getTokens(): Map<String?, Token?> {
            return tokens
        }
    }
}