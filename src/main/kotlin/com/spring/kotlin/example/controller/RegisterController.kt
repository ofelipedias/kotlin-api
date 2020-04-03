package com.spring.kotlin.example.controller

import com.spring.kotlin.example.dto.CompanyRegisterDto
import com.spring.kotlin.example.dto.PersonRegisterDto
import com.spring.kotlin.example.extension.toCompany
import com.spring.kotlin.example.extension.toDto
import com.spring.kotlin.example.extension.toEmployee
import com.spring.kotlin.example.service.CompanyService
import com.spring.kotlin.example.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/register")
class RegisterController(val companyService: CompanyService, val employeeService: EmployeeService) {

    @PostMapping("/company")
    @ResponseStatus(HttpStatus.CREATED)
    fun companyRegister(@Valid @RequestBody companyRegisterDto: CompanyRegisterDto): Response<CompanyRegisterDto> {
        companyService.validate(companyRegisterDto.cnpj)
        val company = companyService.persist(companyRegisterDto.toCompany())
        employeeService.validate(company, companyRegisterDto.cpf, companyRegisterDto.email)
        val employee = employeeService.persist(companyRegisterDto.toEmployee(company.id!!))
        return Response(company.toDto(employee))
    }

    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    fun personRegister(@Valid @RequestBody personRegisterDto: PersonRegisterDto): Response<PersonRegisterDto> {
        val company = companyService.findByDocument(personRegisterDto.cnpj)
        employeeService.validate(company, personRegisterDto.cpf, personRegisterDto.email)
        val employee = employeeService.persist(personRegisterDto.toEmployee(company!!.id!!))
        return Response(employee.toDto(company))
    }

}