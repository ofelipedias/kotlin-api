package com.spring.kotlin.example.repository

import com.spring.kotlin.example.document.Employee
import org.springframework.data.mongodb.repository.MongoRepository

interface EmployeeRepository : MongoRepository<Employee, String> {

    fun findByEmail(email: String): Employee?

    fun findByDocument(document: String): Employee?
}