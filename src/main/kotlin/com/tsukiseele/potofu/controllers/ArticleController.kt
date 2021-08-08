package com.tsukiseele.potofu.controllers

import com.tsukiseele.potofu.helper.TokenPool
import com.tsukiseele.potofu.helper.R
import com.tsukiseele.potofu.models.Article
import com.tsukiseele.potofu.services.ArticleService
import com.tsukiseele.potofu.utils.CookieUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(value = ["/article"])
class ArticleController @Autowired constructor(private val articleService: ArticleService) {
    @PostMapping
    fun submit(@RequestBody article: Article?, req: HttpServletRequest): ResponseEntity<out Any> {
        val token = CookieUtil.getCookie(req, "token")
        // 身份验证成功且提交成功
        if (!TokenPool.exists(token))
            return R.unauth().message("提交失败，请登录")
        // 写入用户id
        TokenPool.get(token)?.user?.also {
            article?.userId = it.userId
        }
        // 插入
        return if (articleService.add(article) == 1) {
            R.created().data("Created")
        } else {
            R.badRequest().message("提交失败")
        }
    }

    @GetMapping("/{articleId}")
    fun getArticle(@PathVariable("articleId") articleId: Int): ResponseEntity<Article?>? {
        return R.ok().data(articleService.getOne(articleId))
    }

    @GetMapping
    fun getArticlePage(
            @RequestParam(value = "page", defaultValue = "1") page: Int,
            @RequestParam(value = "count", defaultValue = "5") count: Int): ResponseEntity<Any> {
        return R.ok().data(articleService.getRange(page, count))
    }

    @GetMapping("/all")
    fun getAllArticle(): ResponseEntity<List<Article>> {
        return R.ok().data(articleService.getAllArticle())
    }

    @GetMapping("/count")
    fun count(): ResponseEntity<Any?> {
        return R.ok().data(articleService.getArticleCount())
    }

    @PostMapping("/update")
    fun update(@RequestBody article: Article?): ResponseEntity<Article?>? {
        return R.ok().data(articleService.setArticle(article))
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ArticleController::class.java)
    }
}