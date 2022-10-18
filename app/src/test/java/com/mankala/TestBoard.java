package com.mankala;


import org.junit.Assert;
import org.junit.Test;

public class TestBoard {
    @Test
    public void testMoveForPlayerWorksCorrectly() {
        Board board = new Board(4);
        board.moveForPlayer(1, 4);
        Assert.assertArrayEquals(board.getFirstRow(), new int[]{4, 4, 4, 4, 4, 5});
        Assert.assertArrayEquals(board.getSecondRow(), new int[]{4, 4, 4, 0, 5, 5});
        Assert.assertEquals(1, board.getStonesForRightBase());
        Assert.assertEquals(0, board.getStonesForLeftBase());

        board.moveForPlayer(1, 5);
        Assert.assertArrayEquals(board.getFirstRow(), new int[]{4, 4, 4, 5, 5, 6});
        Assert.assertArrayEquals(board.getSecondRow(), new int[]{4, 4, 4, 0, 0, 6});
        Assert.assertEquals(2, board.getStonesForRightBase());
        Assert.assertEquals(0, board.getStonesForLeftBase());

        board.moveForPlayer(2, 4);
        Assert.assertArrayEquals(board.getFirstRow(), new int[]{5, 5, 0, 5, 5, 6});
        Assert.assertArrayEquals(board.getSecondRow(), new int[]{5, 4, 4, 0, 0, 6});
        Assert.assertEquals(2, board.getStonesForRightBase());
        Assert.assertEquals(1, board.getStonesForLeftBase());
    }

    @Test
    public void testMoveForPlayerReturnsTrueIfMoveHasBonus() {
        Board board = new Board(4);
        Assert.assertFalse(board.moveForPlayer(1, 4));
        Assert.assertTrue(board.moveForPlayer(1, 3));
    }

    @Test
    public void testMoveForPlayerThrowsExceptionForInvalidArguments() {
        Board board = new Board(4);
        for (int row : new int[]{-1, 0, 3})
            for (int cell : new int[]{0, 7})
                Assert.assertThrows(IllegalArgumentException.class, () -> {
                    board.moveForPlayer(row, cell);
                });
    }

    @Test
    public void testMoveForPlayerThrowsExceptionForInvalidMove() {
        Board board = new Board(4);
        board.moveForPlayer(1, 4);
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            board.moveForPlayer(1, 4);
        });
    }
}
