package com.lz101010

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AppTest {
    @Test
    fun testAppHasAGreeting() {
        val classUnderTest = App()
        assertThat(classUnderTest.greeting).isNotNull
    }
}
