package com.tsukiseele.potofu.models

import java.io.Serializable

class CommentNode(val comment: Comment?, val visitor: Visitor?): Serializable {
    private var childs: MutableList<CommentNode>? = null

    fun getChilds(): List<CommentNode>? {
        return childs
    }

    fun setChilds(childs: MutableList<CommentNode>?) {
        this.childs = childs
    }

    fun addChild(child: CommentNode): CommentNode {
        if (childs == null) childs = ArrayList()
        childs!!.add(child)
        return this
    }

    companion object {
        fun tree(comments: List<Comment>?, visitorMap: Map<Int, Visitor?>): List<CommentNode> {
            comments ?: return arrayListOf()
            // 建立映射
            val commentItems: MutableMap<Int, CommentNode> = hashMapOf()
            for (comment in comments)
                commentItems[comment.commentId] = CommentNode(comment, visitorMap[comment.visitorId])
            // 进行连接
            val commentTree: MutableList<CommentNode> = ArrayList()
            for ((_, commentNode) in commentItems) {
                commentNode.comment?.also {
                    // 存在回复id，则将其添加到该评论的child里，否则添加到root
                    if (it.commentReplyId > 0 && commentItems.containsKey(it.commentReplyId))
                        commentItems[it.commentReplyId]?.addChild(commentNode)
                    else
                        commentTree.add(commentNode)
                }
            }
            return commentTree
        }
    }
}