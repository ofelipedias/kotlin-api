package com.spring.kotlin.example.extension

import com.spring.kotlin.example.document.Launcher
import com.spring.kotlin.example.dto.LauncherDto
import com.spring.kotlin.example.enum.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Launcher.toDto(): LauncherDto {
    return LauncherDto(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), type.name, employeeId, description, location, id)
}