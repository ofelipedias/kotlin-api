package com.spring.kotlin.example.extension

import com.spring.kotlin.example.document.Company
import com.spring.kotlin.example.document.Employee
import com.spring.kotlin.example.dto.CompanyRegisterDto
import com.spring.kotlin.example.enum.Profile
import com.spring.kotlin.example.utils.PasswordUtils

fun CompanyRegisterDto.toEmployee(companyId: String): Employee {
    return Employee(name, email, PasswordUtils().passwordEncrypt(password), cpf, Profile.ROLE_ADMIN, companyId)
}

fun CompanyRegisterDto.toCompany(): Company {
    return Company(socialReason, cnpj)
}