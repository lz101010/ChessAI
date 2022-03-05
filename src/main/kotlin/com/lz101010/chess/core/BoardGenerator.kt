// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.*

object BoardGenerator {
    fun fromFen(fen: String): Board {
        val parts = fen.split(" ")
        require (parts.size == 6) { "invalid FEN: $fen"}

        return Board(
            parts[0].let(::pieces),
            parts[1].let(::whiteToMove),
            parts[2].let(::castlingOptions),
            parts[3].let(::enPassant),
            parts[4].let(::plies),
            parts[5].let(::nextMove)
        )
    }

    private fun pieces(part1: String): Array<Array<Piece?>> {
        val rows = part1.split("/")
        require (rows.size == 8) { "bad part1: $part1" }

        return rows.map(::toPieces).toTypedArray()
    }

    private fun toPieces(row: String): Array<Piece?> {
        val result = mutableListOf<Piece?>()
        row.forEach { result.addAll(toPieces(it)) }
        return result.toTypedArray()
    }

    private fun toPieces(char: Char): List<Piece?> {
        val isWhite = char.isUpperCase()
        return when (char) {
            '1', '2', '3', '4', '5', '6', '7', '8' -> arrayOfNulls<Piece?>(char.digitToInt()).asList()
            'p', 'P' -> listOf(Piece(PieceType.P, isWhite))
            'r', 'R' -> listOf(Piece(PieceType.R, isWhite))
            'n', 'N' -> listOf(Piece(PieceType.N, isWhite))
            'b', 'B' -> listOf(Piece(PieceType.B, isWhite))
            'q', 'Q' -> listOf(Piece(PieceType.Q, isWhite))
            'k', 'K' -> listOf(Piece(PieceType.K, isWhite))
            else -> throw IllegalArgumentException("bad char in row: $char")
        }
    }

    private fun whiteToMove(part2: String): Boolean {
        return when (part2) {
            "w" -> true
            "b" -> false
            else -> throw IllegalArgumentException("bad part2: $part2")
        }
    }

    private fun castlingOptions(part3: String): Collection<CastlingOption> {
        return when (part3) {
            "-" -> listOf()
            "K" -> listOf(CastlingOption.WHITE_K)
            "Q" -> listOf(CastlingOption.WHITE_Q)
            "k" -> listOf(CastlingOption.BLACK_K)
            "q" -> listOf(CastlingOption.BLACK_Q)
            "KQ" -> listOf(CastlingOption.WHITE_K, CastlingOption.WHITE_Q)
            "Kk" -> listOf(CastlingOption.WHITE_K, CastlingOption.BLACK_K)
            "Kq" -> listOf(CastlingOption.WHITE_K, CastlingOption.BLACK_Q)
            "Qk" -> listOf(CastlingOption.WHITE_Q, CastlingOption.BLACK_K)
            "Qq" -> listOf(CastlingOption.WHITE_Q, CastlingOption.BLACK_Q)
            "kq" -> listOf(CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
            "KQk" -> listOf(CastlingOption.WHITE_K, CastlingOption.WHITE_Q, CastlingOption.BLACK_K)
            "KQq" -> listOf(CastlingOption.WHITE_K, CastlingOption.WHITE_Q, CastlingOption.BLACK_Q)
            "Kkq" -> listOf(CastlingOption.WHITE_K, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
            "Qkq" -> listOf(CastlingOption.WHITE_Q, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
            "KQkq" -> CastlingOption.values().asList()
            else -> throw IllegalArgumentException("bad part3: $part3")
        }
    }

    private fun enPassant(part4: String): EnPassantOption? {
        return when (part4) {
            "-" -> null
            "a3" -> EnPassantOption.A3
            "a6" -> EnPassantOption.A6
            "b3" -> EnPassantOption.B3
            "b6" -> EnPassantOption.B6
            "c3" -> EnPassantOption.C3
            "c6" -> EnPassantOption.C6
            "d3" -> EnPassantOption.D3
            "d6" -> EnPassantOption.D6
            "e3" -> EnPassantOption.E3
            "e6" -> EnPassantOption.E6
            "f3" -> EnPassantOption.F3
            "f6" -> EnPassantOption.F6
            "g3" -> EnPassantOption.G3
            "g6" -> EnPassantOption.G6
            "h3" -> EnPassantOption.H3
            "h6" -> EnPassantOption.H6
            else -> throw IllegalArgumentException("bad part4: $part4")
        }
    }

    private fun plies(part5: String): UInt {
        val int = part5.toIntOrNull()
        require (int != null && int >= 0) { "bad part5: $part5" }
        return int.toUInt()
    }

    private fun nextMove(part6: String): UInt {
        val int = part6.toIntOrNull()
        require (int != null && int >= 1) { "bad part5: $part6" }
        return int.toUInt()
    }
}
