// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.abra

import com.lz101010.chess.core.MoveGenerator
import com.lz101010.chess.core.MoveMaker
import com.lz101010.chess.core.PositionEvaluator
import com.lz101010.chess.data.Board

internal object MoveOrder {
    fun orderPositions(board: Board): List<Board> {
        return MoveGenerator.find(board).map { MoveMaker.move(board, it) }.let(::order)
    }

    fun order(positions: Collection<Board>): List<Board> {
        return positions.sortedBy { position ->
            if (PositionEvaluator.isCheck(position)) 0 else 1
        }
    }
}
