package com.spring.kotlin.example.controller

import com.spring.kotlin.example.document.Launcher
import com.spring.kotlin.example.dto.LauncherDto
import com.spring.kotlin.example.extension.toDto
import com.spring.kotlin.example.extension.toEntity
import com.spring.kotlin.example.service.LauncherService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/launchers")
class LauncherController(val launcherService: LauncherService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Launcher create", authorizations = [Authorization(value = "basicAuth")])
    fun create(@Valid @RequestBody launcherDto: LauncherDto): Response<LauncherDto> {
        launcherService.validate(launcherDto)
        val launcher = launcherService.persist(launcherDto.toEntity())
        return Response(launcher.toDto())
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Launcher update", authorizations = [Authorization(value = "basicAuth")])
    fun update(@PathVariable("id") id: String, @Valid @RequestBody launcherDto: LauncherDto): Response<LauncherDto> {
        var launcher = launcherService.findById(id)
        launcherDto.id = launcher.id
        launcherService.validate(launcherDto)
        launcher = launcherService.persist(launcherDto.toEntity())
        return Response(launcher.toDto())
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get launcher by ID", authorizations = [Authorization(value = "basicAuth")])
    fun getById(@PathVariable("id") id: String): Response<LauncherDto> {
        val launcher: Launcher = launcherService.findById(id)
        return Response(launcher.toDto())
    }

    @GetMapping("/employee/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get launcher by employee ID", authorizations = [Authorization(value = "basicAuth")])
    fun getByEmployeeId(@PathVariable("id") employeeId: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int,
                        @RequestParam("ord", defaultValue = "id") ord: String,
                        @RequestParam("dir", defaultValue = "DESC") dir: String): Response<Page<LauncherDto>> {
        val launcherPage = launcherService.findByEmployeeId(employeeId, PageRequest.of(page, size, Sort.Direction.valueOf(dir), ord))
        return Response(launcherPage.map { l -> l.toDto() })
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Laucher remove", authorizations = [Authorization(value = "basicAuth")])
    fun delete(@PathVariable("id") id: String) {
        launcherService.findById(id)
        launcherService.remove(id)
    }
}
