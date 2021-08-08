package com.tsukiseele.potofu.mappers

import com.tsukiseele.potofu.models.Image
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
interface ImageMapper {
    @Insert(""" 
        INSERT INTO img(user_id, img_id, img_src, img_date, img_hash) 
        VALUES(#{userId}, #{imgId}, #{imgSrc}, #{imgDate}, #{imgHash}) """)
    fun insert(image: Image): Int

    @Select(""" SELECT * FROM img WHERE img_hash = #{imgHash} """)
    fun queryImagesByHash(@Param("imgHash") imgHash: String): Image

    @Select(""" SELECT * FROM img WHERE user_id = #{userId} """)
    fun queryImagesByUserId(@Param("userId") userId: Int): List<Image>

    @Select(""" SELECT * FROM img ORDER BY img_date DESC LIMIT #{index}, #{count}""")
    fun queryRange(@Param("index") index: Int, @Param("count") count: Int): List<Image>

    @Select(""" SELECT * FROM img ORDER BY img_date """)
    fun queryAll(): List<Image>
}