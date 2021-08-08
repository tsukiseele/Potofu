package com.tsukiseele.potofu.mappers

import com.tsukiseele.potofu.models.User
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
interface UserMapper {
    @Select(""" SELECT 
                    user_id,
                    user_name, 
                    user_email,
                    user_last_login_time,
                    user_last_login_ip
                FROM user 
                WHERE user_email = #{userEmail} 
                AND user_password = #{userPassword} 
                LIMIT 1 """)
    fun queryOne(userEmail: String?, userPassword: String?): User?

    @Insert(""" INSERT INTO user(
                    user_name, 
                    user_password, 
                    user_email, 
                    user_last_login_time, 
                    user_last_login_ip)
                VALUES (
                    #{userName}, 
                    #{userPassword}, 
                    #{userEmail}, 
                    #{userLastLoginTime}, 
                    #{userLastLoginIp}) """)
    fun insert(user: User?): Int

    // 参见UserMapper.xml
    fun update(user: User?): Int
}