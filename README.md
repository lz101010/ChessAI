# e4 :crown:

[![Build Status](https://github.com/lz101010/e4/actions/workflows/gradle.yml/badge.svg?branch=master)](https://github.com/lz101010/e4/actions?query=branch%3Amaster)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=lz101010_e4&metric=coverage)](https://sonarcloud.io/dashboard?id=lz101010_e4)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

🪦 Abandoned after the realization that move generation is a massive bottleneck beyond reasonable repair. Board representation and choice of language are entirely inappropriate for an engine with hopes for an ELO beyond 1200.

The following branches have some unfinished work:
- [`feature/refactor-modules`](https://github.com/lz101010/e4/tree/feature/refactor-modules) restructures the project so that [board representation](https://www.chessprogramming.org/Board_Representation) can be refactored to use [bitboards](https://www.chessprogramming.org/Bitboards) internally rather than a [2D-array](https://www.chessprogramming.org/8x8_Board).
- [`feature/move-evaluation`](https://github.com/lz101010/e4/tree/feature/move-evaluation) adds a [minimax algorithm with alpha-beta pruning](https://www.chessprogramming.org/Alpha-Beta) and [evaluation hash tables](https://www.chessprogramming.org/Evaluation_Hash_Table). This gets caught up on several scenarios
  - shortest mate (for winning color) and longest mate (for losing color) are either incorrect or perform poorly (or both)
  - mid-game moves take ~2 minutes to evaluate at search depth 3-4

Ideas for starting over (without machine learning):
- language choice: Rust
- Board representation: bitboards
- better move ordering in terms of performance and heuristic: don't just consider checks, but also captures
- call it d4

---

# Context

A simple chess engine with the main purpose of beating my dad in chess. I probably could have used the time I spent on writing this to learn how to play well enough myself - but I'm a fan of automation and wanted to build something new.

This project is mostly for fun and doesn't take itself too seriously. For instance, the name e4 is due to the fact that all games start with this move - sort of as a gimmick, mainly to justify a fun and unique name.

# Scope
Some planned features are:
- all games start with e4
- input: board in FEN format, output: next move in long algebraic notation (semi-deterministic)
- opening book (borrowing from publicly available resources)
- endgame tablebases (borrowing from public APIs such as lichess)
- search depth of 5-6 half moves
- determine the engine's ELO (e.g., by repeatedly playing against lichess)

In particular, certain features one might expect aren't directly supported through the API:
- difficulty settings
- evaluation bar
- hints
- premoves
- UCI (Universal Chess Protocol)
