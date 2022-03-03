package com.mankala;

import java.util.Arrays;

public class Board {
    private final int initialStones;
    private final int[] grid;

    public Board(int initialStones) {
        this.initialStones = initialStones;
        this.grid = this.initializeGrid();
    }

    private int[] initializeGrid() {
        int[] grid = new int[14];
        for (int i = 0; i < 14; i++)
            if (i != 0 && i != 7)
                grid[i] = this.initialStones;
        return grid;
    }

    private int getNextIndex(int row, int index) {
        int skipOtherPlayersBase = (index == 13 && row == 1) || (index == 6 && row == 2) ? 1 : 0;
        return (index + 1 + skipOtherPlayersBase) % 14;
    }

    private void changeBoard(int row, int indexToEmpty, int[] indicesToIncrease) {
        this.grid[indexToEmpty] = 0;
        for (int index : indicesToIncrease)
            this.grid[index]++;
        if (this.moveHasSteal(indicesToIncrease[indicesToIncrease.length - 1], row)) {
            int oppositeIndex = this.getOppositeIndex(indicesToIncrease[indicesToIncrease.length - 1]);
            this.grid[this.baseIndex(row)] += (this.grid[oppositeIndex] + 1);
            this.grid[oppositeIndex] = 0;
            this.grid[indicesToIncrease[indicesToIncrease.length - 1]] = 0;
        }
        if (this.boardIsInFinalState()) {
            this.grid[7] += this.getSumOfFirstRow();
            this.grid[0] += this.getSumOfSecondRow();
            this.setRowsToZero();
        }

    }


    private void setRowsToZero() {
        for (int i = 1; i < 14 && i != 7; i++)
            this.grid[i] = 0;
    }

    private int getSumOfFirstRow() {
        return Arrays.stream(this.grid, 1, 7).sum();
    }

    private int getSumOfSecondRow() {
        return Arrays.stream(this.grid, 8, 14).sum();
    }

    private boolean boardIsInFinalState() {
        return this.getSumOfFirstRow() == 0 && this.getSumOfSecondRow() == 0;
    }

    private int baseIndex(int player) {
        if (player != 1 && player != 2)
            throw new IllegalArgumentException(String.format("Invalid Player: %d!", player));
        return player == 1 ? 7 : 0;
    }

    private boolean moveHasBonus(int lastIndex, int currentPlayer) {
        return (lastIndex == 0 && currentPlayer == 2) || (lastIndex == 7 && currentPlayer == 1);
    }

    private int getOppositeIndex(int index) {
        return 14 - index;
    }

    private boolean moveHasSteal(int lastIndex, int currentPlayer) {
        return this.grid[lastIndex] == 1 && (
                (currentPlayer == 1 && 1 <= lastIndex && lastIndex <= 6) ||
                        (currentPlayer == 2 && 8 <= lastIndex && lastIndex <= 13)
        ) && this.grid[this.getOppositeIndex(lastIndex)] > 0;
    }


    public int[] getSecondRow() {
        return Arrays.stream(this.grid, 1, 7).toArray();
    }

    public int[] getFirstRow() {
        int[] firstRow = new int[6];
        for (int i = 8, j = 5; i < 14; i++, j--)
            firstRow[j] = this.grid[i];
        return firstRow;
    }

    public boolean firstRowIsEmpty() {
        return Arrays.stream(this.grid, 1, 7).sum() == 0;
    }

    public boolean secondRowIsEmpty() {
        return Arrays.stream(this.grid, 8, 14).sum() == 0;
    }

    public int getStonesForFirstBase() {
        return this.grid[7];
    }

    public int getStonesForSecondBase() {
        return this.grid[0];
    }

    public boolean moveForPlayer(int row, int cell) {
        if (!(1 <= cell && cell <= 6))
            throw new IllegalArgumentException(String.format("Cell must be a number between 1 and 6 (got %d)!", cell));
        if (row != 1 && row != 2)
            throw new IllegalArgumentException(String.format("Invalid Row: %d", row));
        int initialIndex = (row - 1) * 7 + cell;
        int nextIndex = initialIndex;
        if (this.grid[initialIndex] == 0)
            throw new IllegalArgumentException("Cell is empty!");
        int[] indicesToIncrease = new int[this.grid[initialIndex]];
        for (int i = 0; i < this.grid[initialIndex]; i++) {
            nextIndex = this.getNextIndex(row, nextIndex);
            indicesToIncrease[i] = nextIndex;
        }
        this.changeBoard(row, initialIndex, indicesToIncrease);
        return this.moveHasBonus(indicesToIncrease[indicesToIncrease.length - 1], row);
    }

}
