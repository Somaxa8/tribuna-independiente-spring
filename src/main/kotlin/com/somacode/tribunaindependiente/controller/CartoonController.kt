package com.somacode.tribunaindependiente.controller

import com.somacode.tribunaindependiente.entity.Cartoon
import com.somacode.tribunaindependiente.entity.Interview
import com.somacode.tribunaindependiente.service.CartoonService
import com.somacode.tribunaindependiente.service.tool.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class CartoonController {

    @Autowired lateinit var cartoonService: CartoonService


    @PostMapping("/api/cartoon")
    fun postCartoon(
            @RequestParam title: String,
            @RequestParam body: String,
            @RequestParam imageFile: MultipartFile
    ): ResponseEntity<Cartoon> {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartoonService.create(title, body, imageFile))
    }

    @PatchMapping("/api/cartoon/{id}")
    fun patchCartoon(
            @PathVariable id: Long,
            @RequestParam title: String?,
            @RequestParam body: String?,
            @RequestParam imageFile: MultipartFile?
    ): ResponseEntity<Cartoon> {
        return ResponseEntity.status(HttpStatus.OK).body(cartoonService.update(id, title, body, imageFile))
    }

    @DeleteMapping("/api/cartoon/{id}")
    fun deleteCartoon(@PathVariable id: Long): ResponseEntity<Void> {
        cartoonService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/cartoon")
    fun getInterviews(
            @RequestParam(required = false) search: String?,
            @RequestParam page: Int,
            @RequestParam size: Int
    ): ResponseEntity<List<Cartoon>> {
        val result = cartoonService.findFilterPageable(page, size, search)
        return ResponseEntity.status(HttpStatus.OK)
                .header(Constants.X_TOTAL_COUNT_HEADER, result.totalElements.toString())
                .body(result.content)
    }

    @GetMapping("/public/cartoon/{id}")
    fun getCartoon(@PathVariable id: Long): ResponseEntity<Cartoon> {
        return ResponseEntity.status(HttpStatus.OK).body(cartoonService.findById(id))
    }

}