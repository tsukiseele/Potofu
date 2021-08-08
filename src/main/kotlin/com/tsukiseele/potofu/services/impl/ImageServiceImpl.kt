package com.tsukiseele.potofu.services.impl

import com.tsukiseele.potofu.mappers.ImageMapper
import com.tsukiseele.potofu.models.Image
import com.tsukiseele.potofu.services.ImageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ImageServiceImpl @Autowired constructor(
        var imageMapper: ImageMapper) : ImageService {
    private val LOG: Logger = LoggerFactory.getLogger(ImageServiceImpl::class.java)

    override fun getAllImage(): List<Image> {
        return imageMapper.queryAll()
    }

    override fun getImageByRange(index: Int, count: Int): List<Image> {
        return imageMapper.queryRange(index, count)
    }

    override fun getImageByUserId(userId: Int): List<Image> {
        return imageMapper.queryImagesByUserId(userId)
    }

    override fun addImage(image: Image): Boolean {
        return imageMapper.insert(image) == 1
    }

    override fun getImageByImageHash(hash: String): Image {
        return imageMapper.queryImagesByHash(hash)
    }
}