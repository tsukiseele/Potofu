package com.tsukiseele.utils

import java.io.*

object ObjectUtil {
    // 序列化拷贝对象
    fun <T> cloneObject(obj: T): T? {
        var oos: ObjectOutputStream? = null
        var ois: ObjectInputStream? = null
        var newObject: T? = null
        try {
            // 序列化
            val baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(obj)
            // 反序列化
            val bais = ByteArrayInputStream(baos.toByteArray())
            ois = ObjectInputStream(bais)
            newObject = ois.readObject() as T
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } finally {
            try {
                oos!!.close()
                ois!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return newObject
    }
}