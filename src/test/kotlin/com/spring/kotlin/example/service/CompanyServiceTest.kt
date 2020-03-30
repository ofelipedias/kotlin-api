package com.spring.kotlin.example.service

import com.spring.kotlin.example.document.Company
import com.spring.kotlin.example.repository.CompanyRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
internal class CompanyServiceTest() {

    @Autowired
    val companyService: CompanyService? = null

    @MockBean
    val companyRepository: CompanyRepository? = null

    private val name: String = "Company1"
    private val document: String = "00000000000000"
    private val id: String = "1"

    @BeforeEach
    fun setup() {
        BDDMockito.given(companyRepository?.findByDocument(document)).willReturn(company())
        BDDMockito.given(companyRepository?.save(Mockito.any(Company::class.java))).willReturn(company())
    }

    @Test
    fun findByDocumentTest() {
        val company = companyService?.findByDocument(document)
        Assertions.assertNotNull(company)
    }

    @Test
    fun saveCompanyTest() {
        val company = companyService?.persist(company())
        Assertions.assertNotNull(company)
    }

    private fun company(): Company = Company(name, document, id)

}