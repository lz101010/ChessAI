package com.lz101010.chess.moves

import com.lz101010.chess.data.*
import com.lz101010.chess.moves.AttackHelper.attackedSquares
import com.lz101010.chess.moves.AttackHelper.isAttacked
import com.lz101010.chess.moves.AttackHelper.isAttackedOrGuarded
import com.lz101010.chess.moves.AttackHelper.countAttacks
import com.lz101010.chess.moves.MoveHelper.evaluate
import com.lz101010.chess.moves.MoveHelper.findPotentialPins


object MyMoveGenerator {
    // TODO: add a bunch of tests --> remove MoveGenerator
    // TODO: consolidate with AttackedSquaresGenerator / try removing the hack for castling / test check by pawn

    fun find(board: Board): List<Move> {
        val allMoves = mutableListOf<Move>()

        for (square in Square.values()) {
            val piece = board[square]
            if (piece != null && piece.white == board.whiteToMove) {
                allMoves.addAll(findMoves(piece, square, board))
            }
        }

        return removeSelfChecks(allMoves, board)
    }

    private fun findMoves(piece: Piece, startSquare: Square, board: Board): List<Move> {
        val allMoves = mutableListOf<Move>()

        attackedSquares(piece, startSquare, board).map { Move(startSquare, it) }.let(allMoves::addAll)

        if (piece.type == PieceType.P) {
            pawnMoves(piece.white, startSquare).filter { board[it.to] == null }.let(allMoves::addAll)
        }
        if (piece.type == PieceType.K) {
            specialKingMoves(board).let(allMoves::addAll)
        }

        return allMoves
    }

    private fun pawnMoves(whiteToMove: Boolean, square: Square): List<Move> {
        val defaultTarget = evaluate(square, 0, 1, whiteToMove)!!
        val defaultMove = Move(square, defaultTarget)

        if (whiteToMove && square.rank == Rank.RANK_2 || !whiteToMove && square.rank == Rank.RANK_7) {
            return listOf(
                defaultMove,
                Move(square, evaluate(square, 0, 2, whiteToMove)!!)
            )
        }

        if (whiteToMove && square.rank == Rank.RANK_7 || !whiteToMove && square.rank == Rank.RANK_2) {
            return listOf(
                defaultMove.copy(promotion = PieceType.Q),
                defaultMove.copy(promotion = PieceType.R),
                defaultMove.copy(promotion = PieceType.N),
                defaultMove.copy(promotion = PieceType.B)
            )
        }

        return listOf(defaultMove)
    }

    private fun specialKingMoves(board: Board): List<Move> {
        val moves = mutableListOf<Move>()
        val castlingOptions = board.castlingOptions
        if (board.whiteToMove) {
            if (castlingOptions.contains(CastlingOption.WHITE_K) && areSafe(board, Square.E1, Square.F1, Square.G1)) {
                moves.add(Move(Square.E1, Square.G1))
            }
            if (castlingOptions.contains(CastlingOption.WHITE_Q) && areSafe(board, Square.E1, Square.D1, Square.C1)) {
                moves.add(Move(Square.E1, Square.C1))
            }
        } else {
            if (castlingOptions.contains(CastlingOption.BLACK_K) && areSafe(board, Square.E8, Square.F8, Square.G8)) {
                moves.add(Move(Square.E8, Square.G8))
            }
            if (castlingOptions.contains(CastlingOption.BLACK_Q) && areSafe(board, Square.E8, Square.D8, Square.C8)) {
                moves.add(Move(Square.E8, Square.C8))
            }
        }
        return moves
    }

    private fun areSafe(board: Board, kingSquare: Square, vararg otherSquares: Square): Boolean {
        if (otherSquares.any { board[it] != null }) {
            return false
        }
        // TODO: add tests
        // minor hacks:
        // - pawn attacking F1 from G2 will be detected via pawn move to G1, but not from E2 --> manual detection
        // - castlingOptions = emptySet() avoids infinite recursion
        // - requires legal moves to include capturing king, which is effectively impossible
        if (board.whiteToMove && board[Square.E2]?.black == true) {
            return false
        }
        if (!board.whiteToMove && board[Square.E7]?.white == true) {
            return false
        }
        val squares = listOf(kingSquare).plus(otherSquares)
        val otherPlayerNextMoves = find(board.copy(whiteToMove = !board.whiteToMove, castlingOptions = emptySet()))
        return otherSquares.all { board[it] == null }
                && squares.none { square -> otherPlayerNextMoves.any { it.to == square } }
    }

    private fun removeSelfChecks(moves: List<Move>, board: Board): List<Move> {
        val kingSquare = Square.values().first { square ->
            val piece = board[square]
            piece != null && piece.type == PieceType.K && piece.white == board.whiteToMove
        }

        // get out of check
        if (isAttackedOrGuarded(kingSquare, !board.whiteToMove, board)) {
            return if (countAttacks(kingSquare, !board.whiteToMove, board) == 2) {
                handleDoubleCheck(moves, kingSquare, board)
            } else {
                handleCheck(moves, kingSquare, board)
            }
        }
        val potentialPins = findPotentialPins(kingSquare, board)

        return moves.filter { move ->
            if (move.from == kingSquare) {
                // TODO: no need to check again for castling
                !isAttackedOrGuarded(move.to, !board.whiteToMove, board)
            } else if (potentialPins.contains(move.from)) {
                MoveHelper.moveTowardsDelta(move, potentialPins[move.from]!!)
            } else {
                true
            }
        }
    }

    private fun handleCheck(moves: List<Move>, kingSquare: Square, board: Board) = moves.filter { move ->
        moveKingToSafety(move, kingSquare, board) || protectKing(move, kingSquare, board)
    }

    private fun handleDoubleCheck(moves: List<Move>, kingSquare: Square, board: Board) = moves.filter { move ->
        moveKingToSafety(move, kingSquare, board)
    }

    private fun moveKingToSafety(move: Move, kingSquare: Square, board: Board) =
        move.from == kingSquare && !isAttackedOrGuarded(move.to, !board.whiteToMove, board)

    private fun protectKing(move: Move, kingSquare: Square, board: Board) =
        !isAttacked(kingSquare, !board.whiteToMove, MoveMaker.simpleMove(board, move))
}
