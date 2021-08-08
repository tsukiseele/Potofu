package com.tsukiseele.potofu.models

import java.io.Serializable
import java.util.*

class Comment: Serializable {
    constructor()
    constructor(commentId: Int,
                articleId: Int,
                commentContent: String?,
                visitorId: Int,
                commentReplyId: Int,
                commentDatetime: Date?) {
        this.commentId = commentId
        this.articleId = articleId
        this.commentContent = commentContent
        this.visitorId = visitorId
        this.commentReplyId = commentReplyId
        this.commentDatetime = commentDatetime
    }

    // 评论ID
    var commentId = 0

    // 所属文章ID
    var articleId = 0

    // 内容
    var commentContent: String? = null

    // 评论者ID
    var visitorId = 0

    // 回复目标ID
    var commentReplyId = 0

    // 评论时间
    var commentDatetime: Date? = null
}