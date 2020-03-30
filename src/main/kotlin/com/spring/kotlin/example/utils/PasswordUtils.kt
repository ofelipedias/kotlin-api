package com.spring.kotlin.example.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordUtils {

    fun passwordEncrypt(password: String): String = BCryptPasswordEncoder().encode(password)

}