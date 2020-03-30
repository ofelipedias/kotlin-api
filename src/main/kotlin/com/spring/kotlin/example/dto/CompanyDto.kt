package com.spring.kotlin.example.dto

data class CompanyDto(
        val name: String,
        val document: String,
        val id: String? = null
)