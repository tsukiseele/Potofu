package com.tsukiseele.potofu.controllers

import com.tsukiseele.potofu.helper.*
import com.tsukiseele.potofu.models.Link
import com.tsukiseele.potofu.services.LinkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/link"])
class LinkController @Autowired constructor(private val linkService: LinkService) {
    @GetMapping
    fun getAll(): ResponseEntity<R<List<Link>>> {
        return linkService.getAll().ok()
    }

    @PutMapping
    fun add(link: Link): ResponseEntity<*> {
        return if (linkService.add(link) == 1) {
            ok("创建成功")
        } else {
            badRequest("创建失败")
        }
    }
}