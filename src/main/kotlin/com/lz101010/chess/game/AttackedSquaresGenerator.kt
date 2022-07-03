// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.game

import com.lz101010.chess.data.Piece
import com.lz101010.chess.data.Square
import java.util.function.Predicate

object AttackedSquaresGenerator {
    fun generate(board: Board): List<Square> {
        return if (board.whiteToMove) {
            attackedByBlack(board)
        } else {
            attackedByWhite(board)
        }
    }

    private fun attackedByWhite(board: Board): List<Square> {
        return attackedBy(Piece::white, board)
    }

    private fun attackedByBlack(board: Board): List<Square> {
        return attackedBy(Piece::black, board)
    }

    private fun attackedBy(pieceColor: Predicate<Piece>, board: Board): List<Square> {
        return Square.values()
            .mapNotNull { square ->
                val piece = board[square]
                if (piece != null && pieceColor.test(piece)) attackedBy(piece, square, board) else null
            }.flatten().distinct()
    }

    private fun attackedBy(piece: Piece, square: Square, board: Board): List<Square> {
        return AttackPatternGenerator.generate(piece.type).attackMoves.map {
            attackedBy(it, piece.white, square, board)
        }.flatten()
    }

    private fun attackedBy(attackMove: AttackMove, whiteAttacking: Boolean, square: Square, board: Board): List<Square> {
        val attackedSquare = evaluate(square, attackMove.deltaX, attackMove.deltaY, whiteAttacking) ?: return listOf()
        val attackedPiece = board[attackedSquare]

        return if (attackMove.repeat) {
            when {
                attackedPiece == null -> listOf(listOf(attackedSquare), attackedBy(attackMove, whiteAttacking, attackedSquare, board)).flatten()
                attackedPiece.white != whiteAttacking -> listOf(attackedSquare)
                else -> listOf()
            }
        } else {
            when {
                attackedPiece == null -> listOf(attackedSquare)
                attackedPiece.white != whiteAttacking -> listOf(attackedSquare)
                else -> listOf()
            }
        }
    }

    private fun evaluate(square: Square, deltaX: Int, deltaY: Int, whiteAttacking: Boolean): Square? {
        val deltaYFactor = if (whiteAttacking) -1 else 1
        val newColIndex = square.file.ordinal + deltaX
        val newRowIndex = (7 - square.rank.ordinal) + deltaYFactor * deltaY

        if (newRowIndex < 0 || newColIndex < 0 || newRowIndex > 7 || newColIndex > 7) {
            return null
        }
        val newSquareOrdinal = newRowIndex * 8 + newColIndex
        return Square.values().first { it.ordinal == newSquareOrdinal }
    }
}
