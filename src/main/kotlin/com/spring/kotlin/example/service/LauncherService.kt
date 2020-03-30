package com.spring.kotlin.example.service

import com.spring.kotlin.example.document.Launcher
import com.spring.kotlin.example.repository.LauncherRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class LauncherService(val launcherRepository: LauncherRepository) {

    fun findByEmployeeId(employeeId: String, pageRequest: PageRequest): Page<Launcher> =
            launcherRepository.findByEmployeeId(employeeId, pageRequest)

    fun findById(id: String): Launcher? = launcherRepository.findById(id).orElse(null)

    fun persist(launcher: Launcher): Launcher = launcherRepository.save(launcher)

    fun remove(id: String) = launcherRepository.deleteById(id)

}