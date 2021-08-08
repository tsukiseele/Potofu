package com.tsukiseele.potofu.services

import com.tsukiseele.potofu.models.Link

interface LinkService {
    fun getAll(): List<Link>

    fun add(link: Link): Int
}