package com.tsukiseele.potofu.controllers

import com.tsukiseele.potofu.helper.*
import com.tsukiseele.potofu.models.Image
import com.tsukiseele.potofu.services.ImageService
import org.apache.ibatis.annotations.Param
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("image")
class ImageController @Autowired constructor(val imageService: ImageService) {
    @PutMapping
    fun addImage(@RequestBody image: Image): ResponseEntity<*> {
        image.imgDate = Date()
        return if (imageService.addImage(image)) {
            mapOf(Pair("message", "上传成功")).created()
        } else {
            badRequest("创建失败")
        }
    }

    @GetMapping("/hash/{hash}")
    fun getImageByHash(@PathVariable hash: String): ResponseEntity<R<Image>> {
        return imageService.getImageByImageHash(hash).ok()
    }

    @GetMapping("/{id}")
    fun getImagesByUserId(@PathVariable id: Int): ResponseEntity<R<List<Image>>> {
        return imageService.getImageByUserId(id).ok()
    }

    @GetMapping
    fun getImagesByRange(@Param("index") index: Int, @Param("count") count: Int)
            : ResponseEntity<R<List<Image>>> {
        return imageService.getImageByRange(index, count).ok()
    }

    @GetMapping("/all")
    fun getAllImages()
            : ResponseEntity<R<List<Image>>> {
        return imageService.getAllImage().ok()
    }
}