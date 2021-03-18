package com.somacode.tribunaindependiente.entity.model

import com.somacode.tribunaindependiente.entity.Authority
import com.somacode.tribunaindependiente.entity.User
import org.springframework.security.oauth2.common.OAuth2AccessToken

data class LoginResponse(
        var oAuth2AccessToken: OAuth2AccessToken,
        var user: User,
        var authorities: Set<Authority>
)