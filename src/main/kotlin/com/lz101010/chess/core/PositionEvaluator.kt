// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.*

object PositionEvaluator {
    fun isMate(board: Board): Boolean = isCheck(board) && noMovesLeft(board)

    fun isStaleMate(board: Board): Boolean = (!isCheck(board) && noMovesLeft(board)) || board.plies >= 100u

    fun isCheck(board: Board): Boolean {
        val target = find(Piece(PieceType.K, board.whiteToMove), board).first()

        val attackedSquares = AttackedSquaresGenerator.generate(board)

        return attackedSquares.contains(target)
    }

    private fun find(piece: Piece, board: Board): List<Square> = Square.values().filter { board[it] == piece }

    private fun noMovesLeft(board: Board) = MoveGenerator.find(board).isEmpty()
}
