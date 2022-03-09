package com.mankala;

public class Game {
    private final int stones;
    private Player nextPlayer;
    private final Board board;

    public Game(int stones) {
        this.stones = stones;
        this.nextPlayer = Player.ONE;
        this.board = new Board(stones);
    }

    public boolean isNotOver() {
        return (this.board.firstRowIsNotEmpty() || this.board.secondRowIsNotEmpty());
    }

    private void changeNextPlayer() {
        this.nextPlayer = this.nextPlayer.getOtherPlayer();
    }

    public Player getTurn() {
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
        boolean hasBonus = this.board.moveForPlayer(this.nextPlayer.row, cell);
        if (!hasBonus) this.changeNextPlayer();
    }

    public Player getLead() {
        int player1Score = this.board.getStonesForRightBase();
        int player2Score = this.board.getStonesForLeftBase();
        if (player1Score > player2Score) return Player.ONE;
        else if (player2Score > player1Score) return Player.TWO;
        return null;
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