// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.moves

import com.lz101010.chess.data.PieceType

data class AttackSet(val attackPatterns: List<AttackPattern>)

data class AttackPattern(val deltaX: Int, val deltaY: Int, val repeat: Boolean = false, val captures: Boolean = false)

object AttackSetGenerator {
    fun generate(pieceType: PieceType): AttackSet = attackSets[pieceType]!!

    private val pawnAttacks = listOf(
        AttackPattern(deltaX = 1, deltaY = 1, captures = true),
        AttackPattern(deltaX = -1, deltaY = 1, captures = true)
    )

    private val rookMoves = listOf(
        AttackPattern(deltaX = 0, deltaY = 1, repeat = true),
        AttackPattern(deltaX = 0, deltaY = -1, repeat = true),
        AttackPattern(deltaX = 1, deltaY = 0, repeat = true),
        AttackPattern(deltaX = -1, deltaY = 0, repeat = true)
    )

    private val knightMoves = listOf(
        AttackPattern(deltaX = 2, deltaY = 1), AttackPattern(deltaX = 2, deltaY = -1),
        AttackPattern(deltaX = 1, deltaY = 2), AttackPattern(deltaX = 1, deltaY = -2),
        AttackPattern(deltaX = -2, deltaY = 1), AttackPattern(deltaX = -2, deltaY = -1),
        AttackPattern(deltaX = -1, deltaY = 2), AttackPattern(deltaX = -1, deltaY = -2)
    )

    private val bishopMoves = listOf(
        AttackPattern(deltaX = 1, deltaY = 1, repeat = true),
        AttackPattern(deltaX = 1, deltaY = -1, repeat = true),
        AttackPattern(deltaX = -1, deltaY = 1, repeat = true),
        AttackPattern(deltaX = -1, deltaY = -1, repeat = true)
    )

    private val queenMoves = listOf(bishopMoves, rookMoves).flatten()

    private val kingMoves = queenMoves.map { it.copy(repeat = false) }

    private val attackSets = mapOf(
        PieceType.P to AttackSet(pawnAttacks),
        PieceType.R to AttackSet(rookMoves),
        PieceType.N to AttackSet(knightMoves),
        PieceType.B to AttackSet(bishopMoves),
        PieceType.Q to AttackSet(queenMoves),
        PieceType.K to AttackSet(kingMoves)
    )
}
