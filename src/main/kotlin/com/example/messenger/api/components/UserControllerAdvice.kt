package com.example.messenger.api.components

import InvalidUserIdException
import UserStatusEmptyException
import UsernameUnavailableException
import com.example.messenger.api.constants.ErrorResponse
import com.example.messenger.api.constants.ResponseConstants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UserControllerAdvice {

    @ExceptionHandler(UsernameUnavailableException::class)
    fun usernameUnavailable(
        usernameUnavailableException: UsernameUnavailableException
    ): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.USERNAME_UNAVAILABLE.value,
            usernameUnavailableException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(InvalidUserIdException::class)
    fun invalidId(
        invalidUserIdException: InvalidUserIdException
    ): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.INVALID_USER_ID.value,
            invalidUserIdException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(UserStatusEmptyException::class)
    fun statusEmpty(
        userStatusEmptyException: UserStatusEmptyException
    ): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(
            ResponseConstants.EMPTY_STATUS.value,
            userStatusEmptyException.message
        )

        return ResponseEntity.unprocessableEntity().body(res)
    }
}