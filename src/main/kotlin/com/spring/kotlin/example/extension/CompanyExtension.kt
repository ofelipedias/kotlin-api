package com.spring.kotlin.example.extension

import com.spring.kotlin.example.document.Company
import com.spring.kotlin.example.document.Employee
import com.spring.kotlin.example.dto.CompanyRegisterDto

fun Company.toDto(employee: Employee): CompanyRegisterDto {
    return CompanyRegisterDto(employee.name, employee.email, employee.password, employee.document, document, name, id)
}