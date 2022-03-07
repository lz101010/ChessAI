// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.notation

import com.lz101010.chess.core.MoveEvaluator
import com.lz101010.chess.core.MoveMaker
import com.lz101010.chess.core.PositionEvaluator
import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Game
import com.lz101010.chess.data.Move

object LanGenerator {
    fun generate(game: Game, board: Board = Board.default): String {
        return game.moves
            .foldIndexed(GameState(board, printMove = Move::lan)) { index, state, move -> state.move(move, index) }
            .moves.joinToString("\n")
    }
}
