package com.somacode.tribunaindependiente.controller

import com.somacode.tribunaindependiente.entity.Slider
import com.somacode.tribunaindependiente.service.SliderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class SliderController {

    @Autowired lateinit var sliderService: SliderService


    @PostMapping("/api/slider")
    fun postSlider(
            @RequestParam title: String,
            @RequestParam imageFile: MultipartFile,
            @RequestParam(required = false) url: String?,
            @RequestParam(required = false) location: Int?
    ): ResponseEntity<Slider> {
        return ResponseEntity.status(HttpStatus.CREATED).body(sliderService.create(title, url, imageFile, location))
    }

    @DeleteMapping("/api/slider/{id}")
    fun deleteSlider(@PathVariable id: Long): ResponseEntity<Void> {
        sliderService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/public/slider")
    fun getSliders(): ResponseEntity<List<Slider>> {
        return ResponseEntity.status(HttpStatus.OK).body(sliderService.findAll())
    }

    @GetMapping("/public/slider/{id}")
    fun getSlider(@PathVariable id: Long): ResponseEntity<Slider> {
        return ResponseEntity.status(HttpStatus.OK).body(sliderService.findById(id))
    }

}