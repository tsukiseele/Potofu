package com.tsukiseele.potofu.services.impl

import com.tsukiseele.potofu.mappers.CommentMapper
import com.tsukiseele.potofu.models.Comment
import com.tsukiseele.potofu.models.CommentNode
import com.tsukiseele.potofu.models.Visitor
import com.tsukiseele.potofu.services.CommentService
import com.tsukiseele.potofu.services.VisitorService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CommentServiceImpl
@Autowired constructor(
        private val commentMapper: CommentMapper,
        private val visitorService: VisitorService) : CommentService {
    override fun add(comment: Comment): Int {
        comment.commentDatetime = Date()
        return commentMapper.insert(comment)
    }

    override fun getCommentByUserId(uid: Int): List<Comment> {
        return commentMapper.queryCommentByUserId(uid)
    }

    override fun getCommentByArticleId(articleId: Int): List<Comment> {
        return commentMapper.queryCommentByArticleId(articleId)
    }

    override fun getCommentTreeByArticleId(articleId: Int): List<CommentNode> {
        val comments = commentMapper.queryCommentByArticleId(articleId)
        val visitorMap: MutableMap<Int, Visitor?> = HashMap()
        comments.apply {
            for (comment in this) {
                comment.also {
                    if (!visitorMap.containsKey(it.visitorId))
                        visitorMap[it.visitorId] = visitorService.getVisitorById(it.visitorId)
                }
            }
        }
        //		LOGGER.debug("[{}]: [{}]", "comment tree created", CommentTree.tree(comments, visitorMap));
        return CommentNode.tree(comments, visitorMap)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CommentServiceImpl::class.java)
    }
}