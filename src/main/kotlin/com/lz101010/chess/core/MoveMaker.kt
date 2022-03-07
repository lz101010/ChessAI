// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.*
import kotlin.math.abs

object MoveMaker {
    fun move(board: Board, move: Move): Board {
        val movedPiece = validate(board, move)
        val isEnPassant = isEnPassant(board, move, movedPiece)
        val pieces = Rank.values().reversed().map { rank ->
            File.values().map { file ->
                piece(move, file, rank, isEnPassant, board[file, rank], movedPiece)
            }.toTypedArray()
        }.toTypedArray()

        return Board(
            pieces = pieces,
            whiteToMove = !board.whiteToMove,
            castlingOptions = updateCastlingOptions(board, move, movedPiece),
            enPassant = updateEnPassant(move, movedPiece),
            plies = updatePlies(board, move, movedPiece),
            nextMove = if (board.whiteToMove) board.nextMove else board.nextMove + 1u
        )
    }

    private fun validate(board: Board, move: Move): Piece {
        val piece = board[move.from]
        require(piece != null) { "${move.from} is empty" }
        require(piece.white == board.whiteToMove) { "${if (board.whiteToMove) "white" else "black"} to move" }
        require(board[move.to]?.white != board.whiteToMove) { "${move.to} is occupied by same color piece" }
        return piece
    }

    private fun isEnPassant(board: Board, move: Move, movedPiece: Piece): Boolean {
        return board.enPassant != null
                && movedPiece.type == PieceType.P
                && move.to.name == board.enPassant.name // shortcut hack
    }

    private fun piece(
        move: Move,
        file: File,
        rank: Rank,
        isEnPassant: Boolean,
        oldPiece: Piece?,
        movedPiece: Piece
    ): Piece? {
        if (move.from.file == file && move.from.rank == rank) { // vacated square
            return null
        }
        if (move.to.file == file && move.to.rank == rank) { // target square
            return move.promotion?.let { Piece(it, movedPiece.white) } ?: movedPiece
        }
        if (isCastling(move, movedPiece) && rank == move.to.rank) { // move rook
            return castle(move.to.file == File.FILE_G, move.to.rank == Rank.RANK_1, file, oldPiece)
        }
        if (isEnPassant) { // remove pawn
            return enPassant(move.to, file, rank, oldPiece)
        }
        return oldPiece
    }

    private fun isCastling(move: Move, piece: Piece): Boolean {
        return piece.type == PieceType.K && abs(move.from.file.ordinal - move.to.file.ordinal) == 2
    }

    private fun castle(
        kingSide: Boolean,
        white: Boolean,
        file: File,
        defaultPiece: Piece?
    ): Piece? {
        return if (kingSide) {
            when (file) {
                File.FILE_F -> Piece(PieceType.R, white)
                File.FILE_H -> null
                else -> defaultPiece
            }
        } else {
            when (file) {
                File.FILE_D -> Piece(PieceType.R, white)
                File.FILE_A -> null
                else -> defaultPiece
            }
        }
    }

    private fun enPassant(target: Square, file: File, rank: Rank, defaultPiece: Piece?): Piece? {
        if (target.file != file) {
            return defaultPiece
        }
        if (target.rank == Rank.RANK_3 && rank == Rank.RANK_4 || target.rank == Rank.RANK_6 && rank == Rank.RANK_5) {
            return null
        }
        return defaultPiece
    }

    private fun updateCastlingOptions(board: Board, move: Move, movedPiece: Piece): Collection<CastlingOption> {
        val sameSide = newCastlingOptionsSameSide(board, move, movedPiece)
        val otherSide = rookCaptured(board, move, movedPiece.black)

        return if (sameSide != null) {
            if (otherSide != null) sameSide.intersect(otherSide.toSet()) else sameSide
        } else {
            otherSide ?: board.castlingOptions
        }
    }

    private fun newCastlingOptionsSameSide(board: Board, move: Move, movedPiece: Piece): Collection<CastlingOption>? {
        return when (movedPiece.type) {
            PieceType.R -> rookMoved(board, move, movedPiece.white)
            PieceType.K -> kingMoved(board, movedPiece.white)
            else -> null
        }
    }

    private fun rookMoved(board: Board, move: Move, white: Boolean): Collection<CastlingOption>? {
        if (!isBackRow(move.from.rank, white)) {
            return null
        }
        return when (move.from.file) {
            File.FILE_A -> remove(board, if (white) CastlingOption.WHITE_Q else CastlingOption.BLACK_Q)
            File.FILE_H -> remove(board, if (white) CastlingOption.WHITE_K else CastlingOption.BLACK_K)
            else -> null
        }
    }

    private fun isBackRow(rank: Rank, white: Boolean): Boolean {
        return if (white) rank == Rank.RANK_1 else rank == Rank.RANK_8
    }

    private fun kingMoved(board: Board, white: Boolean): Collection<CastlingOption> {
        return if (white) {
            remove(board, CastlingOption.WHITE_K, CastlingOption.WHITE_Q)
        } else {
            remove(board, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
        }
    }

    private fun remove(board: Board, vararg castlingOptions: CastlingOption) =
        board.castlingOptions.filterNot { castlingOptions.contains(it) }

    private fun rookCaptured(board: Board, move: Move, white: Boolean): Collection<CastlingOption>? {
        val piece = board[move.to]
        if (piece == null || piece.type != PieceType.R || !isBackRow(move.to.rank, white)) {
            return null
        }
        return when (move.to.file) {
            File.FILE_A -> remove(board, if (white) CastlingOption.WHITE_Q else CastlingOption.BLACK_Q)
            File.FILE_H -> remove(board, if (white) CastlingOption.WHITE_K else CastlingOption.BLACK_K)
            else -> null
        }
    }

    private fun updatePlies(board: Board, move: Move, movedPiece: Piece): UInt {
        if (movedPiece.type == PieceType.P || board[move.to] != null) {
            return 0u
        }
        return board.plies + 1u
    }

    private fun updateEnPassant(move: Move, movedPiece: Piece): EnPassantOption? {
        if (movedPiece.type != PieceType.P) {
            return null
        }
        if (abs(move.from.rank.ordinal - move.to.rank.ordinal) != 2) {
            return null
        }
        return when (move.to) {
            Square.A4 -> EnPassantOption.A3
            Square.B4 -> EnPassantOption.B3
            Square.C4 -> EnPassantOption.C3
            Square.D4 -> EnPassantOption.D3
            Square.E4 -> EnPassantOption.E3
            Square.F4 -> EnPassantOption.F3
            Square.G4 -> EnPassantOption.G3
            Square.H4 -> EnPassantOption.H3
            Square.A5 -> EnPassantOption.A6
            Square.B5 -> EnPassantOption.B6
            Square.C5 -> EnPassantOption.C6
            Square.D5 -> EnPassantOption.D6
            Square.E5 -> EnPassantOption.E6
            Square.F5 -> EnPassantOption.F6
            Square.G5 -> EnPassantOption.G6
            Square.H5 -> EnPassantOption.H6
            else -> throw IllegalStateException("uncovered scenario: $move")
        }
    }
}
