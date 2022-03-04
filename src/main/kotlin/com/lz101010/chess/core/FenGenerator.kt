package com.lz101010.chess.core

import com.lz101010.chess.data.Board

object FenGenerator {
    fun generate(board: Board): String {
        return "${group1(board)} ${group2(board)} ${group3(board)} ${group4(board)} ${group5(board)} ${group6(board)}"
    }

    private fun group1(board: Board): String {
        return board.toString()
            .replace(" ", "")
            .split("\n")
            .joinToString("/", transform = ::normalizeEmptyFields)
    }

    private fun normalizeEmptyFields(rank: String): String {
        if (rank.length != 8) {
            throw IllegalArgumentException("bad length: ${rank.length} ($rank)")
        }
        return rank
            .replace("--------", "8")
            .replace("-------", "7")
            .replace("------", "6")
            .replace("-----", "5")
            .replace("----", "4")
            .replace("---", "3")
            .replace("--", "2")
            .replace("-", "1")
    }

    private fun group2(board: Board): String {
        return if (board.whiteToMove) "w" else "b"
    }

    private fun group3(board: Board): String {
        // TODO revisit: "K" = white king-side castling, "q" = black queen-side castling, "-" = no castling, etc.
        return "KQkq"
    }

    private fun group4(board: Board): String {
        // TODO revisit: possible en-passant
        return "-"
    }

    private fun group5(board: Board): String {
        // TODO revisit: number of plies
        return "0"
    }

    private fun group6(board: Board): String {
        // TODO revisit: next move
        return "1"
    }
}
