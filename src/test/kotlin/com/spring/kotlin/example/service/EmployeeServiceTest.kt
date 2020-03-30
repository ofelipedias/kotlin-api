package com.spring.kotlin.example.service

import com.spring.kotlin.example.document.Employee
import com.spring.kotlin.example.enum.Profile
import com.spring.kotlin.example.repository.EmployeeRepository
import com.spring.kotlin.example.utils.PasswordUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.annotation.DirtiesContext
import java.util.*

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
internal class EmployeeServiceTest() {

    @Autowired
    val employeeService: EmployeeService? = null

    @MockBean
    val employeeRepository: EmployeeRepository? = null

    private val name: String = "employee1"
    private val email: String = "employee@email.com"
    private val password: String = "123"
    private val document: String = "00000000000000"
    private val id: String = "1"


    @BeforeEach
    fun setup() {
        BDDMockito.given(employeeRepository?.save(Mockito.any(Employee::class.java))).willReturn(employee())
        BDDMockito.given(employeeRepository?.findByDocument(document)).willReturn(employee())
        BDDMockito.given(employeeRepository?.findByEmail(email)).willReturn(employee())
        BDDMockito.given(employeeRepository?.findById(id)).willReturn(Optional.of(employee()))
    }

    @Test
    fun findByDocumentTest() {
        val employee = employeeService?.findByDocument(document)
        Assertions.assertNotNull(employee)
    }

    @Test
    fun findByEmailTest() {
        val employee = employeeService?.findByEmail(email)
        Assertions.assertNotNull(employee)
    }

    @Test
    fun findByIdTest() {
        val employee = employeeService?.findById(id)
        Assertions.assertNotNull(employee)
    }

    @Test
    fun saveEmployeeTest() {
        val employee = employeeService?.persist(employee())
        Assertions.assertNotNull(employee)
    }

    private fun employee(): Employee = Employee(name, email, PasswordUtils().passwordEncrypt(password), document,
            Profile.ROLE_USER, id)
}