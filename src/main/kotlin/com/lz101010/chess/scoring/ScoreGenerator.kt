// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.scoring

import com.lz101010.chess.data.Piece
import com.lz101010.chess.data.PieceType
import com.lz101010.chess.data.Square
import com.lz101010.chess.game.Board
import com.lz101010.chess.game.MoveEvaluator
import com.lz101010.chess.game.MoveGenerator
import com.lz101010.chess.game.PositionEvaluator
import com.lz101010.chess.util.ArrayManipulation.rotate

object ScoreGenerator {
    fun simple(board: Board): Int {
        return gameOver(board) ?: diffPieceValues(board)
    }

    fun positional(board: Board): Int {
        return gameOver(board) ?: diffPieceSquareTables(board)
    }

    private fun gameOver(board: Board): Int? {
        if (PositionEvaluator.isMate(board)) {
            return Int.MIN_VALUE
        }
        if (PositionEvaluator.isStaleMate(board)) {
            return 0
        }
        if (MoveGenerator.find(board).any { MoveEvaluator.isMate(board, it) }) {
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

    private fun diffPieceSquareTables(board: Board): Int {
        val (whitePieces, blackPieces) = Square.values().mapNotNull { score(board, it) }.partition { it.second }

        val whiteSum = whitePieces.sumOf { it.first }
        val blackSum = blackPieces.sumOf { it.first }

        return if (board.whiteToMove) {
            whiteSum - blackSum
        } else {
            blackSum - whiteSum
        }
    }

    private fun score(board: Board, square: Square): Pair<Int, Boolean>? {
        val piece = board[square] ?: return null
        return Pair(score(piece, square, isEndgame(board)), piece.white)
    }

    private fun isEndgame(board: Board): Boolean = board.pieces.flatten().let {
        noQueensLeft(it) || queenAndMinorPiece(it)
    }

    private fun noQueensLeft(pieces: List<Piece?>) = pieces.none { it?.type == PieceType.Q }
    private fun noRookOrPawnLeft(pieces: List<Piece>) = pieces.none { it.type == PieceType.R || it.type == PieceType.P }

    private fun queenAndMinorPiece(pieces: List<Piece?>): Boolean {
        val (whitePieces, blackPieces) = pieces.filterNotNull().partition { it.white }

        val whiteEndgame = whitePieces.let { noQueensLeft(it) || noRookOrPawnLeft(it) }
        val blackEndgame = blackPieces.let { noQueensLeft(it) || noRookOrPawnLeft(it) }

        return whiteEndgame && blackEndgame
    }

    private fun score(piece: Piece, square: Square, endgame: Boolean = false): Int {
        val index = 63 - (square.rank.ordinal * 8 + square.file.ordinal)
        return pieceTable(piece, endgame)[index]
    }

    private fun pieceTable(piece: Piece, endgame: Boolean): Array<Int> {
        return when (piece.type) {
            PieceType.P -> pawnTable(piece)
            PieceType.N -> knightTable(piece)
            PieceType.B -> bishopTable(piece)
            PieceType.R -> rookTable(piece)
            PieceType.Q -> queenTable(piece)
            PieceType.K -> kingTable(piece, endgame)
        }
    }

    private fun pawnTable(piece: Piece) = if (piece.white) PAWN_TABLE_WHITE else PAWN_TABLE_BLACK
    private fun knightTable(piece: Piece) = if (piece.white) KNIGHT_TABLE_WHITE else KNIGHT_TABLE_BLACK
    private fun bishopTable(piece: Piece) = if (piece.white) BISHOP_TABLE_WHITE else BISHOP_TABLE_BLACK
    private fun rookTable(piece: Piece) = if (piece.white) ROOK_TABLE_WHITE else ROOK_TABLE_BLACK
    private fun queenTable(piece: Piece) = if (piece.white) QUEEN_TABLE_WHITE else QUEEN_TABLE_BLACK

    private fun kingTable(piece: Piece, endgame: Boolean) = when (endgame) {
        false -> if (piece.white) KING_MIDDLE_TABLE_WHITE else KING_MIDDLE_TABLE_BLACK
        true -> if (piece.white) KING_END_TABLE_WHITE else KING_END_TABLE_BLACK
    }

    // adapted from https://www.chessprogramming.org/Simplified_Evaluation_Function
    // consider using https://www.chessprogramming.org/PeSTO%27s_Evaluation_Function
    private val PAWN_TABLE_WHITE = arrayOf(
        0,  0,  0,  0,  0,  0,  0,  0,
        50, 50, 50, 50, 50, 50, 50, 50,
        10, 10, 20, 30, 30, 20, 10, 10,
        5,  5, 10, 25, 25, 10,  5,  5,
        0,  0,  0, 20, 20,  0,  0,  0,
        5, -5,-10,  0,  0,-10, -5,  5,
        5, 10, 10,-20,-20, 10, 10,  5,
        0,  0,  0,  0,  0,  0,  0,  0
    )
    private val PAWN_TABLE_BLACK = rotate(PAWN_TABLE_WHITE)

    private val KNIGHT_TABLE_WHITE = arrayOf(
        -50,-40,-30,-30,-30,-30,-40,-50,
        -40,-20,  0,  0,  0,  0,-20,-40,
        -30,  0, 10, 15, 15, 10,  0,-30,
        -30,  5, 15, 20, 20, 15,  5,-30,
        -30,  0, 15, 20, 20, 15,  0,-30,
        -30,  5, 10, 15, 15, 10,  5,-30,
        -40,-20,  0,  5,  5,  0,-20,-40,
        -50,-40,-30,-30,-30,-30,-40,-50
    )
    private val KNIGHT_TABLE_BLACK = rotate(KNIGHT_TABLE_WHITE)

    private val BISHOP_TABLE_WHITE = arrayOf(
        -20,-10,-10,-10,-10,-10,-10,-20,
        -10,  0,  0,  0,  0,  0,  0,-10,
        -10,  0,  5, 10, 10,  5,  0,-10,
        -10,  5,  5, 10, 10,  5,  5,-10,
        -10,  0, 10, 10, 10, 10,  0,-10,
        -10, 10, 10, 10, 10, 10, 10,-10,
        -10,  5,  0,  0,  0,  0,  5,-10,
        -20,-10,-10,-10,-10,-10,-10,-20
    )
    private val BISHOP_TABLE_BLACK = rotate(BISHOP_TABLE_WHITE)

    private val ROOK_TABLE_WHITE = arrayOf(
         0,  0,  0,  0,  0,  0,  0,  0,
         5, 10, 10, 10, 10, 10, 10,  5,
        -5,  0,  0,  0,  0,  0,  0, -5,
        -5,  0,  0,  0,  0,  0,  0, -5,
        -5,  0,  0,  0,  0,  0,  0, -5,
        -5,  0,  0,  0,  0,  0,  0, -5,
        -5,  0,  0,  0,  0,  0,  0, -5,
         0,  0,  0,  5,  5,  0,  0,  0
    )
    private val ROOK_TABLE_BLACK = rotate(ROOK_TABLE_WHITE)

    private val QUEEN_TABLE_WHITE = arrayOf(
        -20,-10,-10, -5, -5,-10,-10,-20,
        -10,  0,  0,  0,  0,  0,  0,-10,
        -10,  0,  5,  5,  5,  5,  0,-10,
         -5,  0,  5,  5,  5,  5,  0, -5,
          0,  0,  5,  5,  5,  5,  0, -5,
        -10,  5,  5,  5,  5,  5,  0,-10,
        -10,  0,  5,  0,  0,  0,  0,-10,
        -20,-10,-10, -5, -5,-10,-10,-20
    )
    private val QUEEN_TABLE_BLACK = rotate(QUEEN_TABLE_WHITE)

    private val KING_MIDDLE_TABLE_WHITE = arrayOf(
        -30,-40,-40,-50,-50,-40,-40,-30,
        -30,-40,-40,-50,-50,-40,-40,-30,
        -30,-40,-40,-50,-50,-40,-40,-30,
        -30,-40,-40,-50,-50,-40,-40,-30,
        -20,-30,-30,-40,-40,-30,-30,-20,
        -10,-20,-20,-20,-20,-20,-20,-10,
         20, 20,  0,  0,  0,  0, 20, 20,
         20, 30, 10,  0,  0, 10, 30, 20
    )
    private val KING_MIDDLE_TABLE_BLACK = rotate(KING_MIDDLE_TABLE_WHITE)

    private val KING_END_TABLE_WHITE = arrayOf(
        -50,-40,-30,-20,-20,-30,-40,-50,
        -30,-20,-10,  0,  0,-10,-20,-30,
        -30,-10, 20, 30, 30, 20,-10,-30,
        -30,-10, 30, 40, 40, 30,-10,-30,
        -30,-10, 30, 40, 40, 30,-10,-30,
        -30,-10, 20, 30, 30, 20,-10,-30,
        -30,-30,  0,  0,  0,  0,-30,-30,
        -50,-30,-30,-30,-30,-30,-30,-50
    )
    private val KING_END_TABLE_BLACK = rotate(KING_END_TABLE_WHITE)
}
