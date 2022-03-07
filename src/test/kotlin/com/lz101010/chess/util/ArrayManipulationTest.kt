// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ArrayManipulationTest {
    @Test
    fun arrayRotation_passes() {
        val pieceTable = arrayOf(
             0,  1,  2,  3,  4,  5,  6,  7,
             8,  9, 10, 11, 12, 13, 14, 15,
            16, 17, 18, 19, 20, 21, 22, 23,
            24, 25, 26, 27, 28, 29, 30, 31,
            32, 33, 34, 35, 36, 37, 38, 39,
            40, 41, 42, 43, 44, 45, 46, 47,
            48, 49, 50, 51, 52, 53, 54, 55,
            56, 57, 58, 59, 60, 61, 62, 63
        )

        val rotatedTable = arrayOf(
            63, 62, 61, 60, 59, 58, 57, 56,
            55, 54, 53, 52, 51, 50, 49, 48,
            47, 46, 45, 44, 43, 42, 41, 40,
            39, 38, 37, 36, 35, 34, 33, 32,
            31, 30, 29, 28, 27, 26, 25, 24,
            23, 22, 21, 20, 19, 18, 17, 16,
            15, 14, 13, 12, 11, 10,  9,  8,
             7,  6,  5,  4,  3,  2,  1,  0
        )

        assertThat(ArrayManipulation.rotate(pieceTable).toList())
            .hasSize(pieceTable.size)
            .containsSequence(rotatedTable.toList())
    }

    @Test
    fun arrayMirror_passes() {
        val pieceTable = arrayOf(
             0,  1,  2,  3,  4,  5,  6,  7,
             8,  9, 10, 11, 12, 13, 14, 15,
            16, 17, 18, 19, 20, 21, 22, 23,
            24, 25, 26, 27, 28, 29, 30, 31,
            32, 33, 34, 35, 36, 37, 38, 39,
            40, 41, 42, 43, 44, 45, 46, 47,
            48, 49, 50, 51, 52, 53, 54, 55,
            56, 57, 58, 59, 60, 61, 62, 63
        )

        val rotatedTable = arrayOf(
            56, 57, 58, 59, 60, 61, 62, 63,
            48, 49, 50, 51, 52, 53, 54, 55,
            40, 41, 42, 43, 44, 45, 46, 47,
            32, 33, 34, 35, 36, 37, 38, 39,
            24, 25, 26, 27, 28, 29, 30, 31,
            16, 17, 18, 19, 20, 21, 22, 23,
             8,  9, 10, 11, 12, 13, 14, 15,
             0,  1,  2,  3,  4,  5,  6,  7
        )

        assertThat(ArrayManipulation.mirror(pieceTable).toList())
            .hasSize(pieceTable.size)
            .containsSequence(rotatedTable.toList())
    }

    @Test
    fun badArray_fails() {
        assertThrows<IllegalArgumentException> { ArrayManipulation.mirror(arrayOf()) }
        assertThrows<IllegalArgumentException> { ArrayManipulation.rotate(arrayOf()) }
    }
}
