# e4 :crown:

A simple chess engine with the main purpose of beating my dad in chess. I probably could have used the time I spent on writing this to learn how to play well enough myself - but I'm a fan of automation and wanted to build something new.

This project is mostly for fun and doesn't take itself too seriously. For instance, the name e4 is due to the fact that all games start with this move - sort of as a gimmick, mainly to justify a fun and unique name.

# State of `main` branch
[![Build Status](https://github.com/lz101010/e4/actions/workflows/gradle.yml/badge.svg?branch=master)](https://github.com/lz101010/e4/actions?query=branch%3Amaster)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=lz101010_e4&metric=coverage)](https://sonarcloud.io/dashboard?id=lz101010_e4)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# Scope
Some planned features are:
- all moves start with [e4](https://en.wikibooks.org/wiki/Chess_Opening_Theory/1._e4)
- input: board formatted in [FEN](https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation), output: next move formatted in [SAN](https://en.wikipedia.org/wiki/Algebraic_notation_(chess)) (semi-deterministic)
- [opening book](https://www.chessprogramming.org/Opening_Book) (borrowing from publicly available resources)
- [endgame tablebases](https://www.chessprogramming.org/Endgame_Tablebases) (borrowing from public APIs such as [lichess](https://lichess.org/blog/W3WeMyQAACQAdfAL/7-piece-syzygy-tablebases-are-complete))
- [search depth](https://www.chessprogramming.org/Search) of 5-6 half moves
- determine the engine's [ELO](https://en.wikipedia.org/wiki/Elo_rating_system) (e.g., by repeatedly playing against lichess)

In particular, certain features one might expect aren't directly supported through the API:
- difficulty settings
- evaluation bar
- hints
- [premoves](https://en.wikipedia.org/wiki/Premove)
- [UCI](https://en.wikipedia.org/wiki/Universal_Chess_Interface) (Universal Chess Protocol)
