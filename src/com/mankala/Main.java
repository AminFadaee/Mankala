package com.mankala;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(4);
        Scanner input = new Scanner(System.in);
        game.display(true);
        while (game.isNotOver()) {
            try {
                System.out.printf("Player %s's Turn: ", game.getTurn());
                int cell = input.nextInt();
                game.move(cell);
                game.display(true);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
        Player winner = game.getLead();
        if (winner == null)
            System.out.println("Game is a Draw!");
        else
            System.out.printf("Player %s Won!\n", winner);
    }
}
