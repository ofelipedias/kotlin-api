package com.spring.kotlin.example.dto

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class CompanyRegisterDto(

        @get:NotEmpty(message = "Name can't be empty")
        @get:Length(min = 3, max = 200, message = "Name must contain between 3 and 200 characters")
        val name: String = "",

        @get:NotEmpty(message = "Email can't be empty")
        @get:Email(message = "Invalid email")
        val email: String = "",

        @get:NotEmpty(message = "Password can't be empty")
        val password: String = "",

        @get:NotEmpty(message = "CPF can't be empty")
        @get:CPF(message = "Invalid CPF")
        val cpf: String = "",

        @get:NotEmpty(message = "CNPJ can't be empty")
        @get:CNPJ(message = "Invalid CNPJ")
        val cnpj: String = "",

        @get:NotEmpty(message = "Social reason can't be empty")
        @get:Length(min = 5, max = 200, message = "Social reason must contain between 3 and 200 characters")
        val socialReason: String = "",

        val companyId: String? = null
)