package com.tsukiseele.potofu.services

import com.tsukiseele.potofu.models.Comment
import com.tsukiseele.potofu.models.CommentNode

interface CommentService {
    fun add(comment: Comment): Int
    fun getCommentByUserId(uid: Int): List<Comment>
    fun getCommentByArticleId(articleId: Int): List<Comment>
    fun getCommentTreeByArticleId(articleId: Int): List<CommentNode>
}