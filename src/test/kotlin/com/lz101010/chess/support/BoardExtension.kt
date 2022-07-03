// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.support

import com.lz101010.chess.game.MoveMaker
import com.lz101010.chess.game.Board
import com.lz101010.chess.game.Move

fun Board.move(move: Move): Board {
    return MoveMaker.move(this, move)
}
