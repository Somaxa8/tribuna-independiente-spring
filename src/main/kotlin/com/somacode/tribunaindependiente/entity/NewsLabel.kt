package com.somacode.tribunaindependiente.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class NewsLabel(
        @Id @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        @JsonIgnore
        @OneToMany(mappedBy = "label")
        var news: List<News> = listOf()
)