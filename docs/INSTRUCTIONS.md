# Game Instructions

## Description

Minesweeper is a classic game in which the player uses deductive reasoning (and sometimes a bit of luck!) to identify all safe spaces within a two-dimensional grid containing randomly placed mines, any of which, if uncovered, will cause the player to lose.

## Starting a New Game

Currently, this version of Minesweeper utilizes a 10x10 grid of targets with 10 randomly placed mines. A future version of the game is expected to allow for more variations on the grid size and number of mines.

A new game can be started in the following ways:

- Immediately upon starting the application
- By choosing the replay option prompted when an existing game is won/lost
- By clicking the reset button above the game board at any time

## Gameplay

The player interacts with the game board using the mouse to achieve three main outcomes:

1. Uncover content of a target
2. Flag/unflag a potential mine
3. Reveal contents of surrounding targets via "zone-clicking"

### <ins>Uncovering Content of a Target</ins>

To reveal the content of an individual target that has not yet been revealed, use the mouse to left-click on the target. The player must press and release the left mouse button over the target for the click to be accepted. If the player presses the left mouse button over the target but releases the button outside of the target, the click will not be accepted -- an intended failsafe giving the player a chance to back out of the move.

Left-clicking on any target whose content has already been revealed or on any currently flagged target has no effect.

#### The Reveal

Clicking on a target will uncover either a mine or a safe space. Clicking on a mine will cause the player to lose while clicking on a safe space will allow the player to continue playing.

<p align="center"><img src="https://imgur.com/GMLUUD7.png" width=300><img src="https://imgur.com/gCgeUHe.png" width=300></p>

#### Safe Spaces

Safe spaces are revealed with darker shading and possibly a color-coded number. Color-coded numbers indicate the number of immediately surrounding targets (including diagonals) containing mines. These numbers can range from 1-8. Safe spaces without a number are not surrounded by any targets containing a mine.

#### "Zero-mine Clicking"

When a safe space that is not surrounded by mines is clicked, all surrounding targets that are neither flagged nor have previously been clicked are "auto-clicked", with their contents automatically (and safely) revealed to the player. In the event that any of these targets are also safe spaces without surrounding mines, the "auto-clicking" will propagate. "Zero-mine clicking" can be triggered by a regular target click or by a "zone click."

<p align="center"><img src="https://imgur.com/B9uVbaP.png" width=300><img src="https://imgur.com/wtpAJq5.png" width=300></p>

#### The First Move

The first target left-clicked constitutes the first move of the game. The first target clicked is guaranteed to be a safe space with no surrounding mines. The game timer (to the upper right of the game board) begins as soon as the first move is made and will stop only when the game is won or lost.

<p align="center"><img src="https://imgur.com/5lPiflc.png" width=300><img src="https://imgur.com/0cYoRpF.png" width=300></p>

### <ins>Flagging/Unflagging Targets</ins>

A target thought to contain a mine can be flagged as such by right-clicking the target with the mouse. The player must press and release the right mouse button over the target for the click to be accepted. The content of a flagged target cannot be revealed via regular left-clicking, "zero-mine clicking", or "zone clicking." A flagged target can be unflagged by right-clicking it again, thus re-enabling its content to be revealed.

A counter (to the upper left of the game board) tracks the current number of unused flags, i.e., the difference between the total number of mines and the number of flagged targets. The player can place more flags than number of mines, if desired. Ultimately, flags are used solely to help the player deduce which targets are safe spaces; the number of flags placed has no bearing on whether the player wins.

<p align="center"><img src="https://imgur.com/PV911H7.png" width=300><img src="https://imgur.com/lZQK5IE.png" width=300></p>

### <ins>"Zone-clicking"</ins>

"Zone-clicking" is a special gameplay mechanic to quickly reveal the contents of any unflagged and unclicked targets surrounding a known safe space given that the appropriate number of surrounding targets have been flagged. Typically, with the appropriate number of targets flagged, any remaining surrounding targets would logically be assumed by the player to be safe spaces. When a "zone-click" is performed, the aforementioned unflagged and unclicked targets are "auto-clicked". This results in a faster alternative to a player singularly clicking on each of those targets assumed to be a safe space. Note, however, that a successful "zone-click" is predicated upon the player having correctly flagged mines; if a flag is incorrectly placed, the "zone-click" will reveal a mine and the player will lose.

"Zone-clicking" is achieved by sequentially pressing the left and right mouse buttons (in any order) over a target and then releasing either button over the target. If the player releases the first button before pressing the second, fails to press the second button over the target, or releases one of the buttons outside of the target then the combination click will not be accepted -- an intended failsafe giving the player a chance to back out of the move. Again, "zone-clicking" can only be performed on a revealed safe space where the number of surrounding flagged targets exactly matches the color-coded number displayed on the space.

<p align="center"><img src="https://imgur.com/9HkexV8.png" width=300><img src="https://imgur.com/HZhfNj4.png" width=300></p>

## Winning and Losing

To win the game, the player must successfully uncover all safe spaces on the game board without clicking on a mine. The safe spaces can be uncovered through any combination of regular target clicking, "zero-mine clicking," and "zone clicking." There is no requirement to flag targets in order to win.

<p align="center"><img src="https://imgur.com/IPuhPRv.png" width=300><img src="https://imgur.com/q1luXIk.png" width=300></p>

If a player clicks on a mine prior to uncovering all safe spaces on the game board, he/she will lose. A mine may be uncovered via regular target clicking or via an unsuccessful "zone click."

<p align="center"><img src="https://imgur.com/7kVz1qi.png" width=300><img src="https://imgur.com/bAKA46j.png" width=300></p>

Upon winning or losing the game, the game timer will stop and the player will be prompted to either replay or quit. The player may also exit the prompt dialog and choose to replay or exit the application from the main window.