// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.alakazam

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import com.lz101010.chess.engine.Engine

object AlakazamEngine: Engine {
    override fun nextMove(board: Board): Move {
        TODO("Not yet implemented")
        // TODO: expand upon KadabraEngine:
        // TODO: expand evaluation to consider mobility: pinning, rooks on open files, covered squares, number of moves
        // TODO: expand evaluation to consider pawns: structure, passed pawn, double pawn
        // TODO: add endgame table bases
        // TODO: more pruning, see e.g. https://www.chessprogramming.org/Pruning and /Late_Move_Reductions
        // TODO: add check extensions, see e.g. https://www.chessprogramming.org/Mate_Threat_Extensions
    }
}
