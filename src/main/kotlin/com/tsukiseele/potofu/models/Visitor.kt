package com.tsukiseele.potofu.models

import java.io.Serializable
import java.util.*

data class Visitor (
    var visitorId: Int,
    var visitorName: String?,
    var visitorEmail: String?,
    var visitorDomain: String?,
    var visitorIp: String?,
    var visitorDatetime: Date?
): Serializable