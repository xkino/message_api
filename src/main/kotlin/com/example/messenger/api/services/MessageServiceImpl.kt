package com.example.messenger.api.services

import MessageEmptyException
import MessageRecipientInvalidException
import com.example.messenger.api.models.Message
import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.ConversationRepository
import com.example.messenger.api.repositories.MessageRepository
import com.example.messenger.api.repositories.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class MessageServiceImpl(
    private val repository: MessageRepository,
    private val conversationRepository: ConversationRepository,
    private val conversationService: ConversationService,
    private val userRepository: UserRepository
) : MessageService {

    @Throws(MessageEmptyException::class, MessageRecipientInvalidException::class)
    override fun sendMessage(sender: User, recipientId: Long, messageText: String): Message {
        val optional = userRepository.findById(recipientId)

        if (optional.isPresent) {
            val recipient = optional.get()

            if (messageText.isNotEmpty()) {
                val conversation = if (conversationService.conversationExists(sender, recipient)
                ) {
                    conversationService.getConversation(sender, recipient)
                } else {
                    conversationService
                        .createConversation(sender, recipient)
                }

                conversationRepository.save(conversation!!)

                val message = Message(
                    sender = sender,
                    recipient = recipient,
                    messageText = messageText,
                    conversation = conversation,
                )
                repository.save(message)
                return message
            } else {
                throw MessageRecipientInvalidException(
                    "The recipient id " +
                            "'$recipientId' is invalid."
                )
            }
        }


        throw MessageEmptyException()
    }
}