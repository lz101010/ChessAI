// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun initialBoardPrints_passes() {
        assertThat(Board().toString()).isEqualTo("""
            r n b q k b n r
            p p p p p p p p
            - - - - - - - -
            - - - - - - - -
            - - - - - - - -
            - - - - - - - -
            P P P P P P P P
            R N B Q K B N R
        """.trimIndent())
    }

    @Test
    fun equality_passes() {
        assertThat(Board()).isEqualTo(Board())
        assertThat(Board().hashCode()).isEqualTo(Board().hashCode())

        assertThat(Board(whiteToMove = true)).isNotEqualTo(Board(whiteToMove = false))
        assertThat(Board(whiteToMove = true).hashCode()).isNotEqualTo(Board(whiteToMove = false).hashCode())
    }

    @Test
    fun initialEvalScore_passes() {
        val initialScore = 8 + 9 + 6 + 6 + 10
        assertThat(Board(whiteToMove = true).evalScore()).isEqualTo(initialScore)
        assertThat(Board(whiteToMove = false).evalScore()).isEqualTo(initialScore)
    }

    @Test
    fun initialBoardColors_passes() {
        for (index in 0..7) {
            assertThat(Board().pieces[0][0]!!.black).isTrue
            assertThat(Board().pieces[1][0]!!.black).isTrue
            assertThat(Board().pieces[2][0]).isNull()
            assertThat(Board().pieces[3][0]).isNull()
            assertThat(Board().pieces[4][0]).isNull()
            assertThat(Board().pieces[5][0]).isNull()
            assertThat(Board().pieces[6][0]!!.white).isTrue
            assertThat(Board().pieces[7][0]!!.white).isTrue
        }
    }
}
