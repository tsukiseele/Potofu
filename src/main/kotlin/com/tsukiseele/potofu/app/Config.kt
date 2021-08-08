package com.tsukiseele.potofu.app

import com.google.gson.Gson
import com.tsukiseele.utils.IOUtil
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.io.IOException

class Config  // 单例
private constructor() {
    // 文件上传配置
    val fileUploadPath: String? = null
    val fileUploadUrlPrefix: String? = null

    companion object {
        private const val FILE_CONFIG = "config.json"
        private const val FILE_CONFIG_LINUX = "config-linux.json"

        @Transient
        private var INSTANCE: Config? = null
        fun get(): Config? {
            return INSTANCE
        }

        init {
            try {
                val configPath = PathMatchingResourcePatternResolver().getResource(
                        if (System.getProperty("os.name").toLowerCase().contains("linux"))
                            FILE_CONFIG_LINUX
                        else
                            FILE_CONFIG
                ).file

                var configText = "{}"

                try {
                    configText = IOUtil.readText(configPath.getAbsolutePath(), "UTF-8")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                INSTANCE = Gson().fromJson(configText, Config::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}