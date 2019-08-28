import org.junit.Test;

public class GameTest {
    @Test
    public void gTest() {
        Game g = new Game();
        g.newBoard.move(0, 0);
        g.newBoard.takeTurns();
        g.newBoard.move(0, 1);
        g.newBoard.takeTurns();
        g.newBoard.move(1, 1);
        g.newBoard.takeTurns();
        g.newBoard.displayBoard();
        System.out.println("[" + (g.newBoard.bestMove()[0] + 1) + ", " + (g.newBoard.bestMove()[1] + "]"));
    }
}
