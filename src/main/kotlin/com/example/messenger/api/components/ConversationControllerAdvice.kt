package com.example.messenger.api.components

import ConversationIdInvalidException
import com.example.messenger.api.constants.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ConversationControllerAdvice {

    @ExceptionHandler
    fun conversationIdInvalidException(
        conversationIdInvalidException: ConversationIdInvalidException
    ): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse("", conversationIdInvalidException.message)

        return ResponseEntity.unprocessableEntity().body(res)
    }
}