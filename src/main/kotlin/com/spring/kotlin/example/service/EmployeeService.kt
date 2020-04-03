package com.spring.kotlin.example.service

import com.spring.kotlin.example.document.Company
import com.spring.kotlin.example.document.Employee
import com.spring.kotlin.example.exception.ServiceException
import com.spring.kotlin.example.repository.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmployeeService(val employeeRepository: EmployeeRepository) {

    fun findById(id: String): Employee = employeeRepository.findById(id).orElseThrow() { ServiceException("Employee not found by ID") }

    fun findByDocument(document: String): Employee? = employeeRepository.findByDocument(document)

    fun findByEmail(email: String): Employee? = employeeRepository.findByEmail(email)

    fun persist(employee: Employee) = employeeRepository.save(employee)

    fun validate(company: Company?, cpf: String, email: String) {
        if (Objects.isNull(company)) throw ServiceException("Company not registered")
        if (findByDocument(cpf) != null) throw ServiceException("CPF already in use")
        if (findByEmail(email) != null) throw ServiceException("Email already in use")
    }
}