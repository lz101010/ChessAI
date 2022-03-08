// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.abra

import com.lz101010.chess.core.*
import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import com.lz101010.chess.engine.Engine
import com.lz101010.chess.engine.abra.MoveOrder.order
import com.lz101010.chess.engine.abra.MoveOrder.orderPositions
import java.lang.Integer.max
import java.lang.Integer.min

private const val MAX_SEARCH_DEPTH = 3

data class AbraEngine(val searchDepth: Int = MAX_SEARCH_DEPTH): Engine {
    override fun nextMove(board: Board): Move {
        val allMoves = MoveGenerator.find(board)
        val bestPieceMoves = MiniMax(max(1, searchDepth), ScoreGenerator::simple).findBestMoves(board, allMoves)
        val bestPositionalMoves = MiniMax(1, ScoreGenerator::positional).findBestMoves(board, bestPieceMoves)
        return bestPositionalMoves.random()
    }
}

private data class MiniMax(val searchDepth: Int, val evaluate: (Board) -> Int) {
    fun findBestMoves(board: Board, moves: List<Move>): List<Move> {
        val (maxScore, bestMoves) = findBestMoveCandidates(board, moves)

        if (maxScore == Int.MAX_VALUE) {
            return ShortestMate.find(board, bestMoves, searchDepth - 1)
        }
        if (maxScore == Int.MIN_VALUE) {
            return LongestMate.find(board, bestMoves, searchDepth - 1)
        }

        return bestMoves
    }

    private fun findBestMoveCandidates(board: Board, moves: List<Move>): Pair<Int, List<Move>> {
        val evaluatedMoves = mutableListOf<EvaluatedMove>()
        var maxScore = Int.MIN_VALUE

        val moveMap = moves.associateBy { MoveMaker.move(board, it) }

        for (position in order(moveMap.keys)) {
            val move = moveMap[position]!!
            if (PositionEvaluator.isMate(position)) {
                return Pair(Int.MAX_VALUE, listOf(move))
            }
            val score = miniMax(position, 0, Int.MIN_VALUE, Int.MAX_VALUE, false)
            maxScore = max(maxScore, score)
            evaluatedMoves.add(EvaluatedMove(move, score))
        }

        val bestMoveCandidates = evaluatedMoves.filter { it.score == maxScore }.map { it.move }
        return Pair(maxScore, bestMoveCandidates)
    }

    private fun miniMax(position: Board, depth: Int, baseAlpha: Int, baseBeta: Int, maximizingPlayer: Boolean): Int {
        return recursionBase(position, depth) ?:

        return if (maximizingPlayer) {
            maximize(position, depth, baseAlpha, baseBeta)
        } else {
            minimize(position, depth, baseAlpha, baseBeta)
        }
    }

    private fun recursionBase(position: Board, depth: Int): Int? {
        val cached = PositionCache.read(position)
        if (cached != null) {
            return cached
        }
        if (depth >= searchDepth || isOver(position)) {
            val result = evaluate(position)
            PositionCache.write(position, result)
            return result
        }
        return null
    }

    private fun isOver(board: Board) = PositionEvaluator.isMate(board) || PositionEvaluator.isStaleMate(board)

    private fun maximize(position: Board, depth: Int, baseAlpha: Int, beta: Int): Int {
        var alpha = baseAlpha
        var result = Int.MIN_VALUE

        for (newPosition in orderPositions(position)) {
            val score = miniMax(newPosition, depth + 1, alpha, beta, false)
            result = max(result, score)
            alpha = max(alpha, score)

            if (beta <= alpha) {
                return result
            }
        }

        return result
    }

    private fun minimize(position: Board, depth: Int, alpha: Int, baseBeta: Int): Int {
        var beta = baseBeta
        var result = Int.MAX_VALUE

        for (newPosition in orderPositions(position)) {
            val score = miniMax(newPosition, depth + 1, alpha, beta, true)
            result = min(result, score)
            beta = min(beta, score)

            if (beta <= alpha) {
                return result
            }
        }

        return result
    }
}

internal data class EvaluatedMove(val move: Move, val score: Int)
