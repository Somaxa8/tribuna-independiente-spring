package com.somacode.tribunaindependiente.controller

import com.somacode.tribunaindependiente.entity.Interview
import com.somacode.tribunaindependiente.service.InterviewService
import com.somacode.tribunaindependiente.service.tool.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class InterviewController {

    @Autowired lateinit var interviewService: InterviewService


    @PostMapping("/api/interview")
    fun postInterview(
            @RequestParam title: String,
            @RequestParam body: String,
            @RequestParam videoUrl: String,
            @RequestParam imageFile: MultipartFile
    ): ResponseEntity<Interview> {
        return ResponseEntity.status(HttpStatus.CREATED).body(interviewService.create(title, body, videoUrl, imageFile))
    }

    @PatchMapping("/api/interview/{id}")
    fun patchInterview(
            @PathVariable id: Long,
            @RequestParam(required = false) title: String?,
            @RequestParam(required = false) body: String?,
            @RequestParam(required = false) videoUrl: String?,
            @RequestParam(required = false) imageFile: MultipartFile?
    ): ResponseEntity<Interview> {
        return ResponseEntity.status(HttpStatus.OK).body(interviewService.update(id, title, body, videoUrl, imageFile))
    }

    @DeleteMapping("/api/interview/{id}")
    fun deleteInterview(@PathVariable id: Long): ResponseEntity<Void> {
        interviewService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/interview")
    fun getInterviews(
            @RequestParam(required = false) search: String?,
            @RequestParam page: Int,
            @RequestParam size: Int
    ): ResponseEntity<List<Interview>> {
        val result = interviewService.findFilterPageable(page, size, search)
        return ResponseEntity.status(HttpStatus.OK)
                .header(Constants.X_TOTAL_COUNT_HEADER, result.totalElements.toString())
                .body(result.content)
    }

    @GetMapping("/public/interview/{id}")
    fun getInterview(@PathVariable id: Long): ResponseEntity<Interview> {
        return ResponseEntity.status(HttpStatus.OK).body(interviewService.findById(id))
    }

}