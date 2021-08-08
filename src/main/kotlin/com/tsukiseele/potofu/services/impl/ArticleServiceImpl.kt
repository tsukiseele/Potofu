package com.tsukiseele.potofu.services.impl

import com.tsukiseele.potofu.mappers.ArticleMapper
import com.tsukiseele.potofu.models.Article
import com.tsukiseele.potofu.services.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
open class ArticleServiceImpl
@Autowired constructor(private val articleMapper: ArticleMapper) : ArticleService {
    override fun add(article: Article?): Int {
        article?.articleDate = Date()
        println(article)
        return articleMapper.insert(article)
    }

//    @Cacheable(key = "'articles-' + #index + '-' + #count", value = ["hourCache"])
    override fun getRange(index: Int, count: Int): List<Article?> {
        println("============================================")
        println("query database：index = ${index}, count = ${count}")
        val start = (index - 1) * count
        return articleMapper.queryRange(start, count)
    }

    override fun setArticle(article: Article?): Article? {
        return articleMapper.update(article)
    }

    /**
     * 按ID查询
     *
     * @param articleId
     * @return
     */
    override fun getOne(articleId: Int): Article? {
        val article = articleMapper.query(articleId)
        return article
    }

    /**
     *
     */
    override fun getArticleCount(): Int {
        return articleMapper.queryCount()
    }

    /*

    override fun getArchives(): List<Article> {
        val articles = articleMapper.queryAllNoContent()
        return articles

        val archives = mutableMapOf<String, MutableList<Article>>()
        val sdf = SimpleD ateFormat("yyyy-MM")
        articles.forEach { article ->
            val date = sdf.format(article.articleDate)
            if (archives.containsKey(date))
                archives.get(date)?.add(article)
            else
                archives.put(date, mutableListOf(article))
        }

        return archives.toSortedMap(Comparator<String> { o1, o2 ->
                    return@Comparator -o1.compareTo(o2)
                })
    }
    */
//    @Cacheable(key = "'articles-all'", value = ["hourCache"])
    override fun getAllArticle(): List<Article> {
        return articleMapper.queryAllNoContent()
    }
}