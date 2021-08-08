package com.tsukiseele.potofu.mappers

import com.tsukiseele.potofu.models.Link
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
interface LinkMapper {
    @Select(""" SELECT link_id, 
                    link_name, 
                    link_icon, 
                    link_link, 
                    link_type,
                    link_info
                FROM link""")
    fun queryAll(): List<Link>

    @Insert(""" INSERT INTO link(
                    link_link, 
                    link_name,
                    link_icon, 
                    link_type, 
                    link_info) 
                VALUES(
                    #{linkLink},
                    #{linkName},
                    #{linkIcon},
                    #{linkType},
                    #{linkInfo}) """)
    fun insert(link: Link): Int
}