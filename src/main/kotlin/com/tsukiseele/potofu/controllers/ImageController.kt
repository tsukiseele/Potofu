package com.tsukiseele.potofu.controllers

import com.tsukiseele.potofu.helper.R
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
    fun addImage(@RequestBody image: Image): ResponseEntity<MutableMap<String, Any>> {
        image.imgDate = Date()

        return if (imageService.addImage(image)) {
            R.created().map(Pair("message", "上传成功"))
        } else {
            R.failed().message("创建失败")
        }
    }

    @GetMapping("/hash/{hash}")
    fun getImageByHash(@PathVariable hash: String): ResponseEntity<Image> {
        return R.ok().data(imageService.getImageByImageHash(hash))
    }

    @GetMapping("/{id}")
    fun getImagesByUserId(@PathVariable id: Int): ResponseEntity<List<Image>> {
        return R.ok().data(imageService.getImageByUserId(id))
    }

    @GetMapping
    fun getImagesByRange(@Param("index") index: Int, @Param("count") count: Int)
            : ResponseEntity<List<Image>> {
        return R.ok().data(imageService.getImageByRange(index, count))
    }

    @GetMapping("/all")
    fun getAllImages()
            : ResponseEntity<List<Image>> {
        return R.ok().data(imageService.getAllImage())
    }
}