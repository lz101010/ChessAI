# Chess AI :crown:

A simple chess engine with the main purpose of beating my dad in chess. I probably could have used the time I spent on writing this to learn how to play well enough myself - but I'm a fan of automation and wanted to build something new.

# State of `master` branch
[![Build Status](https://github.com/lz101010/ChessAI/actions/workflows/gradle.yml/badge.svg?branch=master)](https://github.com/lz101010/ChessAI/actions?query=branch%3Amaster)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=lz101010_ChessAI&metric=coverage)](https://sonarcloud.io/dashboard?id=lz101010_ChessAI)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# Scope
Some planned features are:
- input: long algebraic notation, output: next move (semi-deterministic)
- opening book (borrowing from publicly available resources)
- endgame tablebases (borrowing from public APIs such as lichess)
- search depth of 5-6 half moves
- determine the engine's ELO (e.g. by repeatedly playing against lichess)

In particular, certain features one might expect aren't directly supported through the API:
- difficulty settings
- evaluation bar
- hints
- premoves
- UCI (Universal Chess Protocol)
