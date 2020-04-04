package com.spring.kotlin.example.service

import com.spring.kotlin.example.document.Launcher
import com.spring.kotlin.example.dto.LauncherDto
import com.spring.kotlin.example.exception.ServiceException
import com.spring.kotlin.example.extension.isValidDate
import com.spring.kotlin.example.repository.LauncherRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class LauncherService(val launcherRepository: LauncherRepository,
                      val employeeService: EmployeeService) {

    fun findByEmployeeId(employeeId: String, pageRequest: PageRequest): Page<Launcher> = launcherRepository.findByEmployeeId(employeeId, pageRequest)

    fun findById(id: String): Launcher = launcherRepository.findById(id).orElseThrow { ServiceException("Launcher not found by ID") }

    fun persist(launcher: Launcher): Launcher = launcherRepository.save(launcher)

    fun remove(id: String) = launcherRepository.deleteById(id)

    fun validate(launcherDto: LauncherDto) {
        if (launcherDto.employeeId.isNullOrEmpty()) throw ServiceException("Employee ID is empty")
        employeeService.findById(launcherDto.employeeId)
        if (!launcherDto.date!!.isValidDate(DateTimeFormatter.ISO_LOCAL_DATE_TIME)) throw ServiceException("Invalid date time format")
    }
}