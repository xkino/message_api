package com.example.messenger.api.components

import com.example.messenger.api.helpers.objects.UserListVO
import com.example.messenger.api.helpers.objects.UserVO
import com.example.messenger.api.models.User
import org.springframework.stereotype.Component

@Component
class UserAssembler {

    fun toUserVO(user: User?): UserVO {
        return UserVO(
            user?.id!!,
            user.username!!,
            user.phoneNumber!!,
            user.status,
            user.createdAd.toString()
        )
    }

    fun toUserListVO(users: List<User>): UserListVO {
        val userVOList = users.map { toUserVO(it) }
        return UserListVO(userVOList)
    }
}