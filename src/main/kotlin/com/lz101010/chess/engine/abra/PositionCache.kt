// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.abra

import com.lz101010.chess.data.Board

internal object PositionCache {
    var cache = mutableMapOf<Int, Int>()

    fun write(board: Board, score: Int) {
        cache[board.hashCode()] = score
    }

    fun read(board: Board): Int? {
        return cache[board.hashCode()]
    }
}
