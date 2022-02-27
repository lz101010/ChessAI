// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class AppTest {
    @Test
    fun testAppHasAGreeting() {
        val classUnderTest = App()
        assertThat(classUnderTest.greeting).isNotNull
    }

    @Test
    fun mainCanBeCalled() {
        assertDoesNotThrow { main() }
    }
}
