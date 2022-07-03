// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.game

import com.lz101010.chess.data.PieceType

object AttackPatternGenerator {
    fun generate(pieceType: PieceType): AttackPattern {
        return AttackPattern(attackMoves(pieceType))
    }

    private fun attackMoves(pieceType: PieceType): List<AttackMove> {
        return when (pieceType) {
            PieceType.P -> pawnAttacks()
            PieceType.R -> rookMoves()
            PieceType.N -> knightMoves()
            PieceType.B -> bishopMoves()
            PieceType.Q -> queenMoves()
            PieceType.K -> kingMoves()
        }
    }

    private fun pawnAttacks() = listOf(AttackMove(deltaX = 1, deltaY = 1), AttackMove(deltaX = -1, deltaY = 1))

    private fun rookMoves() = listOf(
        AttackMove(deltaX = 0, deltaY = 1, repeat = true),
        AttackMove(deltaX = 0, deltaY = -1, repeat = true),
        AttackMove(deltaX = 1, deltaY = 0, repeat = true),
        AttackMove(deltaX = -1, deltaY = 0, repeat = true)
    )

    private fun knightMoves() = listOf(
        AttackMove(deltaX = 2, deltaY = 1), AttackMove(deltaX = 2, deltaY = -1),
        AttackMove(deltaX = 1, deltaY = 2), AttackMove(deltaX = 1, deltaY = -2),
        AttackMove(deltaX = -2, deltaY = 1), AttackMove(deltaX = -2, deltaY = -1),
        AttackMove(deltaX = -1, deltaY = 2), AttackMove(deltaX = -1, deltaY = -2)
    )

    private fun bishopMoves() = listOf(
        AttackMove(deltaX = 1, deltaY = 1, repeat = true),
        AttackMove(deltaX = 1, deltaY = -1, repeat = true),
        AttackMove(deltaX = -1, deltaY = 1, repeat = true),
        AttackMove(deltaX = -1, deltaY = -1, repeat = true)
    )

    private fun queenMoves() = listOf(bishopMoves(), rookMoves()).flatten()

    private fun kingMoves() = queenMoves().map { it.copy(repeat = false) }
}
