package com.tsukiseele.potofu.services

import com.tsukiseele.potofu.models.Image

interface ImageService {
    fun addImage(image: Image): Boolean
    fun getAllImage(): List<Image>
    fun getImageByRange(index: Int, count: Int): List<Image>
    fun getImageByUserId(userId: Int): List<Image>
    fun getImageByImageHash(hash: String): Image
}