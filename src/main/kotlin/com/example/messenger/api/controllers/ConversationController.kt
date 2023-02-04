package com.example.messenger.api.controllers

import com.example.messenger.api.components.ConversationAssembler
import com.example.messenger.api.helpers.objects.ConversationListVO
import com.example.messenger.api.helpers.objects.ConversationVO
import com.example.messenger.api.repositories.UserRepository
import com.example.messenger.api.services.ConversationService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/conversation")
class ConversationController(
    val conversationService: ConversationService,
    val conversationAssembler: ConversationAssembler,
    val userRepository: UserRepository,
) {
    @GetMapping
    fun list(request: HttpServletRequest): ResponseEntity<ConversationListVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name)
        val conversations = conversationService.listUserConversations(user?.id!!)
        return ResponseEntity.ok(conversationAssembler.toConversationListVO(conversations, user.id!!))
    }

    @GetMapping("/{conversation_id}")
    fun show(
        @PathVariable(name = "conversation_id") conversationId: Long,
        request: HttpServletRequest
    ): ResponseEntity<ConversationVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name)
        val conversationThread = conversationService.retrieveThread(conversationId)
        return ResponseEntity.ok(conversationAssembler.toConversationVO(conversationThread, user?.id!!))
    }
}