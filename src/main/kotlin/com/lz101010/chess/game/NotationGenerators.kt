// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.game

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move

internal abstract class NotationGenerator(private val printMove: (Board, Move) -> String) {
    fun generate(game: Game): String {
        return game.moves
            .foldIndexed(GameState(game.initialBoard, printMove = printMove)) {
                    index, state, move -> state.move(move, index)
            }
            .moves.joinToString("\n")
    }
}

internal object LanGenerator: NotationGenerator({ _, move -> move.lan })

internal object SanGenerator: NotationGenerator({ board, move -> "${board[move.from]!!.type.shortName}$move" })
