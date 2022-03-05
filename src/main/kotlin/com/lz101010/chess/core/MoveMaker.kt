// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.*

object MoveMaker {
    fun move(board: Board, move: Move): Board {
        validate(board, move)

        val pieces: Array<Array<Piece?>> = Rank.values().reversed().map { rank ->
            File.values().map { file ->
                piece(move, file, rank, board[file, rank])
            }.toTypedArray()
        }.toTypedArray()

        return Board(
            pieces = pieces,
            whiteToMove = !board.whiteToMove,
            castlingOptions = updateCastlingOptions(board, move),
            plies = updatePlies(board, move),
            nextMove = if (board.whiteToMove) board.nextMove else board.nextMove + 1u
        )
    }

    private fun validate(board: Board, move: Move) {
        require(move.piece.white == board.whiteToMove) { "${if (board.whiteToMove) "white" else "black"} to move" }
        require(board[move.from] == move.piece) { "${board[move.from]} != ${move.piece}" }
        require(board[move.to]?.white != board.whiteToMove) { "${move.to} is occupied by same color piece" }
    }

    private fun piece(
        move: Move,
        file: File,
        rank: Rank,
        defaultPiece: Piece?
    ): Piece? {
        if (move.from.file == file && move.from.rank == rank) {
            return null
        }
        if (move.to.file == file && move.to.rank == rank) {
            return move.piece
        }
        return defaultPiece
    }

    private fun updateCastlingOptions(board: Board, move: Move): Collection<CastlingOption> {
        return when (move.piece.type) {
            PieceType.R -> rookMoved(move, board)
            PieceType.K -> kingMoved(move, board)
            else -> board.castlingOptions
        }
    }

    private fun kingMoved(move: Move, board: Board): Collection<CastlingOption> {
        return if (move.piece.white) {
            remove(board, CastlingOption.WHITE_K, CastlingOption.WHITE_Q)
        } else {
            remove(board, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
        }
    }

    private fun rookMoved(move: Move, board: Board): Collection<CastlingOption> {
        return when (move.from.file) {
            File.FILE_A -> remove(board, if (move.piece.white) CastlingOption.WHITE_Q else CastlingOption.BLACK_Q)
            File.FILE_H -> remove(board, if (move.piece.white) CastlingOption.WHITE_K else CastlingOption.BLACK_K)
            else -> board.castlingOptions
        }
    }

    private fun remove(board: Board, vararg castlingOptions: CastlingOption) =
        board.castlingOptions.filterNot { castlingOptions.contains(it) }


    private fun updatePlies(board: Board, move: Move): UInt {
        if (move.piece.type == PieceType.P || board[move.to] != null) {
            return 0u
        }
        return board.plies + 1u
    }
}
