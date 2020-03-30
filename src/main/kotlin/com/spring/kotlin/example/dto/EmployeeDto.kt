package com.spring.kotlin.example.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class EmployeeDto(

        @get:NotEmpty(message = "Name can't be empty")
        @get:Length(min = 3, max = 200, message = "Name must contain between 3 and 200 characters")
        val name: String = "",

        @get:NotEmpty(message = "Email can't be empty")
        @get:Email(message = "Invalid email")
        val email: String = "",

        val password: String? = null,
        val hourValue: String? = null,
        val hourOfWorkInDay: String? = null,
        val hourOfLunch: String? = null,
        val id: String? = null
)