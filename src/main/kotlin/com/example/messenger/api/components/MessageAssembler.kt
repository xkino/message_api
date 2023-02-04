package com.example.messenger.api.components

import com.example.messenger.api.helpers.objects.MessageVO
import com.example.messenger.api.models.Message
import org.springframework.stereotype.Component

@Component
class MessageAssembler {
    fun toMessageVO(message: Message): MessageVO {
        return MessageVO(
            id = message.id!!,
            senderId = message.sender?.id,
            recipientId = message.recipient?.id,
            conversationId = message.conversation?.id,
            body = message.messageText,
            createdAt = message.createdAt.toString()
        )
    }

}