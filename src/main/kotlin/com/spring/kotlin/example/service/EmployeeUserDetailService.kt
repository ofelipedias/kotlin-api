package com.spring.kotlin.example.service

import com.spring.kotlin.example.security.EmployeeUserDetail
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class EmployeeUserDetailService(val employeeService: EmployeeService) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username != null) {
            val employee = employeeService.findByEmail(username)
            if (employee != null) {
                return EmployeeUserDetail(employee)
            }
        }
        throw UsernameNotFoundException(username)
    }

}