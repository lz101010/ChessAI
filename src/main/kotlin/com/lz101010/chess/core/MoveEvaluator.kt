// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import com.lz101010.chess.data.PieceType
import com.lz101010.chess.data.Square

object MoveEvaluator {
    fun isCheck(board: Board, move: Move): Boolean {
        return PositionEvaluator.isCheck(MoveMaker.move(board, move))
    }

    fun isMate(board: Board, move: Move): Boolean {
        return PositionEvaluator.isMate(MoveMaker.move(board, move))
    }

    fun isStaleMate(board: Board, move: Move): Boolean {
        return PositionEvaluator.isStaleMate(MoveMaker.move(board, move))
    }

    fun isTechnicalMate(board: Board, move: Move): Boolean {
        val nextBoard = MoveMaker.move(board, move)
        val white = board.whiteToMove
        return !insufficientMaterialForMate(nextBoard, white) && insufficientMaterialForMate(nextBoard, !white)
    }

    fun isTechnicalDraw(board: Board, move: Move): Boolean {
        val nextBoard = MoveMaker.move(board, move)
        val white = board.whiteToMove
        return insufficientMaterialForMate(nextBoard, white) && insufficientMaterialForMate(nextBoard, !white)
    }

    private fun insufficientMaterialForMate(board: Board, white: Boolean): Boolean {
        val pieces = Square.values().mapNotNull { square ->
            val piece = board[square]
            if ((white && piece?.white == true) || (!white && piece?.black == true)) {
                val type = piece.type
                val squareColor = (square.rank.ordinal + square.file.ordinal) % 2 == 0
                AugmentedPiece(type, if (type == PieceType.B) squareColor else null)
            } else {
                null
            }
        }

        val sufficientWinningMaterial = listOf(PieceType.Q, PieceType.R, PieceType.P)
        if (pieces.map(AugmentedPiece::pieceType).any(sufficientWinningMaterial::contains)) {
            return false
        }

        val knightCount = pieces.count { it.pieceType == PieceType.N }
        val bishopCount = pieces.mapNotNull { it.bishopColor }.distinct().count()
        return knightCount + bishopCount < 2
    }

    private data class AugmentedPiece(val pieceType: PieceType, val bishopColor: Boolean?)
}
