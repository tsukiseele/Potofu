package com.tsukiseele.potofu.helper

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

inline fun <T> T.responseMap(status: R.Status, message: String? = null, data: MutableMap<String, Any?>.() -> Unit): ResponseEntity<R<MutableMap<String, Any?>>> {
    return R.create(mutableMapOf<String, Any?>().also { data(it) }, status, message)
}

fun <T> T.ok(message: String? = null): ResponseEntity<R<T>> = R.create(this, R.Status.OK, message);
fun <T> T.created(message: String? = null): ResponseEntity<R<T>> = R.create(this, R.Status.CREATED, message);
fun <T> T.noContent(message: String? = null): ResponseEntity<R<T>> = R.create(this, R.Status.NO_CONTENT, message);
fun <T> T.badRequest(message: String? = null): ResponseEntity<R<T>> = R.create(this, R.Status.BAD_REQUEST, message);
fun <T> T.unauthorized(message: String? = null): ResponseEntity<R<T>> = R.create(this, R.Status.UNAUTHORIZED, message);
fun <T> T.forbidden(message: String? = null): ResponseEntity<R<T>> = R.create(this, R.Status.FORBIDDEN, message);
fun <T> T.notFound(message: String? = null): ResponseEntity<R<T>> = R.create(this, R.Status.NOT_FOUND, message)

open class R<T>(
    var data: T? = null,
    var code: Int? = null,
    var message: String? = null,
    var ok: Boolean? = null) {

    enum class Status(val code: Int, val message: String) {
        OK(200, "OK"),
        CREATED(201, "Created"),
        NO_CONTENT(204, "No Content"),
        BAD_REQUEST(400, "Bad Request"),
        UNAUTHORIZED(401, "Unauthorized"),
        FORBIDDEN(403, "Forbidden"),
        NOT_FOUND(404, "Not Found")
    }

    companion object {
        fun <T> create(data: T, status: Status, message: String? = null): ResponseEntity<R<T>> {
            return ResponseEntity.status(HttpStatus.OK).body(R(
                data,
                status.code,
                message ?: status.message,
                status.code in 200..299
            ));
        }
    }
}