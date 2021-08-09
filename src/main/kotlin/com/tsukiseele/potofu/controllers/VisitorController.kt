package com.tsukiseele.potofu.controllers

import com.tsukiseele.potofu.helper.*
import com.tsukiseele.potofu.models.Visitor
import com.tsukiseele.potofu.services.VisitorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/visitor")
class VisitorController @Autowired constructor(private val visitorService: VisitorService) {

    @PutMapping
    fun access(@RequestBody visitor: Visitor, request: HttpServletRequest?, response: HttpServletResponse)
            : ResponseEntity<*> {
        val visitorId = visitorService.create(visitor, request, response);
        return if (visitorId > 0) {
            mapOf(Pair("visitorId", visitorId)).ok()
            /*
            this.responseMap(R.Status.OK) {
                this["visitorId"] = visitorId
            }*/
//            R.created().map(Pair("visitorId", visitorId))
        }  else {
            badRequest("创建失败")
        }
    }

    @GetMapping("/id/{visitorId}")
    fun findByVisitor(@PathVariable("visitorId") visitorId: Int): Visitor? {
        return visitorService.getVisitorById(visitorId)
    }
}