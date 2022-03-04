// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

import com.lz101010.chess.data.PieceType.R
import com.lz101010.chess.data.PieceType.N
import com.lz101010.chess.data.PieceType.B
import com.lz101010.chess.data.PieceType.P
import com.lz101010.chess.data.PieceType.Q
import com.lz101010.chess.data.PieceType.K
import java.util.*

private val INITIAL_BOARD_LAYOUT = arrayOf(
    arrayOf<Piece?>(R.asBlack, N.asBlack, B.asBlack, Q.asBlack, K.asBlack, B.asBlack, N.asBlack, R.asBlack),
    arrayOf<Piece?>(P.asBlack, P.asBlack, P.asBlack, P.asBlack, P.asBlack, P.asBlack, P.asBlack, P.asBlack),
    arrayOf<Piece?>(null, null, null, null, null, null, null, null),
    arrayOf<Piece?>(null, null, null, null, null, null, null, null),
    arrayOf<Piece?>(null, null, null, null, null, null, null, null),
    arrayOf<Piece?>(null, null, null, null, null, null, null, null),
    arrayOf<Piece?>(P.asWhite, P.asWhite, P.asWhite, P.asWhite, P.asWhite, P.asWhite, P.asWhite, P.asWhite),
    arrayOf<Piece?>(R.asWhite, N.asWhite, B.asWhite, Q.asWhite, K.asWhite, B.asWhite, N.asWhite, R.asWhite)
)

private val EMPTY_BOARD_LAYOUT = arrayOf(
    arrayOfNulls<Piece?>(8),
    arrayOfNulls<Piece?>(8),
    arrayOfNulls<Piece?>(8),
    arrayOfNulls<Piece?>(8),
    arrayOfNulls<Piece?>(8),
    arrayOfNulls<Piece?>(8),
    arrayOfNulls<Piece?>(8),
    arrayOfNulls<Piece?>(8)
)

data class Board(
    val pieces: Array<Array<Piece?>> = INITIAL_BOARD_LAYOUT,
    val whiteToMove: Boolean = true
) {
    companion object {
        var empty = Board(pieces = EMPTY_BOARD_LAYOUT)
    }

    fun evalScore(): Int {
        return pieces.flatten()
            .filterNotNull()
            .filter { whiteToMove == it.white }
            .sumOf { it.value }
    }

    operator fun get(square: Square): Piece? = get(square.file, square.rank)

    operator fun get(file: File, rank: Rank): Piece? {
        val row = 7 - rank.ordinal
        val col = file.ordinal
        return pieces[row][col]
    }

    override fun toString(): String {
        return pieces.joinToString("\n") { row -> row.joinToString(" ") { it?.basic ?: "-" } }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        return pieces.contentDeepEquals((other as Board).pieces) && whiteToMove == other.whiteToMove
    }

    override fun hashCode(): Int {
        return Objects.hash(pieces.contentDeepHashCode(), whiteToMove)
    }
}
