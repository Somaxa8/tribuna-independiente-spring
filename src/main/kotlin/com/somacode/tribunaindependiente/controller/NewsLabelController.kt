package com.somacode.tribunaindependiente.controller

import com.somacode.tribunaindependiente.entity.NewsLabel
import com.somacode.tribunaindependiente.service.NewsLabelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class NewsLabelController {

    @Autowired lateinit var newsLabelService: NewsLabelService


    @PostMapping("/api/news-label")
    fun postNewsLabel(
            @RequestParam title: String
    ): ResponseEntity<NewsLabel> {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsLabelService.create(title))
    }

    @DeleteMapping("/api/news-label/{id}")
    fun deleteNewsLabel(@PathVariable id: Long): ResponseEntity<Void> {
        newsLabelService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/news-label")
    fun getNewsLabels(): ResponseEntity<List<NewsLabel>> {
        return ResponseEntity.status(HttpStatus.OK).body(newsLabelService.findAll())
    }

    @GetMapping("/public/news-label/{id}")
    fun getNewsLabel(@PathVariable id: Long): ResponseEntity<NewsLabel> {
        return ResponseEntity.status(HttpStatus.OK).body(newsLabelService.findById(id))
    }

}