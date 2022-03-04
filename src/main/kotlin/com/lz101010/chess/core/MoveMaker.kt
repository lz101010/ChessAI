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

        return Board(pieces = pieces, whiteToMove = !board.whiteToMove)
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
}
