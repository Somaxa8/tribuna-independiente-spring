package com.somacode.tribunaindependiente.controller

import com.somacode.tribunaindependiente.entity.Headline
import com.somacode.tribunaindependiente.service.HeadlineService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class HeadlineController {

    @Autowired lateinit var headlineService: HeadlineService


    @PostMapping("/api/headline")
    fun postHeadline(
            @RequestParam body: String,
            @RequestParam hour: String
    ): ResponseEntity<Headline> {
        return ResponseEntity.status(HttpStatus.CREATED).body(headlineService.create(body, hour))
    }

    @PatchMapping("/api/headline/{id}")
    fun pathHeadline(
            @PathVariable id: Long,
            @RequestParam(required = false) body: String?,
            @RequestParam(required = false) hour: String?
    ): ResponseEntity<Headline> {
        return ResponseEntity.status(HttpStatus.OK).body(headlineService.update(id, body, hour))
    }

    @DeleteMapping("/api/headline/{id}")
    fun deleteHeadline(@PathVariable id: Long): ResponseEntity<Void> {
        headlineService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/headline")
    fun getHeadlines(): ResponseEntity<List<Headline>> {
        return ResponseEntity.status(HttpStatus.OK).body(headlineService.findAll())
    }

    @GetMapping("/public/headline/{id}")
    fun getHeadline(@PathVariable id: Long): ResponseEntity<Headline> {
        return ResponseEntity.status(HttpStatus.OK).body(headlineService.findById(id))
    }

}