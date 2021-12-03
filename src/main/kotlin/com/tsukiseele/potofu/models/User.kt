package com.tsukiseele.potofu.models

import com.tsukiseele.potofu.kotlin.Noarg
import java.io.Serializable
import java.util.*

@Noarg
data class User(
        var userId: Int,
        var userName: String?,
        var userPassword: String?,
        var userEmail: String?,
        var userLastLoginTime: Date?,
        var userLastLoginIp: String?
) : Serializable