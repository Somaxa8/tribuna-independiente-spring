package com.somacode.tribunaindependiente.service

import com.somacode.tribunaindependiente.entity.Cartoon
import com.somacode.tribunaindependiente.repository.CartoonRepository
import com.somacode.tribunaindependiente.config.exception.NotFoundException
import com.somacode.tribunaindependiente.entity.Document
import com.somacode.tribunaindependiente.entity.Interview
import com.somacode.tribunaindependiente.repository.criteria.CartoonCriteria
import org.springframework.beans.factory.annotation.Autowired
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


    fun init() {
        if (cartoonRepository.count() <= 0) {
            TODO("IMPLEMENT THIS")
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
        cartoonRepository.deleteById(id)
    }

    fun findFilterPageable(page: Int, size: Int, search: String?): Page<Cartoon> {
        return cartoonCriteria.findFilterPageable(page, size, search)
    }
}