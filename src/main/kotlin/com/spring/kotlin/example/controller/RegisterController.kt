package com.spring.kotlin.example.controller

import com.spring.kotlin.example.document.Company
import com.spring.kotlin.example.dto.CompanyRegisterDto
import com.spring.kotlin.example.dto.PersonRegisterDto
import com.spring.kotlin.example.extension.hasErrors
import com.spring.kotlin.example.extension.toCompany
import com.spring.kotlin.example.extension.toDto
import com.spring.kotlin.example.extension.toEmployee
import com.spring.kotlin.example.service.CompanyService
import com.spring.kotlin.example.service.EmployeeService
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/register")
class RegisterController(val companyService: CompanyService, val employeeService: EmployeeService) {

    @PostMapping("/company")
    fun companyRegister(@Valid @RequestBody companyRegisterDto: CompanyRegisterDto, bindingResult: BindingResult):
            ResponseEntity<Response<CompanyRegisterDto>> {
        val response = Response<CompanyRegisterDto>()

        companyValidate(companyRegisterDto, bindingResult)
        if (bindingResult.hasErrors(response)) return ResponseEntity.badRequest().body(response)

        val company = companyService.persist(companyRegisterDto.toCompany())
        val employee = employeeService.persist(companyRegisterDto.toEmployee(company.id!!))

        response.data = company.toDto(employee)
        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/person")
    fun personRegister(@Valid @RequestBody personRegisterDto: PersonRegisterDto, bindingResult: BindingResult):
            ResponseEntity<Response<PersonRegisterDto>> {
        val response = Response<PersonRegisterDto>()

        val company = companyService.findByDocument(personRegisterDto.cnpj)
        personValidate(personRegisterDto, company, bindingResult)
        if (bindingResult.hasErrors(response)) return ResponseEntity.badRequest().body(response)

        val employee = employeeService.persist(personRegisterDto.toEmployee(company!!.id!!))
        response.data = employee.toDto(company)
        return ResponseEntity.ok().body(response)
    }

    private fun companyValidate(companyRegisterDto: CompanyRegisterDto, bindingResult: BindingResult) {
        val validateCnpj = companyService.findByDocument(companyRegisterDto.cnpj)
        if (validateCnpj != null) bindingResult.addError(ObjectError("companyRegisterDto", "Company already exists"))
        employeeValidate(companyRegisterDto::class.java, companyRegisterDto.cpf, companyRegisterDto.email, bindingResult)
    }

    private fun personValidate(personRegisterDto: PersonRegisterDto, company: Company?, bindingResult: BindingResult) {
        if (Objects.isNull(company)) bindingResult.addError(ObjectError("personRegisterDto", "Company not registered"))
        employeeValidate(personRegisterDto::class.java, personRegisterDto.cpf, personRegisterDto.email, bindingResult)
    }

    private fun <T> employeeValidate(clazz: Class<T>, cpf: String, email: String, bindingResult: BindingResult) {
        val validateCpf = employeeService.findByDocument(cpf)
        if (validateCpf != null) bindingResult.addError(ObjectError(clazz.simpleName, "CPF already in use"))

        val validateEmail = employeeService.findByEmail(email)
        if (validateEmail != null) bindingResult.addError(ObjectError(clazz.simpleName, "Email already in use"))
    }
}