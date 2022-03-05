package com.mankala;

public class Game {
    private final int stones;
    private int nextPlayer;
    private final Board board;

    public Game(int stones) {
        this.stones = stones;
        this.nextPlayer = 1;
        this.board = new Board(stones);
    }

    public boolean isOver() {
        return (this.stones * 12 == this.board.getStonesForRightBase() + this.board.getStonesForLeftBase() || this.board.firstRowIsEmpty() || this.board.secondRowIsEmpty());
    }

    private void changeNextPlayer() {
        this.nextPlayer = 3 - this.nextPlayer;
    }

    public int getTurn() {
        return this.nextPlayer;
    }

    public int getPlayer1Score() {
        return this.board.getStonesForRightBase();
    }

    public int getPlayer2Score() {
        return this.board.getStonesForLeftBase();
    }

    public void move(int cell) {
        if (!(1 <= cell && cell <= 6))
            throw new IllegalArgumentException(String.format("Cell must be a number between 1 and 6 (got %d!)", cell));
        boolean hasBonus = this.board.moveForPlayer(this.getTurn(), cell);
        if (!hasBonus) this.changeNextPlayer();
    }

    public int winner() {
        if (!this.isOver()) return -1;
        int player1Score = this.board.getStonesForRightBase();
        int player2Score = this.board.getStonesForLeftBase();
        if (player1Score > player2Score) return 1;
        else if (player2Score > player1Score) return 2;
        return 0;
    }

    public void display(boolean clear) {
        if (clear) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        System.out.print("   ");
        for (int cell : this.board.getFirstRow())
            System.out.print(" " + (cell < 10 ? " " + cell : cell) + " ");
        System.out.println();
        System.out.print(this.board.getStonesForLeftBase() < 10 ? " " : "");
        System.out.println(this.board.getStonesForLeftBase() + " ".repeat(27) + this.board.getStonesForRightBase());
        System.out.print("   ");
        for (int cell : this.board.getSecondRow())
            System.out.print(" " + (cell < 10 ? " " + cell : cell) + " ");
        System.out.println();
    }
}