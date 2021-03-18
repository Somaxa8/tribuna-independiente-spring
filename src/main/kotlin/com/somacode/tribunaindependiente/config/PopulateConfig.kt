package com.somacode.tribunaindependiente.config

import com.somacode.tribunaindependiente.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PopulateConfig {

    @Autowired lateinit var authorityService: AuthorityService
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var blogService: BlogService
    @Autowired lateinit var newsService: NewsService
    @Autowired lateinit var newsLabelService: NewsLabelService


    fun init() {
        authorityService.init()
        userService.init()
        //blogService.init()
        newsLabelService.init()
        newsService.init()
    }

}