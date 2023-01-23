package com.example.messenger.api.models

import com.example.messenger.api.listeners.UserListener
import jakarta.persistence.*
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat
import java.time.Instant
import java.util.Date

@Entity
@Table(name = "sys_user")
@EntityListeners(UserListener::class)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(unique = true)
    @Size(min = 2)
    var username: String? = null,

    @Size(min = 11)
    @Column(unique = true)
    @Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$")
    var phoneNumber: String? = null,

    @Size(min = 60, max = 60)
    var password: String? = null,

    var status: String = "available",

    @Pattern(regexp = "\\A(activated|deactivated)\\z")
    var accountStatus: String = "activated",

    @DateTimeFormat
    var createdAd: Date = Date.from(Instant.now()),
) {
    // отправленные сообщения
    @OneToMany(mappedBy = "sender", targetEntity = Message::class)
    private var sentMessages: List<Message>? = null

    // полученные сообщения
    @OneToMany(mappedBy = "recipient", targetEntity = Message::class)
    private var receivedMessages: List<Message>? = null
}
