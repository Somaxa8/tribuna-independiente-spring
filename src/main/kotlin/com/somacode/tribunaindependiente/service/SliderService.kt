package com.somacode.tribunaindependiente.service

import com.somacode.tribunaindependiente.config.exception.NotFoundException
import com.somacode.tribunaindependiente.entity.Document
import com.somacode.tribunaindependiente.entity.Slider
import com.somacode.tribunaindependiente.repository.SliderRepository
import com.somacode.tribunaindependiente.service.tool.FakerTool
import com.somacode.tribunaindependiente.service.tool.MockTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class SliderService {

    @Autowired lateinit var sliderRepository: SliderRepository
    @Autowired lateinit var documentService: DocumentService
    @Autowired lateinit var mockTool: MockTool
    @Value("\${custom.mock}") var mock: Boolean = false


    fun init() {
        val faker = FakerTool.faker
        if (mock) {
            println("SliderService init()")
            for (i in 1..4) {
                create(faker.pokemon().name(), faker.internet().url(), mockTool.multipartFileImage())
            }
        }
    }

    fun create(title: String, url: String?, imageFile: MultipartFile): Slider {
        if (title.isBlank()) throw IllegalArgumentException()

        val slider = Slider(
                title = title,
                image = documentService.create(
                        imageFile, Document.Type.IMAGE, Slider::class.java.simpleName, null
                )
        )

        url?.let { slider.url = it }

        return sliderRepository.save(slider)
    }

    fun findById(id: Long): Slider {
        if (!sliderRepository.existsById(id)) {
            throw NotFoundException()
        }
        return sliderRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!sliderRepository.existsById(id)) {
            throw NotFoundException()
        }
        val slider = findById(id)
        slider.image?.let { documentService.delete(it.id!!) }
        sliderRepository.deleteById(id)
    }

    fun findAll(): List<Slider> {
        return sliderRepository.findByOrderByIdAsc()
    }
}