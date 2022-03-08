// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.game

import com.lz101010.chess.core.FenGenerator
import com.lz101010.chess.core.MoveMaker
import com.lz101010.chess.core.PositionEvaluator
import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import com.lz101010.chess.engine.Engine

data class Game(
    val initialBoard: Board = Board.default,
    val moves: MutableList<Move> = mutableListOf()
) {
    private var board = initialBoard

    fun move(move: Move) {
        board = MoveMaker.move(board, move)
        moves.add(move)
    }

    fun findMove(engine: Engine): Move = engine.nextMove(board)

    fun whiteToMove() = board.whiteToMove

    fun isOver() = PositionEvaluator.isMate(board) || PositionEvaluator.isStaleMate(board)

    fun lan() = LanGenerator.generate(this)

    fun fen() = FenGenerator.generate(board)

    override fun toString(): String {
        return "${moves.last()}\n$board\n"
    }
}
