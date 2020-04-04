package com.spring.kotlin.example.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.spring.kotlin.example.document.Employee
import com.spring.kotlin.example.document.Launcher
import com.spring.kotlin.example.enum.Profile
import com.spring.kotlin.example.enum.Type
import com.spring.kotlin.example.extension.toDto
import com.spring.kotlin.example.service.EmployeeService
import com.spring.kotlin.example.service.LauncherService
import com.spring.kotlin.example.utils.PasswordUtils
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class LauncherControllerTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val launcherService: LauncherService? = null

    @MockBean
    private val employeeService: EmployeeService? = null

    // request data
    private val baseUrl: String = "/launchers"

    // employee data
    private val name: String = "employee1"
    private val email: String = "employee@email.com"
    private val password: String = "123"
    private val document: String = "00000000000000"
    private val employeeId: String = "001"

    // launcher data
    private val launcherId: String = "0001"

    @Test
    @WithMockUser
    fun postLauncherTest() {
        val launcher = launcher(false)
        BDDMockito.given(employeeService?.findById(employeeId)).willReturn(employee())
        BDDMockito.given(launcherService?.persist(launcher)).willReturn(launcher(true))

        mockMvc!!.perform(MockMvcRequestBuilders.post(baseUrl)
                .content(ObjectMapper().writeValueAsString(launcher.toDto()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty)
    }

    @Test
    @WithMockUser
    fun putLauncherTest() {
        val launcher = launcher(true)
        BDDMockito.given(launcherService?.findById(launcherId)).willReturn(launcher)
        BDDMockito.given(employeeService?.findById(employeeId)).willReturn(employee())
        BDDMockito.given(launcherService?.persist(launcher)).willReturn(launcher)

        mockMvc!!.perform(MockMvcRequestBuilders.put("$baseUrl/$launcherId")
                .content(ObjectMapper().writeValueAsString(launcher.toDto()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty)
    }

    @Test
    @WithMockUser
    fun getLauncherByIdTest() {
        val launcher = launcher(true)
        BDDMockito.given(launcherService?.findById(launcherId)).willReturn(launcher)

        mockMvc!!.perform(MockMvcRequestBuilders.get("$baseUrl/$launcherId"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty)
    }

    @Test
    @WithMockUser(username = "admin@admin.com.br", roles = arrayOf("ADMIN"))
    fun deleteLauncherByIdTest() {
        val launcher = launcher(true)
        BDDMockito.given(launcherService?.findById(launcherId)).willReturn(launcher)

        mockMvc!!.perform(MockMvcRequestBuilders.delete("$baseUrl/$launcherId"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    private fun launcher(withId: Boolean): Launcher {
        return if (withId) Launcher(LocalDateTime.now(), Type.START_WORK, employeeId, null, null, launcherId)
        else Launcher(LocalDateTime.now(), Type.START_WORK, employeeId)
    }

    private fun employee(): Employee = Employee(name, email, PasswordUtils().passwordEncrypt(password), document,
            Profile.ROLE_USER, employeeId)
}