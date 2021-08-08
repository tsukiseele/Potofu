package com.tsukiseele.potofu.services.impl

import com.tsukiseele.potofu.mappers.LinkMapper
import com.tsukiseele.potofu.models.Link
import com.tsukiseele.potofu.services.LinkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LinkServerImpl
@Autowired constructor(private val linkMapper: LinkMapper) : LinkService {
    override fun getAll(): List<Link> {
        return linkMapper.queryAll()
    }

    override fun add(link: Link): Int {
        return linkMapper.insert(link)
    }
}