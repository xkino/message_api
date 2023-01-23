package com.example.messenger.api.services

import com.example.messenger.api.models.User

interface UserServices {
    fun attemptRegistration(userDetails: User): User

    fun listUsers(currentUser: User): List<User>

    fun retrieveUserData(username: String): User?

    fun retrieveUserData(id: Long): User?

    fun usernameExists(username: String): Boolean

    fun updateUserStatus(currentUser: User, updateDetails: User): User
}