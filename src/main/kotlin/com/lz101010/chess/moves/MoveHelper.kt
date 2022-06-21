package com.lz101010.chess.moves

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import com.lz101010.chess.data.PieceType
import com.lz101010.chess.data.Square


// TODO: #0 make this pretty + add tests
object MoveHelper {
    fun evaluate(square: Square, deltaX: Int, deltaY: Int, whiteToMove: Boolean): Square? {
        val newColIndex = square.file.ordinal + deltaX
        val newRowIndex = if (whiteToMove) (7 - square.rank.ordinal) - deltaY else (7 - square.rank.ordinal) + deltaY

        if (newRowIndex < 0 || newColIndex < 0 || newRowIndex > 7 || newColIndex > 7) {
            return null
        }
        val newSquareOrdinal = newRowIndex * 8 + newColIndex
        return Square.find(newSquareOrdinal)
    }

    private val kingDeltas = listOf(
        Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0), Pair(1, 1)
    )

    private val knightDeltas = listOf(
        Pair(-2, -1), Pair(-2, 1), Pair(-1, -2), Pair(-1, 2), Pair(1, -2), Pair(1, 2), Pair(2, -1), Pair(2, 1)
    )

    fun findPotentialPins(kingSquare: Square, board: Board): Map<Square, Pair<Int, Int>> {
        val relevantSquares = mutableMapOf<Square, Pair<Int, Int>>()

        for ((deltaX, deltaY) in kingDeltas) {
            val square = findNextOccupiedSquare(kingSquare, deltaX, deltaY, board) ?: continue
            val piece = board[square]!!

            // hit --> only interesting if we can move it
            if (piece.white == board.whiteToMove) {
                val nextSquare = findNextOccupiedSquare(square, deltaX, deltaY, board) ?: continue
                val nextPiece = board[nextSquare]!!

                // hit --> only interesting if it's threatening
                if (nextPiece.white != board.whiteToMove && checkForAttack(nextPiece.type, deltaX, deltaY)) {
                    relevantSquares[square] = Pair(deltaX, deltaY)
                }
            }
        }

        return relevantSquares
    }

    private const val dummy = false

    private fun findNextOccupiedSquare(startSquare: Square, deltaX: Int, deltaY: Int, board: Board): Square? {
        for (i in 1..7) {
            // miss --> out of bounds, done
            val square = evaluate(startSquare, deltaX * i, deltaY * i, dummy) ?: break
            // hit
            if (board[square] != null) {
                return square
            }
        }
        return null
    }

    private fun checkForAttack(type: PieceType, deltaX: Int, deltaY: Int) =
        checkForQueen(type) || checkForRook(type, deltaX, deltaY) || checkForBishop(type, deltaX, deltaY)

    private fun checkForQueen(type: PieceType) =
        type == PieceType.Q

    private fun checkForBishop(type: PieceType, deltaX: Int, deltaY: Int) =
        type == PieceType.B && (deltaX == deltaY || deltaX == -deltaY)

    private fun checkForRook(type: PieceType, deltaX: Int, deltaY: Int) =
        type == PieceType.R && (deltaX == 0 || deltaY == 0)

    fun moveTowardsDelta(move: Move, deltas: Pair<Int, Int>): Boolean {
        val fileDelta = move.to.file.ordinal - move.from.file.ordinal
        val rankDelta = move.to.rank.ordinal - move.from.rank.ordinal

        // TODO: tests
        return fileDelta == deltas.first && rankDelta == deltas.second
    }

    fun isAttacked(square: Square, board: Board): Boolean {
        for ((deltaX, deltaY) in kingDeltas) {
            // check for king
            val adjacentSquare = evaluate(square, deltaX, deltaY, dummy)
            if (adjacentSquare != null) {
                val adjacentPiece = board[adjacentSquare]
                if (adjacentPiece != null && adjacentPiece.white != board.whiteToMove && adjacentPiece.type == PieceType.K) {
                    return true
                }
            }

            // check for queen, bishop, rook
            val nextSquare = findNextOccupiedSquare(square, deltaX, deltaY, board) ?: continue
            val piece = board[nextSquare]!!

            // hit --> only interesting if it attacks
            if (piece.white != board.whiteToMove && checkForAttack(piece.type, deltaX, deltaY)) {
                return true
            }
        }

        for ((deltaX, deltaY) in knightDeltas) {
            val knightSquare = evaluate(square, deltaX, deltaY, dummy) ?: continue
            val piece = board[knightSquare] ?: continue
            if (piece.white != board.whiteToMove && piece.type == PieceType.N) {
                return true
            }
        }

        return false
    }
}
