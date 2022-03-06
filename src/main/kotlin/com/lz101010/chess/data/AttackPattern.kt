// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

data class AttackPattern(val attackMoves: List<AttackMove>)

data class AttackMove(val deltaX: Int, val deltaY: Int, val repeat: Boolean = false)
