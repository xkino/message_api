package com.example.messenger.api.models

import jakarta.persistence.*
import org.springframework.format.annotation.DateTimeFormat
import java.time.Instant
import java.util.Date
//import javax.persistence.*

@Entity
class Message (
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var sender: User? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    var recipient: User? = null,

    var messageText: String? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    var conversation: Conversation? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @DateTimeFormat
    var createdAt: Date = Date.from(Instant.now()),
)