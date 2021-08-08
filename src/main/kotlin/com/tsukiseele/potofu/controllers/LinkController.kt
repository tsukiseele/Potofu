package com.tsukiseele.potofu.controllers

import com.tsukiseele.potofu.helper.R
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
    fun getAll(): ResponseEntity<List<Link>> {
        return R.ok().data(linkService.getAll())
    }

    @PutMapping
    fun add(link: Link): ResponseEntity<MutableMap<String, Any>> {
        return if (linkService.add(link) == 1)
            R.ok().message("创建成功")
        else
            R.failed().message("创建失败")
    }
}