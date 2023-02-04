package com.example.messenger.api.components

import com.example.messenger.api.helpers.objects.ConversationListVO
import com.example.messenger.api.helpers.objects.ConversationVO
import com.example.messenger.api.helpers.objects.MessageVO
import com.example.messenger.api.models.Conversation
import com.example.messenger.api.services.ConversationService
import org.springframework.stereotype.Component

@Component
class ConversationAssembler(
    val conversationService: ConversationService,
    val messageAssembler: MessageAssembler
) {
    fun toConversationVO(conversation: Conversation, userID: Long): ConversationVO {
        val conversationMessages = ArrayList<MessageVO>()
        conversation.messages?.mapTo(conversationMessages) {
            messageAssembler.toMessageVO(it)
        }
        return ConversationVO(
            conversationId = conversation.id!!,
            secondPartyUsername = conversationService.nameSecondParty(conversation, userID),
            messages = conversationMessages
        )
    }

    fun toConversationListVO(conversations: List<Conversation>, userID: Long): ConversationListVO {
        val conversationVOList = conversations.map { toConversationVO(it, userID) }
        return ConversationListVO(conversationVOList)
    }

}