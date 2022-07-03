// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.game

import com.lz101010.chess.data.File
import com.lz101010.chess.data.PieceType
import com.lz101010.chess.data.Rank
import com.lz101010.chess.data.Square

object MoveGenerator {
    fun find(board: Board): List<Move> {
        val mappedBoard = com.github.bhlangonijr.chesslib.Board()
        mappedBoard.loadFromFen(FenGenerator.generate(board))

        val moves = com.github.bhlangonijr.chesslib.move.MoveGenerator.generateLegalMoves(mappedBoard)

        return moves.map(::convert)
    }

    private fun convert(move: com.github.bhlangonijr.chesslib.move.Move): Move {
        return Move(convert(move.from), convert(move.to), move.promotion.pieceType?.let(::convert))
    }

    private fun convert(pieceType: com.github.bhlangonijr.chesslib.PieceType): PieceType {
        return when (pieceType) {
            com.github.bhlangonijr.chesslib.PieceType.KNIGHT -> PieceType.N
            com.github.bhlangonijr.chesslib.PieceType.BISHOP -> PieceType.B
            com.github.bhlangonijr.chesslib.PieceType.ROOK -> PieceType.R
            com.github.bhlangonijr.chesslib.PieceType.QUEEN -> PieceType.Q
            else -> throw IllegalArgumentException("cannot convert $pieceType")
        }
    }

    private fun convert(square: com.github.bhlangonijr.chesslib.Square): Square {
        return Square.values().first { it.file == convert(square.file) && it.rank == convert(square.rank) }
    }

    private fun convert(file: com.github.bhlangonijr.chesslib.File): File {
        return when (file) {
            com.github.bhlangonijr.chesslib.File.FILE_A -> File.FILE_A
            com.github.bhlangonijr.chesslib.File.FILE_B -> File.FILE_B
            com.github.bhlangonijr.chesslib.File.FILE_C -> File.FILE_C
            com.github.bhlangonijr.chesslib.File.FILE_D -> File.FILE_D
            com.github.bhlangonijr.chesslib.File.FILE_E -> File.FILE_E
            com.github.bhlangonijr.chesslib.File.FILE_F -> File.FILE_F
            com.github.bhlangonijr.chesslib.File.FILE_G -> File.FILE_G
            com.github.bhlangonijr.chesslib.File.FILE_H -> File.FILE_H
            com.github.bhlangonijr.chesslib.File.NONE -> throw IllegalArgumentException("cannot map NONE")
        }
    }

    private fun convert(rank: com.github.bhlangonijr.chesslib.Rank): Rank {
        return when (rank) {
            com.github.bhlangonijr.chesslib.Rank.RANK_1 -> Rank.RANK_1
            com.github.bhlangonijr.chesslib.Rank.RANK_2 -> Rank.RANK_2
            com.github.bhlangonijr.chesslib.Rank.RANK_3 -> Rank.RANK_3
            com.github.bhlangonijr.chesslib.Rank.RANK_4 -> Rank.RANK_4
            com.github.bhlangonijr.chesslib.Rank.RANK_5 -> Rank.RANK_5
            com.github.bhlangonijr.chesslib.Rank.RANK_6 -> Rank.RANK_6
            com.github.bhlangonijr.chesslib.Rank.RANK_7 -> Rank.RANK_7
            com.github.bhlangonijr.chesslib.Rank.RANK_8 -> Rank.RANK_8
            com.github.bhlangonijr.chesslib.Rank.NONE -> throw IllegalArgumentException("cannot map NONE")
        }
    }
}
