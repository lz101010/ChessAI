// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.Board

object ScoreGenerator {
    fun simpleScore(board: Board): Int {
        return gameOver(board) ?: diffPieceValues(board)
    }

    private fun gameOver(board: Board): Int? {
        if (PositionEvaluator.isMate(board)) {
            return Int.MIN_VALUE
        }
        if (PositionEvaluator.isStaleMate(board)) {
            return 0
        }
        if (MoveGenerator.find(board).any { PositionEvaluator.isMate(board, it) }) {
            return Int.MAX_VALUE
        }
        return null
    }

    private fun diffPieceValues(board: Board): Int {
        val (whitePieces, blackPieces) = board.pieces.flatten().filterNotNull().partition { it.white }

        val whiteSum = whitePieces.sumOf { it.value }
        val blackSum = blackPieces.sumOf { it.value }

        return if (board.whiteToMove) {
            whiteSum - blackSum
        } else {
            blackSum - whiteSum
        }
    }
}
