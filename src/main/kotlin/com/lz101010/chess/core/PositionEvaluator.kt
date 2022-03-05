// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.*

object PositionEvaluator {
    fun isCheck(board: Board, move: Move): Boolean {
        return isCheck(MoveMaker.move(board, move))
    }

    fun isMate(board: Board, move: Move): Boolean {
        val nextBoard = MoveMaker.move(board, move)
        return isCheck(nextBoard) && noMovesLeft(nextBoard)
    }

    fun isTechnicalMate(board: Board, move: Move): Boolean {
        val nextBoard = MoveMaker.move(board, move)
        val white = board.whiteToMove
        return !insufficientMaterialForMate(nextBoard, white) && insufficientMaterialForMate(nextBoard, !white)
    }

    fun isStaleMate(board: Board, move: Move): Boolean {
        val nextBoard = MoveMaker.move(board, move)
        return !isCheck(nextBoard) && noMovesLeft(nextBoard)
    }

    fun isTechnicalDraw(board: Board, move: Move): Boolean {
        val nextBoard = MoveMaker.move(board, move)
        val white = board.whiteToMove
        return insufficientMaterialForMate(nextBoard, white) && insufficientMaterialForMate(nextBoard, !white)
    }

    private fun isCheck(board: Board): Boolean {
        val target = find(Piece(PieceType.K, board.whiteToMove), board).first()

        val attackedSquares = AttackedSquaresGenerator.generate(board)

        return attackedSquares.contains(target)
    }

    private fun find(piece: Piece, board: Board): List<Square> = Square.values().filter { board[it] == piece }

    private fun noMovesLeft(board: Board) = MoveGenerator.find(board).isEmpty()

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
