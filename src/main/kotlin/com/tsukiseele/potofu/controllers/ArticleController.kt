package com.tsukiseele.potofu.controllers

import com.tsukiseele.potofu.helper.*
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
            return unauthorized("提交失败，请登录")
        // 写入用户id
        TokenPool.get(token)?.user?.also {
            article?.userId = it.userId
        }
        // 插入
        return if (articleService.add(article) == 1) {
            created()
        } else {
            badRequest()
        }
    }

    @GetMapping("/{articleId}")
    fun getArticle(@PathVariable("articleId") articleId: Int): ResponseEntity<*> {
        return articleService.getOne(articleId).ok()
    }

    @GetMapping
    fun getArticlePage(
            @RequestParam(value = "page", defaultValue = "1") page: Int,
            @RequestParam(value = "count", defaultValue = "5") count: Int): ResponseEntity<*> {
        return articleService.getRange(page, count).ok()
    }

    @GetMapping("/all")
    fun getAllArticle(): ResponseEntity<*> {
        return articleService.getAllArticle().ok()
    }

    @GetMapping("/count")
    fun count(): ResponseEntity<*> {
        return articleService.getArticleCount().ok()
    }

    @PostMapping("/update")
    fun update(@RequestBody article: Article?): ResponseEntity<*> {
        return articleService.setArticle(article).ok()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ArticleController::class.java)
    }
}