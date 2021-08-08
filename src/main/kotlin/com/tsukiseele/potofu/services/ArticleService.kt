package com.tsukiseele.potofu.services

import com.tsukiseele.potofu.models.Article

interface ArticleService {
    /**
     * 提交
     * @param article
     * @return
     */
    fun add(article: Article?): Int

    /**
     * 分页查询
     * @param index
     * @param count
     * @return
     */
    fun getRange(index: Int, count: Int): List<Article?>

    /**
     * 修改
     * @param article
     * @return
     */
    fun setArticle(article: Article?): Article?

    /**
     * 按ID查询e
     * @param articleId
     * @return
     */
    fun getOne(articleId: Int): Article?

    /**
     *
     */
    fun getArticleCount(): Any?

    /**
     * 获取所有Article，但不包含<strong>article_content</strong>
     * @return List<Article>
     */
    fun getAllArticle(): List<Article>
}