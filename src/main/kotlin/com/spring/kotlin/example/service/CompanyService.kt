package com.spring.kotlin.example.service

import com.spring.kotlin.example.document.Company
import com.spring.kotlin.example.exception.ServiceException
import com.spring.kotlin.example.repository.CompanyRepository
import org.springframework.stereotype.Service

@Service
class CompanyService(val companyRepository: CompanyRepository) {

    fun findById(id: String): Company = companyRepository.findById(id).orElseThrow { ServiceException("Company not found by ID") }

    fun findByDocument(document: String): Company? = companyRepository.findByDocument(document)

    fun persist(company: Company) = companyRepository.save(company)

    fun validate(cnpj: String) {
        if (findByDocument(cnpj) != null) throw ServiceException("Company already exists")
    }
}