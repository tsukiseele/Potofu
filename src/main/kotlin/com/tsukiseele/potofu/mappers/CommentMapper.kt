package com.tsukiseele.potofu.mappers

import com.tsukiseele.potofu.models.Comment
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
interface CommentMapper {
    @Insert(""" INSERT INTO comment(
                    article_id,
                    visitor_id,
                    comment_content, 
                    comment_reply_id) 
                VALUES (
                    #{articleId}, 
                    #{visitorId}, 
                    #{commentContent}, 
                    #{commentReplyId}) """)
    fun insert(comment: Comment?): Int

    @Select("SELECT * FROM comment WHERE article_id = #{article_id}")
    fun queryCommentByArticleId(@Param("article_id") articleId: Int): List<Comment>

    @Select("SELECT * FROM comment WHERE from_uid = #{from_uid}")
    fun queryCommentByUserId(@Param("from_uid") fromUid: Int): List<Comment>
}