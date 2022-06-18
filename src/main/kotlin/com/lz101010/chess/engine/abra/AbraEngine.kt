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

private const val DEFAULT_SEARCH_DEPTH = 4
private const val MIN_SEARCH_DEPTH = 1
private const val MAX_SEARCH_DEPTH = 6
private val logger = KotlinLogging.logger {}


data class AbraEngine(val searchDepth: Int = DEFAULT_SEARCH_DEPTH, val log: Boolean = false): Engine {
    override fun nextMove(board: Board): Move {
        val allMoves = MoveGenerator.find(board)
        val bestPieceMoves = MiniMax(limit(searchDepth), ScoreGenerator::simple, log).findBestMoves(board, allMoves)
        val bestPositionalMoves = filterMin(bestPieceMoves) { ScoreGenerator.positional(MoveMaker.move(board, it)) }
        return bestPositionalMoves.random()
    }
}

private fun limit(searchDepth: Int): Int {
    if (searchDepth < MIN_SEARCH_DEPTH) {
        return MIN_SEARCH_DEPTH
    }
    if (searchDepth > MAX_SEARCH_DEPTH) {
        return MAX_SEARCH_DEPTH
    }
    return searchDepth
}

private data class MiniMax(val searchDepth: Int, val evaluate: (Board) -> Int, val log: Boolean) {
    fun findBestMoves(board: Board, moves: List<Move>): List<Move> {
        val bestMoves = findBestMoveCandidates(board, moves)

        val maxScoreValue = bestMoves.maxByOrNull { it.score.value }?.score?.value ?: Int.MIN_VALUE

        if (maxScoreValue == Int.MAX_VALUE) {
            return filterMin(bestMoves) { it.score.depth }.map { it.move }
        }
        if (maxScoreValue == Int.MIN_VALUE) {
            return filterMax(bestMoves) { it.score.depth }.map { it.move }
        }

        return bestMoves.map { it.move }
    }

    private fun findBestMoveCandidates(board: Board, moves: List<Move>): List<EvaluatedMove> {
        val evaluatedMoves = mutableListOf<EvaluatedMove>()
        var maxScore = Score(Int.MIN_VALUE, -1)

        val moveMap = moves.associateBy { MoveMaker.move(board, it).copy(lastMoves = listOf("${board[it.from]!!.basic}$it")) }

        for (position in order(moveMap.keys)) {
            val move = moveMap[position]!!
            if (PositionEvaluator.isMate(position)) {
                return listOf(EvaluatedMove(move, Score(Int.MAX_VALUE, 0)))
            }
            val score = miniMax(position, 0, Int.MIN_VALUE, Int.MAX_VALUE, false)
            logParent(position, score)
            maxScore = max(maxScore, score)
            evaluatedMoves.add(EvaluatedMove(move, score))
        }

        val bestMoveCandidates = filterMax(evaluatedMoves) { it.score.value }
        logRoot(bestMoveCandidates)

        println(bestMoveCandidates)

        return bestMoveCandidates
    }

    private fun miniMax(position: Board, depth: Int, baseAlpha: Int, baseBeta: Int, maximizingPlayerNext: Boolean): Score {
        return recursionBase(position, depth, !maximizingPlayerNext) ?:

        return if (maximizingPlayerNext) {
            maximize(position, depth, baseAlpha, baseBeta)
        } else {
            minimize(position, depth, baseAlpha, baseBeta)
        }
    }

    private fun recursionBase(position: Board, depth: Int, maximizingPlayer: Boolean): Score? {
        val cached = PositionCache.read(position)
        if (cached != null) {
            return Score(cached, depth)
        }

        if (depth >= searchDepth || PositionEvaluator.isOver(position)) {
            val result = if (!maximizingPlayer) evaluate(position) else flip(evaluate(position))
            PositionCache.write(position, result)
            return Score(result, depth)
        }
        return null
    }

    private fun flip(value: Int): Int {
        return when (value) {
            Int.MAX_VALUE -> Int.MIN_VALUE
            Int.MIN_VALUE -> Int.MAX_VALUE
            else -> return -value
        }
    }

    private fun maximize(position: Board, depth: Int, baseAlpha: Int, beta: Int): Score {
        var alpha = baseAlpha
        var result = Score(Int.MIN_VALUE, Int.MAX_VALUE)

        for (newPosition in orderPositions(position)) {
            val score = miniMax(newPosition, depth + 1, alpha, beta, false)
            logNode(depth, newPosition, score, "//${max(score, result)}")
            result = max(score, result)
            alpha = max(alpha, score.value)

            if (beta <= alpha) {
                return result
            }
        }

        return result
    }

    private fun minimize(position: Board, depth: Int, alpha: Int, baseBeta: Int): Score {
        var beta = baseBeta
        var result = Score(Int.MAX_VALUE, Int.MIN_VALUE)

        for (newPosition in orderPositions(position)) {
            val score = miniMax(newPosition, depth + 1, alpha, beta, true)
            logNode(depth, newPosition, score, "..${min(score, result)}")
            result = min(score, result)
            beta = min(beta, score.value)

            if (beta <= alpha) {
                return result
            }
        }

        return result
    }

    private fun logRoot(bestMoveCandidates: List<EvaluatedMove>) {
        if (log) {
            logger.info("  moves:")
            logger.info("  best_score: ${bestMoveCandidates.first().score}")
            logger.info("result:")
        }
    }

    private fun logParent(position: Board, score: Score) {
        if (log) {
            logger.debug("    children:")
            logger.debug("  - ${position.lastMoves.last()}: $score")
        }
    }

    private fun logNode(depth: Int, position: Board, score: Score, detail: String = "") {
        if (log) {
            if (depth + 1 < searchDepth) {
                logger.debug("${whitespace(depth)}    children:")
            }
            logger.debug("${whitespace(depth)}  - ${position.lastMoves.last()}: $score $detail")
        }
    }
}

internal data class Score(val value: Int, val depth: Int)
internal data class EvaluatedMove(val move: Move, val score: Score)

private fun <T> filterMax(values: Collection<T>, basedOn: (T) -> Int): List<T> {
    val max = values.maxOf(basedOn)
    return values.filter { basedOn(it) == max }
}

private fun <T> filterMin(values: Collection<T>, basedOn: (T) -> Int): List<T> {
    val min = values.minOf(basedOn)
    return values.filter { basedOn(it) == min }
}

private fun whitespace(indent: Int): String {
    return (0..indent).joinToString("") { "  " }
}

private fun max(score1: Score, score2: Score): Score {
    return if (score1.value == score2.value) {
        if (score1.depth < score2.depth) score1 else score2
    } else {
        if (score1.value > score2.value) score1 else score2
    }
}

private fun min(score1: Score, score2: Score): Score {
    return if (score1.value == score2.value) {
        if (score1.depth > score2.depth) score1 else score2
    } else {
        if (score1.value < score2.value) score1 else score2
    }
}
