package tests;


import com.mankala.Game;
import com.mankala.Player;
import org.junit.Assert;
import org.junit.Test;

public class TestGame {
    @Test
    public void testTurnReturns1InitiallyForPlayer1() {
        Game game = new Game(4);
        Assert.assertEquals(Player.ONE, game.getTurn());
    }

    @Test
    public void testMoveChangesTurnCorrectlyForSimpleInitialMove() {
        for (int cell = 2; cell < 6 + 1; cell++) {
            Game game = new Game(6);
            game.move(cell);
            Assert.assertEquals(Player.TWO, game.getTurn());
        }
    }

    @Test
    public void testMoveChangesTurnCorrectlyForInitialMoveWithBonus() {
        Game game = new Game(6);
        game.move(1);
        Assert.assertEquals(Player.ONE, game.getTurn());
    }

    @Test
    public void testMoveChangesScoreCorrectlyForInitialMove() {
        Game game = new Game(6);
        game.move(2);
        Assert.assertEquals(1, game.getPlayer1Score());
        Assert.assertEquals(0, game.getPlayer2Score());
    }

    @Test
    public void testSequenceOfMovesCorrectlyChangesTheScores() {
        Game game = new Game(6);
        game.move(1);
        game.move(2);
        game.move(5);
        Assert.assertEquals(2, game.getPlayer1Score());
        Assert.assertEquals(1, game.getPlayer2Score());
    }

    @Test
    public void testFinishingInEmptyOppositeCellDoesNotSteal() {
        Game game = new Game(6);
        game.move(1);
        game.move(2);
        game.move(5);
        game.move(3);
        Assert.assertEquals(3, game.getPlayer1Score());
        Assert.assertEquals(1, game.getPlayer2Score());
    }

    @Test
    public void testFinishingInEmptySelfCellCorrectlySteals() {
        Game game = new Game(6);
        game.move(1);
        game.move(2);
        game.move(5);
        game.move(3);
        game.move(1);
        game.move(1);
        Assert.assertEquals(12, game.getPlayer1Score());
        Assert.assertEquals(2, game.getPlayer2Score());
        Assert.assertEquals(Player.TWO, game.getTurn());
    }

    @Test
    public void testCompleteGameWith4Stones() {
        Game game = new Game(4);
        int[] moveSequence = {3, 6, 2, 1, 5, 2, 6, 2, 5, 2, 6, 6, 5, 6, 3, 6, 2, 3, 5, 6, 3, 6, 5, 2};
        for (int move : moveSequence)
            game.move(move);
        Assert.assertEquals(35, game.getPlayer1Score());
        Assert.assertEquals(13, game.getPlayer2Score());
        Assert.assertFalse(game.isNotOver());
    }

    @Test
    public void testInvalidInputThrowsException() {
        Game game = new Game(5);
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            game.move(0);
        });
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            game.move(-1);
        });
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            game.move(7);
        });
    }

    @Test
    public void testInvalidMovesThrowsException() {
        Game game = new Game(5);
        game.move(2);
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            game.move(2);
        });
    }
}
