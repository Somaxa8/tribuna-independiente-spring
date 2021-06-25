package com.somacode.tribunaindependiente.service

import com.somacode.tribunaindependiente.entity.Cartoon
import com.somacode.tribunaindependiente.repository.CartoonRepository
import com.somacode.tribunaindependiente.config.exception.NotFoundException
import com.somacode.tribunaindependiente.entity.Document
import com.somacode.tribunaindependiente.entity.Interview
import com.somacode.tribunaindependiente.repository.criteria.CartoonCriteria
import com.somacode.tribunaindependiente.service.tool.FakerTool
import com.somacode.tribunaindependiente.service.tool.MockTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class CartoonService {

    @Autowired lateinit var cartoonRepository: CartoonRepository
    @Autowired lateinit var cartoonCriteria: CartoonCriteria
    @Autowired lateinit var documentService: DocumentService
    @Autowired lateinit var mockTool: MockTool
    @Value("\${custom.mock}") var mock: Boolean = false

    fun init() {
        if (mock) {
            println("CartoonService init()")
            val faker = FakerTool.faker
            for (i in 1..20) {
                create(faker.lorem().characters(1, 18), faker.lorem().paragraph(20), mockTool.multipartFileImage())
            }
        }
    }

    fun create(title: String, body: String, imageFile: MultipartFile): Cartoon {
        if (title.isBlank() && body.isBlank()) {
            throw IllegalArgumentException()
        }
        val cartoon = Cartoon(
                title = title,
                body = body,
                image = documentService.create(imageFile, Document.Type.IMAGE, Cartoon::class.java.simpleName, null)
        )

        return cartoonRepository.save(cartoon)
    }

    fun update(id: Long, title: String?, body: String?, imageFile: MultipartFile?): Cartoon {
        val cartoon = findById(id)

        title?.let { cartoon.title = it }
        body?.let { cartoon.body = it }
        imageFile?.let { cartoon.image = documentService.create(it, Document.Type.IMAGE, Cartoon::class.java.simpleName, null) }

        return cartoonRepository.save(cartoon)
    }

    fun findById(id: Long): Cartoon {
        if (!cartoonRepository.existsById(id)) {
            throw NotFoundException()
        }
        return cartoonRepository.getOne(id)
    }

    fun delete(id: Long) {
        if (!cartoonRepository.existsById(id)) {
            throw NotFoundException()
        }
        val cartoon = findById(id)
        cartoon.image?.let { documentService.delete(it.id!!) }
        cartoonRepository.deleteById(id)
    }

    fun findFilterPageable(page: Int, size: Int, search: String?): Page<Cartoon> {
        return cartoonCriteria.findFilterPageable(page, size, search)
    }
}