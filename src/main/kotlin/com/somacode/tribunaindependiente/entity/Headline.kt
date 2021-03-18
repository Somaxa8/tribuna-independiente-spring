package com.somacode.tribunaindependiente.entity

import javax.persistence.*

@Entity
class Headline(
        @Id @GeneratedValue
        var id: Long? = null,
        var body: String? = null,
        var hour: String? = null
)