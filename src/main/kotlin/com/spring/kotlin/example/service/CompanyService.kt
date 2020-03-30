package com.spring.kotlin.example.service

import com.spring.kotlin.example.document.Company
import com.spring.kotlin.example.repository.CompanyRepository
import org.springframework.stereotype.Service

@Service
class CompanyService(val companyRepository: CompanyRepository) {

    fun findByDocument(document: String): Company? = companyRepository.findByDocument(document)

    fun persist(company: Company) = companyRepository.save(company)

}