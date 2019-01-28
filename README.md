# Minesweeper

#### Replicated version of classic Minesweeper game written in Java

<p align="center"><img src="https://imgur.com/xpzB5tx.png" width=300></p>

## Language

- Java

## Features

- 10x10 target grid with 10 mines (base version -- to be updated in the future)
- Supports left-clicking with mouse to reveal a mine or safe spaces with color-coded numbers indicating total number of surrounding mines
- Supports right-clicking with mouse to flag/unflag potential mines with left-clicking disabled for flagged targets
- Supports combination left- and right-clicking with mouse on known safe spaces with correct number of surrounding targets flagged to achieve "zone clicking" -- the automatic reveal of contents of all surrounding, unflagged targets
- "Zero-mine clicking" -- the automatic reveal of contents of all surrounding, unflagged targets for any clicked safe space with no surrounding mines
- Guaranteed safe space with no surrounding mines for first target clicked
- Counter indicating unused flags
- Game timer

## Installation / To Play

#### *Option 1*

Run **Minesweeper.jar** file included with the latest release

#### *Option 2*

Fork/clone or download repository, navigate to [build/classes](build/classes), and execute:

```
$ java minesweeper.Minesweeper
```

## Usage / Gameplay Instructions

Refer to documentation for detailed gameplay instructions

## Potential Future Work

- Menu with options to choose game board size and number of mines
- Options for magnification
- Saved best times
- Graphical updates

## Version History

#### v1.0.0 -- January 27, 2019

- Initial release
- 10x10 game board with 10 mines
- All primary functionality
