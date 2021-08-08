package com.tsukiseele.potofu.controllers

import com.tsukiseele.potofu.helper.R
import com.tsukiseele.potofu.models.Comment
import com.tsukiseele.potofu.models.CommentNode
import com.tsukiseele.potofu.services.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/comment"])
class CommentController @Autowired constructor(private val commentService: CommentService) {
    @PutMapping
    fun add(@RequestBody comment: Comment): ResponseEntity<Any?>? {
        return R.ok().data(commentService.add(comment))
    }

    @GetMapping("uid/{uid}")
    fun getByUserId(@PathVariable(value = "uid") uid: Int): ResponseEntity<List<Comment?>?> {
        return R.ok().data(commentService.getCommentByUserId(uid))
    }

    @GetMapping
    fun getByArticleId(@RequestParam(value = "article_id") articleId: Int): ResponseEntity<List<Comment?>?> {
        return R.ok().data(commentService.getCommentByArticleId(articleId))
    }

    @GetMapping("/tree/{article_id}")
    fun getCommentTreeByArticleId(@PathVariable(value = "article_id") articleId: Int): ResponseEntity<List<CommentNode?>?> {
        return R.ok().data(commentService.getCommentTreeByArticleId(articleId))
    }
}