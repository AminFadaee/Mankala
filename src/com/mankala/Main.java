package com.mankala;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(4);
        Scanner input = new Scanner(System.in);
        game.display(true);
        while (!game.isOver()) {
            try {
                System.out.printf("Player %d's Turn: ", game.getTurn());
                int cell = input.nextInt();
                game.move(cell);
                game.display(true);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
        int winner = game.winner();
        if (winner == 0)
            System.out.println("Game is a Draw!");
        else
            System.out.printf("Player %d Won!\n", winner);
    }
}
