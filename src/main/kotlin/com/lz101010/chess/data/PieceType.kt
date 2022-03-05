// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

enum class PieceType(val shortName: String, val white: String, val black: String, val value: Int) {
    P("", "♙", "♟", 1),
    R("R", "♖", "♜", 5),
    N("N", "♘", "♞", 3),
    B("B", "♗", "♝", 3),
    Q("Q", "♕", "♛", 9),
    K("K", "♔", "♚", 0);

    val asWhite = Piece(this, true)
    val asBlack = Piece(this, false)
}
