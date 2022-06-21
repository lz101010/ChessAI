package com.lz101010.chess.moves

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Piece
import com.lz101010.chess.data.PieceType
import com.lz101010.chess.data.Square
import com.lz101010.chess.moves.MoveHelper.evaluate


object AttackHelper {
    fun attackedSquares(piece: Piece, startSquare: Square, board: Board): List<Square> {
        val allAttackedSquares = mutableListOf<Square>()

        AttackSetGenerator.generate(piece.type).attackPatterns.forEach { pattern ->
            val attackedSquares = attackedSquares(startSquare, pattern, piece.white, board)
            allAttackedSquares.addAll(attackedSquares)
        }

        return allAttackedSquares
    }

    private fun attackedSquares(
        startSquare: Square,
        movePattern: AttackPattern,
        whiteToMove: Boolean,
        board: Board
    ): List<Square> {
        val targetSquare = evaluate(
            startSquare,
            movePattern.deltaX,
            movePattern.deltaY,
            whiteToMove
        ) ?: return listOf()
        val targetPiece = board[targetSquare]
        if (movePattern.captures && targetPiece == null && board.enPassant?.name != targetSquare.name) {
            return emptyList()
        }

        return if (movePattern.repeat) {
            when {
                targetPiece == null -> listOf(targetSquare).plus(attackedSquares(targetSquare, movePattern, whiteToMove, board))
                targetPiece.white != whiteToMove -> listOf(targetSquare)
                else -> listOf()
            }
        } else {
            when {
                targetPiece == null -> listOf(targetSquare)
                targetPiece.white != whiteToMove -> listOf(targetSquare)
                else -> listOf()
            }
        }
    }

    // TODO: #1 remove as necessary (see MoveHelper), add tests
    fun isAttacked(square: Square, byWhite: Boolean, board: Board): Boolean {
        val piece = board[square]
        if (piece != null && piece.white == byWhite) {
            return false
        }
        return isAttackedOrGuarded(square, byWhite, board)
    }

    fun isAttackedOrGuarded(square: Square, byWhite: Boolean, board: Board): Boolean {
        return MoveHelper.isAttacked(square, board)
        /*
        return PieceType.values().any { type ->
            val attackedSquares = attackedSquares(Piece(type, !byWhite), square, board)
            attackedSquares.any { attackedSquare ->
                val attackedPiece = board[attackedSquare]
                attackedPiece != null && attackedPiece.type == type && attackedPiece.white == byWhite
            }
        }

         */
    }

    fun countAttacks(square: Square, byWhite: Boolean, board: Board): Int {
        val piece = board[square]
        if (piece != null && piece.white == byWhite) {
            return 0
        }
        return PieceType.values().count { type ->
            val attackedSquares = attackedSquares(Piece(type, !byWhite), square, board)
            attackedSquares.any { attackedSquare ->
                val attackedPiece = board[attackedSquare]
                attackedPiece != null && attackedPiece.type == type && attackedPiece.white == byWhite
            }
        }
    }
}
