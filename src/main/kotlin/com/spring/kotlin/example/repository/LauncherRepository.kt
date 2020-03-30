package com.spring.kotlin.example.repository

import com.spring.kotlin.example.document.Launcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface LauncherRepository : MongoRepository<Launcher, String> {

    fun findByEmployeeId(employeeId: String, pageable: Pageable): Page<Launcher>

}