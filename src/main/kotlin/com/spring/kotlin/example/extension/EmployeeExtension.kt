package com.spring.kotlin.example.extension

import com.spring.kotlin.example.document.Company
import com.spring.kotlin.example.document.Employee
import com.spring.kotlin.example.dto.PersonRegisterDto

fun Employee.toDto(company: Company): PersonRegisterDto {
    return PersonRegisterDto(name, email, password, document, company.document, company.name, company.id!!,
            hourValue.toString(), hourOfWorkInDay.toString(), hourOfLunch.toString(), id)
}