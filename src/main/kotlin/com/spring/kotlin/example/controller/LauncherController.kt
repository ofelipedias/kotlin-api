package com.spring.kotlin.example.controller

import com.spring.kotlin.example.document.Launcher
import com.spring.kotlin.example.dto.LauncherDto
import com.spring.kotlin.example.extension.dateValidate
import com.spring.kotlin.example.extension.hasErrors
import com.spring.kotlin.example.extension.toDto
import com.spring.kotlin.example.extension.toEntity
import com.spring.kotlin.example.service.EmployeeService
import com.spring.kotlin.example.service.LauncherService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/launchers")
class LauncherController(val launcherService: LauncherService,
                         val employeeService: EmployeeService) {

    @PostMapping
    fun create(@Valid @RequestBody launcherDto: LauncherDto, bindingResult: BindingResult): ResponseEntity<Response<LauncherDto>> {
        val response: Response<LauncherDto> = Response()
        employeeValidate(launcherDto, bindingResult)
        launcherDto.dateValidate(bindingResult)

        if (bindingResult.hasErrors(response)) return ResponseEntity.badRequest().body(response)
        if (notFoundLauncher(launcherDto.id, response)) return ResponseEntity.badRequest().body(response)

        val launcher = launcherService.persist(launcherDto.toEntity())
        response.data = launcher.toDto()
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String): ResponseEntity<Response<LauncherDto>> {
        val response = Response<LauncherDto>()
        val launcher: Launcher? = launcherService.findById(id)

        if (Objects.isNull(launcher)) {
            response.errors.add("Launcher not found by ID")
            return ResponseEntity.badRequest().body(response)
        }

        response.data = launcher!!.toDto()
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/employee/{id}")
    fun getByEmployeeId(@PathVariable("id") employeeId: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int,
                        @RequestParam("ord", defaultValue = "id") ord: String,
                        @RequestParam("dir", defaultValue = "DESC") dir: String): ResponseEntity<Response<Page<LauncherDto>>> {
        val response = Response<Page<LauncherDto>>()
        val launcherPage = launcherService.findByEmployeeId(employeeId, PageRequest.of(page, size, Sort.Direction.valueOf(dir), ord))
        response.data = launcherPage.map { l -> l.toDto() }
        return ResponseEntity.ok().body(response)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String, @Valid @RequestBody launcherDto: LauncherDto,
               bindingResult: BindingResult): ResponseEntity<Response<LauncherDto>> {
        val response = Response<LauncherDto>()
        employeeValidate(launcherDto, bindingResult)
        launcherDto.dateValidate(bindingResult)

        if (bindingResult.hasErrors(response)) return ResponseEntity.badRequest().body(response)
        if (notFoundLauncher(id, response)) return ResponseEntity.badRequest().body(response)

        launcherDto.id = id
        response.data = launcherService.persist(launcherDto.toEntity()).toDto()
        return ResponseEntity.ok().body(response)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun delete(@PathVariable("id") id: String): ResponseEntity<Response<LauncherDto>> {
        val response = Response<LauncherDto>()
        val launcher = launcherService.findById(id)

        if (Objects.isNull(launcher)) {
            response.errors.add("Launcher not found by ID")
            return ResponseEntity.badRequest().body(response)
        }

        launcherService.remove(id)
        return ResponseEntity.ok().body(response)
    }

    fun employeeValidate(launcherDto: LauncherDto, bindingResult: BindingResult) {
        if (launcherDto.employeeId.isNullOrEmpty()) {
            bindingResult.addError(ObjectError("employee", "Employee ID is empty"))
            return
        }
        val employee = employeeService.findById(launcherDto.employeeId)
        if (Objects.isNull(employee)) {
            bindingResult.addError(ObjectError("employee", "Employee not found by ID=${launcherDto.employeeId}"))
        }

    }

    private fun notFoundLauncher(launcherId: String?, response: Response<LauncherDto>): Boolean {
        if (!launcherId.isNullOrEmpty()) {
            val launcher: Launcher? = launcherService.findById(launcherId)
            if (Objects.isNull(launcher)) {
                response.errors.add("Launcher not found by ID=${launcherId}")
                return true
            }
        }
        return false
    }

}
