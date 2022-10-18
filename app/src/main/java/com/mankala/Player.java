package com.mankala;

public enum Player {
    ONE(1), TWO(2);
    final int row;

    Player(int row) {
        this.row = row;
    }

    public Player getOtherPlayer() {
        if (this == Player.ONE)
            return Player.TWO;
        return Player.ONE;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
