package com.example.messenger.api.services

import ConversationIdInvalidException
import com.example.messenger.api.models.Conversation
import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.ConversationRepository
import org.springframework.stereotype.Service

@Service
class ConversationServiceImpl(
    private val repository: ConversationRepository
) : ConversationService {
    override fun conversationExists(sender: User, recipient: User): Boolean {
        return if (repository.findBySenderIdAndRecipientId(sender.id!!, recipient.id!!) != null) true
        else repository.findBySenderIdAndRecipientId(recipient.id!!, sender.id!!) != null
    }

    override fun getConversation(sender: User, recipient: User): Conversation? {
        return when {
            repository.findBySenderIdAndRecipientId(
                sender.id!!,
                recipient.id!!
            ) != null -> repository.findBySenderIdAndRecipientId(
                sender.id!!,
                recipient.id!!
            )

            repository.findBySenderIdAndRecipientId(
                recipient.id!!,
                sender.id!!,
            ) != null -> repository.findBySenderIdAndRecipientId(
                recipient.id!!,
                sender.id!!,
            )

            else -> null
        }
    }

    override fun createConversation(sender: User, recipient: User): Conversation {
        val conversation = Conversation(
            sender = sender,
            recipient = recipient
        )
        repository.save(conversation)
        return conversation
    }

    override fun retrieveThread(conversationId: Long): Conversation {
        val conversation = repository.findById(conversationId)
        if (conversation.isPresent) {
            return conversation.get()
        }
        throw ConversationIdInvalidException("Invalid conversation id '$conversationId")
    }

    override fun listUserConversations(userId: Long): List<Conversation> {
        val conversationsList: ArrayList<Conversation> = ArrayList()
        conversationsList.addAll(repository.findBySenderId(userId))
        conversationsList.addAll(repository.findByRecipientId(userId))
        return conversationsList
    }

    override fun nameSecondParty(conversation: Conversation, userId: Long): String {
        return if (conversation.sender?.id == userId) {
            conversation.recipient?.username as String
        } else {
            conversation.sender?.username as String
        }
    }
}