package com.tsukiseele.potofu.mappers

import com.tsukiseele.potofu.models.Article
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
interface ArticleMapper {
    @Select(""" SELECT
                    article_id, 
                    user_id, 
                    article_title, 
                    article_subtitle, 
                    article_cover, 
                    article_date, 
                    article_last_update, 
                    article_views,
                    article_comment_count  
                FROM article 
                ORDER BY article_id DESC 
                LIMIT #{index}, #{count} """)
    fun queryRangeNoContent(@Param("index") index: Int, @Param("count") count: Int): List<Article?>


    @Select(""" SELECT
                    article_id, 
                    user_id, 
                    article_title, 
                    article_subtitle,
                    article_tags,
                    article_type,
                    article_content,
                    article_cover, 
                    article_date, 
                    article_last_update, 
                    article_views,
                    article_comment_count  
                FROM article 
                ORDER BY article_id DESC 
                LIMIT #{index}, #{count} """)
    fun queryRange(@Param("index") index: Int, @Param("count") count: Int): List<Article?>

    @Select("SELECT * FROM article WHERE article_id = #{articleId} LIMIT 1")
    fun query(@Param("articleId") articleId: Int): Article?

    @Select(""" SELECT 
                    article_id, 
                    user_id, 
                    article_title, 
                    article_subtitle,
                    article_tags,
                    article_type,
                    article_cover, 
                    article_date, 
                    article_last_update, 
                    article_views, 
                    article_comment_count 
                FROM article 
                ORDER BY article_id DESC
                LIMIT 1000""")
    fun queryAllNoContent(): List<Article>

    @Select("SELECT COUNT(article_id) FROM article")
    fun queryCount(): Int

    @Insert(""" INSERT INTO article(
                    user_id, 
                    article_title, 
                    article_subtitle,
                    article_tags,
                    article_type,
                    article_cover, 
                    article_content, 
                    article_date)
                VALUES (
                    #{userId}, 
                    #{articleTitle},
                    #{articleSubtitle},
                    #{articleTags},
                    #{articleType},
                    #{articleCover}, 
                    #{articleContent}, 
                    #{articleDate}) """)
    fun insert(article: Article?): Int

    @Delete("DELETE FROM article WHERE article_id = #{articleId}")
    fun deleteById(@Param("articleId") articleId: Int): Article?

    fun update(article: Article?): Article?
}