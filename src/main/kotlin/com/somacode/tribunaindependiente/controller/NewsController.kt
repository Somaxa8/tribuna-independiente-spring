package com.somacode.tribunaindependiente.controller

import com.somacode.tribunaindependiente.entity.News
import com.somacode.tribunaindependiente.service.NewsService
import com.somacode.tribunaindependiente.service.tool.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class NewsController {

    @Autowired lateinit var newsService: NewsService


    @PostMapping(value = ["/api/news", "/api/news-label/{labelId}/news"])
    fun postNews(
            @PathVariable(required = false) labelId: Long?,
            @RequestParam title: String,
            @RequestParam body: String,
            @RequestParam imageFile: MultipartFile,
            @RequestParam featured: Boolean
    ): ResponseEntity<News> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                newsService.create(title, body, imageFile, featured, labelId)
        )
    }

    @PatchMapping(value = ["/api/news/{id}", "/api/news-label/{labelId}/news/{id}"])
    fun patchNews(
            @PathVariable id: Long,
            @PathVariable(required = false) labelId: Long?,
            @RequestParam(required = false) title: String?,
            @RequestParam(required = false) body: String?,
            @RequestParam(required = false) imageFile: MultipartFile?,
            @RequestParam featured: Boolean
    ): ResponseEntity<News> {
        return ResponseEntity.status(HttpStatus.OK).body(
                newsService.update(id, title, body, imageFile, featured, labelId)
        )
    }

    @DeleteMapping("/api/news/{id}")
    fun deleteNews(@PathVariable id: Long): ResponseEntity<Void> {
        newsService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/news")
    fun getNewsPaginated(
            @RequestParam(required = false) search: String?,
            @RequestParam page: Int,
            @RequestParam size: Int,
            @RequestParam(required = false) labelId: Long?
    ): ResponseEntity<List<News>> {
        val result = newsService.findFilterPageable(page, size, search, labelId)
        return ResponseEntity.status(HttpStatus.OK)
                .header(Constants.X_TOTAL_COUNT_HEADER, result.totalElements.toString())
                .body(result.content)
    }

    @GetMapping("/public/news/{id}")
    fun getNews(@PathVariable id: Long): ResponseEntity<News> {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.findById(id))
    }

}