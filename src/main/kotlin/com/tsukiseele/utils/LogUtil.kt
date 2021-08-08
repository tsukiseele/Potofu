package com.tsukiseele.utils

import java.io.FileOutputStream
import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.*

object LogUtil {
    var level = Level.VERBOSE
    private var logWriter: PrintStream? = null
    private var logFilePath: String? = null
    private val dateFormat = SimpleDateFormat("HH:mm:ss.SSS")
    fun setStream(writer: PrintStream?) {
        logWriter = writer
    }

    fun setLogFilePath(logOutputPath: String?) {
        logFilePath = logOutputPath
    }

    fun v(tag: String, message: String) {
        if (level.level < 1) {
            logWriter!!.println("[" + dateFormat.format(System.currentTimeMillis()) + "]" + ": " + level.name + "  " + tag + "  " + message)
        }
    }

    fun d(tag: String, message: String) {
        if (level.level < 2) {
            logWriter!!.println("[" + dateFormat.format(System.currentTimeMillis()) + "]" + ": " + level.name + "  " + tag + "  " + message)
        }
    }

    fun i(tag: String, message: String) {
        if (level.level < 3) {
            logWriter!!.println("[" + dateFormat.format(System.currentTimeMillis()) + "]" + ": " + level.name + "  " + tag + "  " + message)
        }
    }

    fun w(tag: String, message: String) {
        if (level.level < 4) {
            logWriter!!.println("[" + dateFormat.format(System.currentTimeMillis()) + "]" + ": " + level.name + "  " + tag + "  " + message)
        }
    }

    fun e(tag: String, message: String) {
        if (level.level < 5) {
            logWriter!!.println("[" + dateFormat.format(System.currentTimeMillis()) + "]" + ": " + level.name + "  " + tag + "  " + message)
        }
    }

    fun e(e: Exception) {
        if (level.level < 5) {
            logWriter!!.println("[" + dateFormat.format(System.currentTimeMillis()) + "]" + ": " + level.name + "  " + e.toString())
        }
    }

    fun printDebugLog(tag: String?, message: String?): Boolean {
        if (logFilePath == null) return false
        var debugLogWrite: PrintStream? = null
        try {
            debugLogWrite = PrintStream(FileOutputStream(logFilePath, true), true, "UTF-8")
            debugLogWrite.printf("%s\nTag : %s\nMsg : %s\n", dateFormat.format(Date()), tag, message)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                debugLogWrite!!.close()
            } catch (e: Exception) {
            }
        }
        return true
    }

    enum class Level(val level: Int) {
        VERBOSE(0), DEBUG(1), INFO(2), WARN(3), ERROR(4), CLOSE(5);
    }

    init {
        logWriter = System.out
    }
}