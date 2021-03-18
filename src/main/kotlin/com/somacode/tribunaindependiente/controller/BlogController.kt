package com.somacode.tribunaindependiente.controller

import com.somacode.tribunaindependiente.entity.Blog
import com.somacode.tribunaindependiente.service.BlogService
import com.somacode.tribunaindependiente.service.tool.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class BlogController {

    @Autowired lateinit var blogService: BlogService


    @PostMapping("/api/blog")
    fun postBlog(
            @RequestParam title: String,
            @RequestParam body: String,
            @RequestParam imageFile: MultipartFile
    ): ResponseEntity<Blog> {
        return ResponseEntity.status(HttpStatus.CREATED).body(blogService.create(title, body, imageFile))
    }

    @PatchMapping("/api/blog/{id}")
    fun patchBlog(
            @PathVariable id: Long,
            @RequestParam(required = false) title: String?,
            @RequestParam(required = false) body: String?,
            @RequestParam(required = false) imageFile: MultipartFile?
    ): ResponseEntity<Blog> {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.update(id, title, body, imageFile))
    }

    @DeleteMapping("/api/blog/{id}")
    fun deleteBlog(@PathVariable id: Long): ResponseEntity<Void> {
        blogService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/blog")
    fun getBlogs(
            @RequestParam(required = false) search: String?,
            @RequestParam page: Int,
            @RequestParam size: Int
    ): ResponseEntity<List<Blog>> {
        val result = blogService.findFilterPageable(page, size, search)
        return ResponseEntity.status(HttpStatus.OK)
                .header(Constants.X_TOTAL_COUNT_HEADER, result.totalElements.toString())
                .body(result.content)
    }

    @GetMapping("/public/blog/{id}")
    fun getBlog(@PathVariable id: Long): ResponseEntity<Blog> {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.findById(id))
    }

}