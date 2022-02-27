// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

data class Move(
    val piece: Piece,
    val from: Field,
    val to: Field
) {
    val pretty = piece.pretty + from + to

    override fun toString(): String {
        return piece.type.shortName + from + to
    }
}
