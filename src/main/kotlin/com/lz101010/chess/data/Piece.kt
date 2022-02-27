// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

data class Piece(val type: PieceType, val white: Boolean) {
    val black = !white

    val basic =
        if (white) {
            type.name.uppercase()
        } else {
            type.name.lowercase()
        }

    val pretty =
        if (white) {
            type.white
        } else {
            type.black
        }

    val value = type.value
}
