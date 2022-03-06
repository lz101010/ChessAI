// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.util

object ArrayManipulation {
    fun rotate(pieceTable: Array<Int>): Array<Int> {
        validatePieceTable(pieceTable)

        return pieceTable.reversed().toTypedArray()
    }

    fun mirror(pieceTable: Array<Int>): Array<Int> {
        validatePieceTable(pieceTable)

        return (0..7)
            .map { pieceTable.copyOfRange(8 * it, 8 * it + 8) }
            .reversed()
            .flatMap { it.toList() }
            .toTypedArray()
    }

    private fun validatePieceTable(pieceTable: Array<Int>) {
        require(pieceTable.size == 64) { "pieceTables need length 64, not ${pieceTable.size}" }
    }
}
