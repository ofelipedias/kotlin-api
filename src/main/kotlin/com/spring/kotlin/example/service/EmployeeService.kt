package com.spring.kotlin.example.service

import com.spring.kotlin.example.document.Employee
import com.spring.kotlin.example.repository.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeService(val employeeRepository: EmployeeRepository) {

    fun findById(id: String): Employee? = employeeRepository.findById(id).orElse(null)

    fun findByDocument(document: String): Employee? = employeeRepository.findByDocument(document)

    fun findByEmail(email: String): Employee? = employeeRepository.findByEmail(email)

    fun persist(employee: Employee) = employeeRepository.save(employee)

}