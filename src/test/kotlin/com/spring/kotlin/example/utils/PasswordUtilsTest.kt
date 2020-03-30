package com.spring.kotlin.example.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordUtilsTest {

    private val PASSWORD = "Mudar@1234"

    @Test
    fun encryptPasswordTest() {
        val passwordEncrypted = PasswordUtils().passwordEncrypt(PASSWORD)
        Assertions.assertTrue(BCryptPasswordEncoder().matches(PASSWORD, passwordEncrypted))
    }

}