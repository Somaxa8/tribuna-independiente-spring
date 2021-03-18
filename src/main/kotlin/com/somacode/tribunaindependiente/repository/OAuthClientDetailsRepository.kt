package com.somacode.tribunaindependiente.repository

import com.somacode.tribunaindependiente.entity.oauth.OAuthClientDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OAuthClientDetailsRepository: JpaRepository<OAuthClientDetails, String> {

}