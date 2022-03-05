// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move

interface Engine {
    fun nextMove(board: Board): Move
}
