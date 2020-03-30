package com.spring.kotlin.example.extension

import com.spring.kotlin.example.document.Employee
import com.spring.kotlin.example.dto.PersonRegisterDto
import com.spring.kotlin.example.enum.Profile
import com.spring.kotlin.example.utils.PasswordUtils

fun PersonRegisterDto.toEmployee(companyId: String): Employee {
    return Employee(name, email, PasswordUtils().passwordEncrypt(password), cpf, Profile.ROLE_USER, companyId,
            hourValue?.toDouble(), hourOfWorkInDay?.toFloat(), hourOfLunch?.toFloat(), employeeId)
}