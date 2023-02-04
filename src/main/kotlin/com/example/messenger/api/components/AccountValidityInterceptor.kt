package com.example.messenger.api.components

import UserDeactivatedException
import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequestInterceptor
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter
import java.security.Principal

@Component
class AccountValidityInterceptor(
    private val userRepository: UserRepository, requestInterceptor: WebRequestInterceptor
) : WebRequestHandlerInterceptorAdapter(requestInterceptor) {
    @Throws(UserDeactivatedException::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val principal: Principal? = request.userPrincipal
        if (principal != null) {
            val user = userRepository.findByUsername(principal.name) as User
            if (user.accountStatus == "deactivated") {
                throw UserDeactivatedException("The account of this user has been deactivated.")
            }
        }
        return super.preHandle(request, response, handler)
    }
}