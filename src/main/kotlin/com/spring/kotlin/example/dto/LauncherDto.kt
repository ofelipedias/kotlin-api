package com.spring.kotlin.example.dto

import javax.validation.constraints.NotEmpty

data class LauncherDto(

        @get:NotEmpty(message = "Date can't be empty")
        val date: String? = null,

        @get:NotEmpty(message = "Type can't be empty")
        val type: String? = null,

        val employeeId: String? = null,
        val description: String? = null,
        val location: String? = null,
        var id: String? = null
)
