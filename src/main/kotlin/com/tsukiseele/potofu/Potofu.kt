package com.tsukiseele.potofu

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.runApplication

// 排除Jackson以使用Gson
@SpringBootApplication(exclude = [JacksonAutoConfiguration::class])
@MapperScan("com.tsukiseele.potofu.mappers")
open class Potofu
fun main(args: Array<String>) {
    runApplication<Potofu>(*args)
}