package tests;


import com.mankala.Board;
import com.mankala.Game;
import org.junit.Assert;
import org.junit.Test;

public class TestGame {
    @Test
    public void testTurnReturns1InitiallyForPlayer1() {
        Game game = new Game(4);
        Assert.assertEquals(1, game.getTurn());
    }

    @Test
    public void testMoveChangesTurnCorrectlyForSimpleInitialMove() {
        for (int cell = 2; cell < 6 + 1; cell++) {
            Game game = new Game(6);
            game.move(cell = cell);
            Assert.assertEquals(2, game.getTurn());
        }
    }

    @Test
    public void testMoveChangesTurnCorrectlyForInitialMoveWithBonus() {
        Game game = new Game(6);
        game.move(1);
        Assert.assertEquals(1, game.getTurn());
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
        Assert.assertEquals(2, game.getTurn());
    }
}
