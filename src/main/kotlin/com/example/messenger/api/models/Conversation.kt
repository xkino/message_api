package com.example.messenger.api.models

import jakarta.persistence.*
import org.springframework.format.annotation.DateTimeFormat
import java.time.Instant
import java.util.*

@Entity
class Conversation(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    var sender: User? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    var recipient: User? = null,

    @DateTimeFormat
    var createdAt: Date = Date.from(Instant.now()),

    ) {
    @OneToMany(mappedBy = "conversation", targetEntity = Message::class)
    private var messages: List<Message>? = null
}