package com.spring.kotlin.example.document

import com.spring.kotlin.example.enum.Profile
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Employee(
        val name: String,
        val email: String,
        val password: String,
        val document: String,
        val profile: Profile,
        val companyId: String,
        val hourValue: Double? = 0.0,
        val hourOfWorkInDay: Float? = 0.0f,
        val hourOfLunch: Float? = 0.0f,
        @Id val id: String? = null
)