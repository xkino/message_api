package com.example.messenger.api.controllers

import com.example.messenger.api.components.MessageAssembler
import com.example.messenger.api.helpers.objects.MessageVO
import com.example.messenger.api.repositories.UserRepository
import com.example.messenger.api.services.MessageService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/messages")
class MessageController(
    val messageService: MessageService,
    val userRepository: UserRepository,
    val messageAssembler: MessageAssembler,
) {
    fun create(@RequestBody messageDetail: MessageRequest, request: HttpServletRequest): ResponseEntity<MessageVO> {
        val principal = request.userPrincipal
        val sender = userRepository.findByUsername(principal.name)
        val message = messageService.sendMessage(sender!!, messageDetail.recipientId, messageDetail.message)
        return ResponseEntity.ok(messageAssembler.toMessageVO(message))
    }

    data class MessageRequest(val recipientId: Long, val message: String)
}