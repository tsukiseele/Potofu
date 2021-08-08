package com.tsukiseele.potofu.helper

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

open class R {
    class Successful {
        private var httpStatus: HttpStatus = HttpStatus.OK

        fun status(httpStatus: HttpStatus): Successful {
            this.httpStatus = httpStatus
            return this
        }

        fun <T> data(data: T): ResponseEntity<T> {
            return ResponseEntity.status(httpStatus).body(data)
        }

        fun map(vararg pairs: Pair<String, Any>): ResponseEntity<MutableMap<String, Any>> {
            return ResponseEntity.status(httpStatus).body(mutableMapOf(*pairs))
        }

        fun message(message: String): ResponseEntity<MutableMap<String, Any>> {
            return map(Pair("message", message))
        }

    }

    class Failed {
        private var httpStatus: HttpStatus = HttpStatus.BAD_REQUEST

        fun status(httpStatus: HttpStatus): Failed {
            this.httpStatus = httpStatus
            return this
        }

        fun message(message: String): ResponseEntity<MutableMap<String, Any>> {
            return map(
                    Pair("code", httpStatus.value()),
                    Pair("status", httpStatus.reasonPhrase),
                    Pair("message", message))
        }

        fun map(vararg pairs: Pair<String, Any>): ResponseEntity<MutableMap<String, Any>> {
            return ResponseEntity.status(httpStatus).body(mutableMapOf(*pairs))
        }
    }

    companion object {
        fun success(httpStatus: HttpStatus = HttpStatus.OK): Successful {
            return Successful().status(httpStatus)
        }
        // 200
        fun ok(): Successful {
            return Successful().status(HttpStatus.OK)
        }
        // 201
        fun created(): Successful {
            return Successful().status(HttpStatus.CREATED)
        }
        // 204
        fun noContent(): Successful {
            return Successful().status(HttpStatus.NO_CONTENT)
        }
        // 400
        fun failed(httpStatus: HttpStatus = HttpStatus.BAD_REQUEST): Failed {
            return Failed().status(httpStatus)
        }
        // 400
        fun badRequest(): Failed {
            return Failed().status(HttpStatus.BAD_REQUEST)
        }
        // 401
        fun unauth(): Failed {
            return Failed().status(HttpStatus.UNAUTHORIZED)
        }
        // 403
        fun forbidden(): Failed {
            return Failed().status(HttpStatus.FORBIDDEN)
        }
        // 404
        fun notFound(): Failed {
            return Failed().status(HttpStatus.NOT_FOUND)
        }
    }
}