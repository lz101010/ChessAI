// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.kadabra

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import com.lz101010.chess.engine.Engine

object KadabraEngine: Engine {
    override fun nextMove(board: Board): Move {
        TODO("Not yet implemented")
        // TODO: expand upon AbraEngine:
        // TODO: add opening book
        // TODO: expand move ordering to look ahead 2 moves and prioritize: captures/simplification, check, pinning
        // TODO: expand evaluation to relative advantage and technical mates
        // TODO: as soon as mate (or "winning") is found: decrease search depth
    }
}
