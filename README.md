# Wave Function Collapse (WFC)

This repository provides a customizable implementation of the Wave Function Collapse algorithm using Processing.

> **Note:** This project is a work in progress.
> **Note:** This project is not full implemented. Potential adjacencies depending on their relative neighbours is still missing
## Overview

The Wave Function Collapse (WFC) algorithm generates patterns by enforcing local constraints globally. This repository allows you to customize the algorithm by defining your own tiles and their adjacencies.

## Prerequisites

Before running the WFC algorithm, you need to define the tiles and their possible adjacencies. This setup is essential for the algorithm to understand how tiles can be placed next to each other.

### 1. Define Tile Neighbouring Candidates

You need to register neighbouring candidates for the tiles. This step defines the relationships between different tiles that will be used in the WFC algorithm.
You can register them like this:
```Tiles.registerNeighbouringCandidate(representation, (xOffset, yOffset))```
Or using the provided defaults using:
```Tiles.initDefaultNeighbouringCandidates()```

### 2. Register Tile Candidates

Next, register the tile candidates. These are the tiles that the algorithm will consider when generating the grid.
```Tiles.registerTileCandidate(TileA, TileB, ...)```

### 3. Initialize Tile Adjacency

Initialize the adjacency relationships for all tiles. This step completes the setup required for the algorithm to function.
```Tiles.initAdjacencyOfAllTiles()```

Now the algorithm can be applied:

### 1. Generate a Grid
```Grid grid = new Grid(n, n, Tiles.allTiles())```

### 2. Create a WFC Object
```WaveFunctionCollapse oWFC = new WaveFunctionCollapse()```

### 3. Initialize the WFC Object on the previously defined Grid
```oWFC.init(grid)```

### 4. Collapse the algorithm onto a state
```boolean success = oWFC.collapse()```
- If 'success' == true -> the algorithm finished successfully
- If 'success' == false -> the algorithm could not collapse onto a state that suffices all adjacency rules

## Example: 
[Main file](src/Main.java)