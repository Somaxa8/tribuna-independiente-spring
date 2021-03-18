package com.somacode.tribunaindependiente.controller

import com.somacode.tribunaindependiente.entity.Interview
import com.somacode.tribunaindependiente.service.InterviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class InterviewController {

    @Autowired lateinit var interviewService: InterviewService


    @PostMapping("/api/interview")
    fun postInterview(
            @RequestParam title: String,
            @RequestParam body: String,
            @RequestParam videoUrl: String
    ): ResponseEntity<Interview> {
        return ResponseEntity.status(HttpStatus.CREATED).body(interviewService.create(title, body, videoUrl))
    }

    @PatchMapping("/api/interview/{id}")
    fun pathInterview(
            @PathVariable id: Long,
            @RequestParam(required = false) title: String?,
            @RequestParam(required = false) body: String?,
            @RequestParam(required = false) videoUrl: String?
    ): ResponseEntity<Interview> {
        return ResponseEntity.status(HttpStatus.OK).body(interviewService.update(id, title, body, videoUrl))
    }

    @DeleteMapping("/api/interview/{id}")
    fun deleteInterview(@PathVariable id: Long): ResponseEntity<Void> {
        interviewService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/interview")
    fun getInterviews(): ResponseEntity<List<Interview>> {
        return ResponseEntity.status(HttpStatus.OK).body(interviewService.findAll())
    }

    @GetMapping("/public/interview/{id}")
    fun getInterview(@PathVariable id: Long): ResponseEntity<Interview> {
        return ResponseEntity.status(HttpStatus.OK).body(interviewService.findById(id))
    }

}