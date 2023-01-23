package com.example.messenger.api.components

import MessengerApiException
import com.example.messenger.api.constants.ErrorResponse
import com.example.messenger.api.constants.ResponseConstants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class MessageApiControllerAdvice {

    @ExceptionHandler
    fun messageApiException(
        messageApiException: MessengerApiException
    ): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.MESSAGE_API_EXCEPTION.value,
            messageApiException.message,
        )
        return ResponseEntity.unprocessableEntity().body(res)
    }
}