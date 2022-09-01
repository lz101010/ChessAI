// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.random

import com.lz101010.chess.core.MoveGenerator
import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import com.lz101010.chess.engine.Engine

object RandomEngine: Engine {
    override fun nextMove(board: Board): Move {
        return MoveGenerator.find(board).random()
    }
}
