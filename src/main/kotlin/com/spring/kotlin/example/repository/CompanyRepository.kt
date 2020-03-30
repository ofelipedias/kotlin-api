package com.spring.kotlin.example.repository

import com.spring.kotlin.example.document.Company
import org.springframework.data.mongodb.repository.MongoRepository

interface CompanyRepository : MongoRepository<Company, String> {

    fun findByDocument(id: String): Company?

}