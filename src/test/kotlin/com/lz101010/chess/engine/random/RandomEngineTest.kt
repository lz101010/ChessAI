// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.random

import com.lz101010.chess.game.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private const val MAX_MOVES = 1_000

class RandomEngineTest {
    @Test
    fun initialBoardPrints_passes() {
        val engineWhite = RandomEngine
        val engineBlack = RandomEngine

        val game = Game()
        while (game.moves.size < MAX_MOVES && !game.isOver()) {
            val engine = if (game.whiteToMove()) engineWhite else engineBlack

            val nextMove = game.findMove(engine)
            game.move(nextMove)
        }

        println(game.lan())
        assertThat(game.moves.size).isLessThan(MAX_MOVES)
    }
}
