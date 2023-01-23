package com.example.messenger.api.services

import com.example.messenger.api.models.Conversation
import com.example.messenger.api.models.User

interface ConversationService {
    fun conversationExists(sender: User, recipient: User): Boolean

    fun getConversation(sender: User, recipient: User): Conversation?

    fun createConversation(sender: User, recipient: User): Conversation

    fun retrieveThread(conversationId: Long): Conversation

    fun listUserConversations(userId: Long): List<Conversation>

    fun nameSecondParty(conversation: Conversation, userId: Long): String
}