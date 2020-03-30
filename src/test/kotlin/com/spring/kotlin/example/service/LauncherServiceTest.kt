package com.spring.kotlin.example.service

import com.spring.kotlin.example.document.Launcher
import com.spring.kotlin.example.enum.Type
import com.spring.kotlin.example.repository.LauncherRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.DirtiesContext
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Stream

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
internal class LauncherServiceTest {

    @Autowired
    val launcherService: LauncherService? = null

    @MockBean
    val launcherRepository: LauncherRepository? = null

    private val employeeId: String = "1"
    private val id: String = "1"

    @BeforeEach
    fun setup() {
        BDDMockito.given(launcherRepository?.findByEmployeeId(employeeId, PageRequest.of(0, 10)))
                .willReturn(PageImpl(arrayListOf(launcher())))
        BDDMockito.given(launcherRepository?.findById(id)).willReturn(Optional.of(launcher()))
        BDDMockito.given(launcherRepository?.save(Mockito.any(Launcher::class.java))).willReturn(launcher())
    }

    @Test
    fun findByEmployeeId() {
        val launcherPage: Page<Launcher>? = launcherService?.findByEmployeeId(employeeId, PageRequest.of(0, 10))
        Assertions.assertNotNull(launcherPage)
        val launcherStream: Stream<Launcher>? = launcherPage?.get()
        Assertions.assertEquals(1, launcherStream?.count())

    }

    @Test
    fun findById() {
        val launcher = launcherService?.findById(id)
        Assertions.assertNotNull(launcher)
    }

    @Test
    fun persist() {
        val launcher = launcherService?.persist(launcher())
        Assertions.assertNotNull(launcher)
    }

    @Test
    fun remove() {
        launcherService?.remove(id)
    }

    private fun launcher() = Launcher(LocalDateTime.now(), Type.START_WORK, employeeId)
}
