package com.spring.kotlin.example.document

import com.spring.kotlin.example.enum.Type
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Launcher(
        val date: LocalDateTime,
        val type: Type,
        val employeeId: String,
        val description: String? = "",
        val location: String? = "",
        @Id val id: String? = null
)