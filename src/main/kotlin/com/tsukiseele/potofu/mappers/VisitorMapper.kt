package com.tsukiseele.potofu.mappers

import com.tsukiseele.potofu.models.Visitor
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.SelectKey
import org.springframework.stereotype.Repository

@Repository
interface VisitorMapper {
    @Insert(""" INSERT INTO visitor(
                    visitor_nickname,
                    visitor_email,
                    visitor_domain,
                    visitor_ip,
                    visitor_datetime) 
                VALUES(
                    #{visitorNickname},
                    #{visitorEmail},
                    #{visitorDomain},
                    #{visitorIp},
                    #{visitorDatetime}) """)
    // 注意：@Param注解会导致@SelectKey注解的keyProperty属性无法正确映射到目标对象
    @SelectKey(statement = ["SELECT LAST_INSERT_ID()"], keyProperty = "visitorId", before = false, resultType = Int::class)
    fun insert(visitor: Visitor?): Int

    @Select(""" SELECT 
                    visitor_id, 
                    visitor_nickname,
                    visitor_email,
                    visitor_domain,
                    visitor_ip,
                    visitor_datetime 
                FROM visitor """)
    fun queryAll(): List<Visitor?>?

    @Select("SELECT * FROM visitor WHERE visitor_id = #{id}")
    fun queryVisitorById(@Param("id") id: Int): Visitor
}