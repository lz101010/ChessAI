// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

data class Move(
    val from: Square,
    val to: Square,
    val promotion: PieceType? = null
) {
    val san = "$to" + (promotion ?: "")
    val lan = "$from$to" + (promotion ?: "")

    override fun toString(): String {
        return lan
    }
}
