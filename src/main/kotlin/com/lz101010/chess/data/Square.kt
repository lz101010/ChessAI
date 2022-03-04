// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

enum class Square {
    A8, B8, C8, D8, E8, F8, G8, H8,
    A7, B7, C7, D7, E7, F7, G7, H7,
    A6, B6, C6, D6, E6, F6, G6, H6,
    A5, B5, C5, D5, E5, F5, G5, H5,
    A4, B4, C4, D4, E4, F4, G4, H4,
    A3, B3, C3, D3, E3, F3, G3, H3,
    A2, B2, C2, D2, E2, F2, G2, H2,
    A1, B1, C1, D1, E1, F1, G1, H1;

    override fun toString(): String {
        return super.toString().lowercase()
    }

    val file: File
        get() {
            return when (this) {
                A1, A2, A3, A4, A5, A6, A7, A8 -> File.FILE_A
                B1, B2, B3, B4, B5, B6, B7, B8 -> File.FILE_B
                C1, C2, C3, C4, C5, C6, C7, C8 -> File.FILE_C
                D1, D2, D3, D4, D5, D6, D7, D8 -> File.FILE_D
                E1, E2, E3, E4, E5, E6, E7, E8 -> File.FILE_E
                F1, F2, F3, F4, F5, F6, F7, F8 -> File.FILE_F
                G1, G2, G3, G4, G5, G6, G7, G8 -> File.FILE_G
                H1, H2, H3, H4, H5, H6, H7, H8 -> File.FILE_H
            }
        }

    val rank: Rank
        get() {
            return when (this) {
                A1, B1, C1, D1, E1, F1, G1, H1 -> Rank.RANK_1
                A2, B2, C2, D2, E2, F2, G2, H2 -> Rank.RANK_2
                A3, B3, C3, D3, E3, F3, G3, H3 -> Rank.RANK_3
                A4, B4, C4, D4, E4, F4, G4, H4 -> Rank.RANK_4
                A5, B5, C5, D5, E5, F5, G5, H5 -> Rank.RANK_5
                A6, B6, C6, D6, E6, F6, G6, H6 -> Rank.RANK_6
                A7, B7, C7, D7, E7, F7, G7, H7 -> Rank.RANK_7
                A8, B8, C8, D8, E8, F8, G8, H8 -> Rank.RANK_8
            }
        }
}
