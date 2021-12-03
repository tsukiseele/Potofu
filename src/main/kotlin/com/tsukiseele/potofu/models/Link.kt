package com.tsukiseele.potofu.models

import com.tsukiseele.potofu.kotlin.Noarg
import java.io.Serializable

@Noarg
data class Link(
        var linkId : Int,
        var linkName : String?,
        var linkIcon : String?,
        var linkLink : String?,
        var linkType : String?,
        var linkInfo : String?
): Serializable