package com.spring.kotlin.example.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.spring.kotlin.example.document.Company
import com.spring.kotlin.example.document.Employee
import com.spring.kotlin.example.enum.Profile
import com.spring.kotlin.example.extension.toDto
import com.spring.kotlin.example.repository.CompanyRepository
import com.spring.kotlin.example.repository.EmployeeRepository
import com.spring.kotlin.example.utils.PasswordUtils
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
internal class RegisterControllerTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val companyRepository: CompanyRepository? = null

    @MockBean
    private val employeeRepository: EmployeeRepository? = null

    // request data
    private val baseUrl: String = "/register"
    private val companyUrl: String = "/company"
    private val personUrl: String = "/person"

    // company data
    private val companyName: String = "company1"
    private val companyDocument: String = "29446002000190"
    private val companyId: String = "001"

    // employee data
    private val emppoyName: String = "employee1"
    private val email: String = "employee@email.com"
    private val password: String = "123"
    private val employeeDocument: String = "36636310014"

    @Test
    @WithMockUser(username = "admin@admin.com.br", roles = arrayOf("ADMIN"))
    fun companyRegister() {
        val company = company()
        val employee = employee()

        BDDMockito.given(companyRepository?.save(Mockito.any(Company::class.java))).willReturn(company)
        BDDMockito.given(employeeRepository?.save(Mockito.any(Employee::class.java))).willReturn(employee)

        mockMvc!!.perform(MockMvcRequestBuilders.post("${baseUrl}${companyUrl}")
                .content(ObjectMapper().writeValueAsString(company.toDto(employee)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty)
    }

    @Test
    @WithMockUser
    fun personRegister() {
        val company = company()
        val employee = employee()

        BDDMockito.given(companyRepository?.findByDocument(company.document)).willReturn(company)
        BDDMockito.given(employeeRepository?.save(Mockito.any(Employee::class.java))).willReturn(employee)

        mockMvc!!.perform(MockMvcRequestBuilders.post("${baseUrl}${personUrl}")
                .content(ObjectMapper().writeValueAsString(employee.toDto(company)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty)
    }

    private fun company(): Company = Company(companyName, companyDocument, companyId)

    private fun employee(): Employee = Employee(emppoyName, email, PasswordUtils().passwordEncrypt(password), employeeDocument,
            Profile.ROLE_USER, companyId)
}