package com.somacode.tribunaindependiente.repository

import com.somacode.tribunaindependiente.entity.Slider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SliderRepository : JpaRepository<Slider, Long> {

    fun findByOrderByIdAsc(): List<Slider>
}