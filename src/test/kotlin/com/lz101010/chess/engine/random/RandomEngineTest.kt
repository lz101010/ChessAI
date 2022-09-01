// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.random

import com.lz101010.chess.notation.LanGenerator
import com.lz101010.chess.core.PositionEvaluator
import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Game
import com.lz101010.chess.data.Move
import com.lz101010.chess.support.move
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private const val MAX_MOVES = 1_000

class RandomEngineTest {
    @Test
    fun initialBoardPrints_passes() {
        val engineWhite = RandomEngine
        val engineBlack = RandomEngine

        var board = Board.default
        val moves = mutableListOf<Move>()

        var moveCount = 0
        while (moveCount < MAX_MOVES && !PositionEvaluator.isOver(board)) {
            val engine = if (board.whiteToMove) engineWhite else engineBlack

            val nextMove = engine.nextMove(board)
            moves.add(nextMove)
            board = board.move(nextMove)
            moveCount++
        }

        assertThat(moveCount).isLessThan(MAX_MOVES)
        println(LanGenerator.generate(Game(moves)))
    }
}
