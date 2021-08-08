package com.tsukiseele.potofu.models

import com.tsukiseele.kotlin.Noarg
import java.io.Serializable
import java.util.Date

@Noarg
data class Image(
        var userId: Int,
        var imgId: Int,
        var imgSrc: String?,
        var imgDate: Date?,
        var imgHash: String?
): Serializable