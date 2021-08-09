package com.tsukiseele.potofu.controllers

import com.tsukiseele.potofu.app.Config
import com.tsukiseele.potofu.helper.badRequest
import com.tsukiseele.potofu.helper.*
import com.tsukiseele.potofu.models.Image
import com.tsukiseele.potofu.services.ImageService
import com.tsukiseele.potofu.utils.SystemUtil
import com.tsukiseele.utils.IOUtil
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(value = ["/upload"])
class UploadController @Autowired constructor(private val imageService: ImageService) {
//    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    @PostMapping("/image")
    @ResponseBody
    fun uploadImage(@RequestPart("image") image: MultipartFile, request: HttpServletRequest?): ResponseEntity<*>? {
        try {
            val data = image.bytes
            val md5 = DigestUtils.md5Hex(data)
            // 目录名：当前日期
            val dir = Config.get()!!.fileUploadPath//File(Config.get()!!.fileUploadPath, simpleDateFormat.format(Date()))
            // 文件名：MD5 + 后缀名
            val fileName = md5 + "." + IOUtil.getFileSuffix(image.originalFilename)
            // 拼接输出路径
            val out = File(dir, fileName)
            // 只有服务端不存在才会写入文件
            if (!out.exists()) {
                out.writeBytes(data)
                // Linux系统需要赋予上传文件的访问权限
                if (SystemUtil.isLinux()) {
                    val runtime = Runtime.getRuntime()
                    val command = "chmod 774 " + out.absolutePath
                    try {
                        runtime.exec(command)
//                        val process = runtime.exec(command)
//                        process.waitFor()
//                        val existValue = process.exitValue()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            val ref: String = Config.get()!!.fileUploadUrlPrefix + fileName
            return mapOf(Pair("ref", ref)).ok()
        } catch (e: Exception) {
            e.printStackTrace()
            return badRequest("上传失败: " + e.message)
        }
    }

    @PostMapping("/photo")
    @ResponseBody
    fun uploadPhoto(@RequestPart("photo") image: MultipartFile, request: HttpServletRequest?): ResponseEntity<*>? {
        try {
            val data = image.bytes
            val md5 = DigestUtils.md5Hex(data)
            // 目录名：当前日期
            val dir = Config.get()!!.fileUploadPath//File(Config.get()!!.fileUploadPath, simpleDateFormat.format(Date()))
            // 文件名：MD5 + 后缀名
            val fileName = md5 + "." + IOUtil.getFileSuffix(image.originalFilename)
            // 拼接输出路径
            val out = File(dir, fileName)
            // 只有服务端不存在才会写入文件
            if (!out.exists()) {
                out.writeBytes(data)
                // Linux系统需要赋予上传文件的访问权限
                if (SystemUtil.isLinux()) {
                    val runtime = Runtime.getRuntime()
                    val command = "chmod 774 " + out.absolutePath
                    try {
                        runtime.exec(command)
//                        val process = runtime.exec(command)
//                        process.waitFor()
//                        val existValue = process.exitValue()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            val ref: String = Config.get()!!.fileUploadUrlPrefix + fileName
            imageService.addImage(Image(114514, 0, ref, Date(), md5))
            return mapOf(Pair("ref", ref)).ok()
        } catch (e: Exception) {
            e.printStackTrace()
            return badRequest("上传失败: " + e.message)
        }
    }
}