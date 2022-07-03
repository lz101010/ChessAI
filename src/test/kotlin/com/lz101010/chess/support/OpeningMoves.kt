// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.support

import com.lz101010.chess.data.Square
import com.lz101010.chess.game.Move

object OpeningMoves {
    val A3 = Move(Square.A2, Square.A3)
    val B3 = Move(Square.B2, Square.B3)
    val C3 = Move(Square.C2, Square.C3)
    val D3 = Move(Square.D2, Square.D3)
    val E3 = Move(Square.E2, Square.E3)
    val F3 = Move(Square.F2, Square.F3)
    val G3 = Move(Square.G2, Square.G3)
    val H3 = Move(Square.H2, Square.H3)

    val A4 = Move(Square.A2, Square.A4)
    val B4 = Move(Square.B2, Square.B4)
    val C4 = Move(Square.C2, Square.C4)
    val D4 = Move(Square.D2, Square.D4)
    val E4 = Move(Square.E2, Square.E4)
    val F4 = Move(Square.F2, Square.F4)
    val G4 = Move(Square.G2, Square.G4)
    val H4 = Move(Square.H2, Square.H4)

    val Na3 = Move(Square.B1, Square.A3)
    val Nc3 = Move(Square.B1, Square.C3)
    val Nf3 = Move(Square.G1, Square.F3)
    val Nh3 = Move(Square.G1, Square.H3)

    val A6 = Move(Square.A7, Square.A6)
    val B6 = Move(Square.B7, Square.B6)
    val C6 = Move(Square.C7, Square.C6)
    val D6 = Move(Square.D7, Square.D6)
    val E6 = Move(Square.E7, Square.E6)
    val F6 = Move(Square.F7, Square.F6)
    val G6 = Move(Square.G7, Square.G6)
    val H6 = Move(Square.H7, Square.H6)

    val A5 = Move(Square.A7, Square.A5)
    val B5 = Move(Square.B7, Square.B5)
    val C5 = Move(Square.C7, Square.C5)
    val D5 = Move(Square.D7, Square.D5)
    val E5 = Move(Square.E7, Square.E5)
    val F5 = Move(Square.F7, Square.F5)
    val G5 = Move(Square.G7, Square.G5)
    val H5 = Move(Square.H7, Square.H5)

    val Na6 = Move(Square.B8, Square.A6)
    val Nc6 = Move(Square.B8, Square.C6)
    val Nf6 = Move(Square.G8, Square.F6)
    val Nh6 = Move(Square.G8, Square.H6)
}
