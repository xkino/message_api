package com.example.messenger.api.components

import UserDeactivatedException
import com.example.messenger.api.constants.ErrorResponse
import com.example.messenger.api.constants.ResponseConstants
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class RestControllerAdvice {

    fun userDeactivated(userDeactivatedException: UserDeactivatedException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.ACCOUNT_DEACTIVATED.value,
            userDeactivatedException.message
        )

        return ResponseEntity(res, HttpStatus.UNAUTHORIZED)
    }
}