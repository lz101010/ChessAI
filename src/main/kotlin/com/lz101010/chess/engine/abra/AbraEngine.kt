// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.abra

import com.lz101010.chess.core.*
import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import com.lz101010.chess.engine.Engine
import com.lz101010.chess.engine.abra.MoveOrder.order
import com.lz101010.chess.engine.abra.MoveOrder.orderPositions
import mu.KotlinLogging
import java.lang.Integer.max
import java.lang.Integer.min

private const val MAX_SEARCH_DEPTH = 3
private val logger = KotlinLogging.logger {}


data class AbraEngine(val searchDepth: Int = MAX_SEARCH_DEPTH): Engine {
    override fun nextMove(board: Board): Move {
        val allMoves = MoveGenerator.find(board)
        val bestPieceMoves = MiniMax(max(1, searchDepth), ScoreGenerator::simple).findBestMoves(board, allMoves)
        val bestPositionalMoves = filterMin(bestPieceMoves) { ScoreGenerator.positional(MoveMaker.move(board, it)) }
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

        val moveMap = moves.associateBy { MoveMaker.move(board, it).copy(lastMoves = listOf("${board[it.from]!!.basic}$it")) }

        for (position in order(moveMap.keys)) {
            val move = moveMap[position]!!
            if (PositionEvaluator.isMate(position)) {
                return Pair(Int.MAX_VALUE, listOf(move))
            }
            val score = miniMax(position, 0, Int.MIN_VALUE, Int.MAX_VALUE, false)
            logParent(position, score)
            maxScore = max(maxScore, score)
            evaluatedMoves.add(EvaluatedMove(move, score))
        }

        val bestMoveCandidates = filterMax(evaluatedMoves) { it.score }
        logRoot(bestMoveCandidates)

        return Pair(maxScore, bestMoveCandidates.map { it.move })
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
            logNode(depth, newPosition, score)
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
            logNode(depth, newPosition, score)
            result = min(result, score)
            beta = min(beta, score)

            if (beta <= alpha) {
                return result
            }
        }

        return result
    }

    private fun logRoot(bestMoveCandidates: List<EvaluatedMove>) {
        if (searchDepth == MAX_SEARCH_DEPTH) {
            logger.info("  moves:")
            logger.info("  best_score: ${bestMoveCandidates.first().score}")
            logger.info("result:")
        }
    }

    private fun logParent(position: Board, score: Int) {
        if (searchDepth == MAX_SEARCH_DEPTH) {
            logger.debug("    children:")
            logger.debug("  - ${position.lastMoves.last()}: $score")
        }
    }

    private fun logNode(depth: Int, position: Board, score: Int) {
        if (searchDepth == MAX_SEARCH_DEPTH) {
            if (depth + 1 < searchDepth) {
                logger.debug("${whitespace(depth)}    children:")
            }
            logger.debug("${whitespace(depth)}  - ${position.lastMoves.last()}: $score")
        }
    }
}

internal data class EvaluatedMove(val move: Move, val score: Int)

private fun <T> filterMax(values: Collection<T>, basedOn: (T) -> Int): List<T> {
    val max = values.maxOf(basedOn)
    return values.filter { basedOn(it) == max }
}

private fun <T> filterMin(values: Collection<T>, basedOn: (T) -> Int): List<T> {
    val max = values.minOf(basedOn)
    return values.filter { basedOn(it) == max }
}

private fun whitespace(indent: Int): String {
    return (0..indent).joinToString("") { "  " }
}
