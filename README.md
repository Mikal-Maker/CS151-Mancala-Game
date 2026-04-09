# Mancala Game

A Java based 2 player Mancala board game with a graphical user interface.

## Table of Contents

- [Overview](#overview)
- [Game Rules](#game-rules)
- [Features](#features)
- [Project Structure](#project-structure)
- [Authors](#authors)

## Overview

Mancala is an ancient board game for 2 players. This implementation provides a GUI for two human players to take turns.

## Game Rules

- The board has two rows of pits, each.
- Three pieces of stones are placed in each of the 12 holes.
- Each player has a store called Mancala on the right of the board.
- One player starts by picking up all of the stones in a pit on their side.
- The player places a stone in each pit starting with the next pit until the stones run out going counter clockwise.
- Whenever you run into your own Mancala, place a stone in it. Skip your opponent's Mancala when you run into it.
- If the last stone you drop is in your Mancala, take another turn.
- If the last stone you drop is in an empty pit on your side, take the stone and your opponents stones in the opposite pit and place them in your Mancala.
- The game ends when one side of the Mancala board is empty (6 pits). The player with stones on their side captures those pieces and places them in their Mancala.
- The player with the most stones in their Mancala wins.

## Features

- This game is for two human players using one mouse.
- The program starts with an empty baord and asks the player to choose the number of stones to be placed in each pit. (4 max, 4 would place 4 stones in each pit on the board)
- An undo function which reverts the board to the state before the last move. (3 max per turn, and allowed to undo again after making a choice)
- A player chooses a pit by clicking on it Then, the program updates the board according to the game rule explained above.

## Project Structure

```
mancala-game/
|-- src/
│   |-- MancalaTest.java       # Entry point
│   |-- MancalaModel.java      # Game state + logic
│   |-- MancalaBoard.java      # View and controller (GUI)
│   |-- BoardStyle.java        # Strategy interface
│   |-- Style1.java            # Board style 1
│   |-- Style2.java            # Board style 2
|-- .gitignore
|-- README.md
```

## Authors

Michael Hoshen, Duc Nguyen, Jonathan Gau
