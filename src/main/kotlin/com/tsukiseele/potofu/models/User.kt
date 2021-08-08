package com.tsukiseele.potofu.models

import com.tsukiseele.kotlin.Noarg
import java.io.Serializable
import java.util.*

@Noarg
class User(
        var userId: Int,
        var userName: String?,
        var userPassword: String?,
        var userEmail: String?,
        var userLastLoginTime: Date?,
        var userLastLoginIp: String?
) : Serializable