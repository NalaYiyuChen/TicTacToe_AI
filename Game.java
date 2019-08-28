import java.util.Scanner;

public class Game {
    TicTacToe newBoard = new TicTacToe();

    /** Init an instance of a Game. Players take turn until the end of the game. */
    public Game() {
        Scanner scan = new Scanner(System.in);
        System.out.println("New Game!");
        do {
            int row;
            int col;
            do {
                newBoard.displayBoard();
                System.out.println("[" + (newBoard.bestMove()[0] + 1) + ", " + (newBoard.bestMove()[1] + 1) + "]");
                System.out.println(newBoard.getCurrPlayer() + " to move. Please enter a row.");
                row = scan.nextInt() - 1;
                System.out.println("Please enter a column.");
                col = scan.nextInt() - 1;
            } while (!newBoard.move(row, col));
            newBoard.takeTurns();
        } while (newBoard.moveAvailable() && !newBoard.winner());
        if (!newBoard.moveAvailable() && !newBoard.winner()) {
            System.out.println("This game has ended in a tie!");
        } else {
            newBoard.displayBoard();
            newBoard.takeTurns();
            System.out.println("Congratulations! Player " + newBoard.getCurrPlayer() + " has won!");
        }
    }

    public static void main(String[] args) {
        if (args[0].equals("computer")) {
            new Game();
        }
    }
}
