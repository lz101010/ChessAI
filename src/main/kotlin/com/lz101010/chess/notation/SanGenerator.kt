// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.notation

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Game
import com.lz101010.chess.data.Move

object SanGenerator {
    fun generate(game: Game, board: Board = Board.default): String {
        // TODO: expand + tests
        return game.moves
            .foldIndexed(GameState(board, printMove = Move::san)) { index, state, move -> state.move(move, index) }
            .moves.joinToString("\n")
    }
}
