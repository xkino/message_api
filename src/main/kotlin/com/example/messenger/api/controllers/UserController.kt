package com.example.messenger.api.controllers

import MessengerApiException
import com.example.messenger.api.components.UserAssembler
import com.example.messenger.api.helpers.objects.UserListVO
import com.example.messenger.api.helpers.objects.UserVO
import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.UserRepository
import com.example.messenger.api.services.UserServices
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    val userServices: UserServices,
    val userRepository: UserRepository,
    val userAssembler: UserAssembler,
) {

    @PostMapping("/registrations")
    fun create(@Validated @RequestBody userDetails: User): ResponseEntity<UserVO> {
        val user = userServices.attemptRegistration(userDetails)
        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping("/{user_id}")
    fun show(@PathVariable("user_id") userId: Long): ResponseEntity<UserVO> {
        val user = userServices.retrieveUserData(userId)
        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping("/details")
    fun echoDetails(request: HttpServletRequest): ResponseEntity<UserVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name)
            ?: throw MessengerApiException("request.userPrincipal.name must not be null")
        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping
    fun index(request: HttpServletRequest): ResponseEntity<UserListVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name)
            ?: throw MessengerApiException("request.userPrincipal.name must not be null")
        val users = userServices.listUsers(user)
        return ResponseEntity.ok(userAssembler.toUserListVO(users))
    }

    @PutMapping
    fun update(
        @RequestBody updateDetails: User,
        request: HttpServletRequest
    ): ResponseEntity<UserVO> {
        val currentUser = userRepository.findByUsername(request.userPrincipal.name)
            ?: throw MessengerApiException("request.userPrincipal.name must not be null")
        userServices.updateUserStatus(currentUser, updateDetails)
        return ResponseEntity.ok(userAssembler.toUserVO(currentUser))
    }


}