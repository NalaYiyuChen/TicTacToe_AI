import java.util.ArrayList;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class TicTacToe {
    private char[][] TicTacToeBoard = new char[3][3];
    private int currPlayer = 0;
    private char winningPlayer;

    /** Init instance of TicTacToe. */
    public TicTacToe() {
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j++) {
                TicTacToeBoard[i][j] = ' ';
            }
        }
    }

    /** Init instance of TicTacToe with a certain board position. */
    public TicTacToe(char[][] board) {
        TicTacToeBoard = board;
    }

    /** Returns the character representing the current player. Use to place marker. */
    public char getCurrPlayer() {
        if (currPlayer == 0) {
            return 'X';
        } else {
            return 'Y';
        }
    }

    /** Returns the winning player. Returns null if tie or unfinished game. */
    public char getWinningPlayer() {
        return winningPlayer;
    }

    /** Changes player between turns. */
    public void takeTurns() {
        currPlayer = (currPlayer + 1) % 2;
    }

    /** Prints out the board during gameplay. */
    public void displayBoard() {
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 13; k++) {
                System.out.print('-');
            }
            System.out.println();
            for (int j = 0; j < 3; j++) {
                System.out.print('|');
                System.out.print(' ');
                System.out.print(TicTacToeBoard[i][j]);
                System.out.print(' ');
            }
            System.out.println('|');
        }
        for (int k = 0; k < 13; k++) {
            System.out.print('-');
        }
        System.out.println();
    }

    /** Returns whether there are empty spaces left on the board. */
    public boolean moveAvailable() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (TicTacToeBoard[i][j] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }

    /** Returns whether someone has won the game. */
    public boolean winner() {
        return inARow() || inACol() || inADiag();
    }

    /** Returns whether there are three markers by the same player in a row. */
    public boolean inARow() {
        for (int i = 0; i < 3; i++) {
            char c1 = TicTacToeBoard[i][0];
            char c2 = TicTacToeBoard[i][1];
            char c3 = TicTacToeBoard[i][2];
            if (checkBoxes(c1, c2, c3)) {
                return true;
            }
        }
        return false;
    }

    /** Returns whether there are three markers by the same player in a column. */
    public boolean inACol() {
        for (int i = 0; i < 3; i++) {
            char c1 = TicTacToeBoard[0][i];
            char c2 = TicTacToeBoard[1][i];
            char c3 = TicTacToeBoard[2][i];
            if (checkBoxes(c1, c2, c3)) {
                return true;
            }
        }
        return false;
    }

    /** Returns whether there are three markers by the same player in a diagonal. */
    public boolean inADiag() {
        char upperLeft = TicTacToeBoard[0][0];
        char lowerLeft = TicTacToeBoard[2][0];
        char mid = TicTacToeBoard[1][1];
        char upperRight = TicTacToeBoard[0][2];
        char lowerRight = TicTacToeBoard[2][2];
        return (checkBoxes(upperLeft, mid, lowerRight)) || (checkBoxes(lowerLeft, mid, upperRight));
    }

    /** Returns whether the three boxes given contain the same marker. */
    public boolean checkBoxes(char c1, char c2, char c3) {
        if ((c1 != ' ') && (c1 == c2) && (c2 == c3)) {
            winningPlayer = c1;
            return true;
        }
        return false;
    }

    /** Attempts to place a marker of the current player on the given row and column.
     * Returns true if marker is successfully placed. */
    public boolean move(int row, int col) {
        if ((row < 0) || (row > 2) || (col < 0) || (col > 2)) {
            return false;
        }
        if (TicTacToeBoard[row][col] != ' ') {
            return false;
        }
        TicTacToeBoard[row][col] = getCurrPlayer();
        return true;
    }

    /** Returns an Arraylist of arrays of available indices to move to. */
    public ArrayList<int[]> availableMoves() {
        ArrayList moves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <3; j++) {
                if (TicTacToeBoard[i][j] == ' ') {
                    int[] availableMove = new int[2];
                    availableMove[0] = i;
                    availableMove[1] = j;
                    moves.add(availableMove);
                }
            }
        }
        return moves;
    }

    /** returns int[] containing the row and column of the best move. */
    public int[] bestMove() {
        int[] returnMove = null;
        Node returnNode = new Node(null);
        returnNode.isMaximizer = false;
        for (int[] move : availableMoves()) {
            Node moveNode = new Node(this);
            int row = move[0];
            int col = move[1];
            moveNode.isMaximizer = false;
            moveNode.currBoard.move(row, col);
            moveNode.currBoard.takeTurns();
            if (returnMove == null) {
                returnMove = move;
                returnNode = moveNode;
                continue;
            }
            if (minimax(moveNode, 0, currPlayer) > minimax(returnNode, 0, currPlayer)) {
                returnMove = move;
                returnNode = moveNode;
            }
        }
        return returnMove;
    }

    /** Returns how favorable the board is to the player passed in. */
    public int boardValue(int playerToMove) {
        if (winner()) {
            if (playerToMove == winningPlayer) {
                return 50;
            } else {
                return -50;
            }
        } else {
            return 0;
        }
    }

    /** Recursively find the best value achievable for the player to move,
     * given a node containing the current position of the board.
     * Returns the most favorable move. */
    public int minimax(Node node, int depth, int playerToMove) {
        if (!node.currBoard.moveAvailable()) {
            return node.currBoard.boardValue(playerToMove);
        }
        if (node.isMaximizer) {
            int bestVal = Integer.MIN_VALUE;
            int val;
            for (int[] possibleMove : node.currBoard.availableMoves()) {
                Node possibleNode = new Node(node.currBoard);
                int row = possibleMove[0];
                int col = possibleMove[1];
                possibleNode.isMaximizer = false;
                possibleNode.currBoard.move(row, col);
                possibleNode.currBoard.takeTurns();
                val = minimax(possibleNode, depth + 1, playerToMove);
                bestVal = max(bestVal, val);
            }
            return bestVal;
        } else {
            int bestVal = Integer.MAX_VALUE;
            int val;
            for (int[] possibleMove : node.currBoard.availableMoves()) {
                Node possibleNode = new Node(node.currBoard);
                int row = possibleMove[0];
                int col = possibleMove[1];
                possibleNode.isMaximizer = true;
                possibleNode.currBoard.move(row, col);
                possibleNode.currBoard.takeTurns();
                val = minimax(possibleNode, depth + 1, playerToMove);
                bestVal = min(bestVal, val);
            }
            return bestVal;
        }
    }

    private class Node {
        TicTacToe currBoard;
//        Node[] children;
//        ArrayList potentialMoves = currBoard.availableMoves();
        int value;
        boolean isMaximizer;

        public Node(TicTacToe board) {
            currBoard = new TicTacToe();
            if (board != null) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        currBoard.TicTacToeBoard[i][j] = board.TicTacToeBoard[i][j];
                    }
                }
            }
        }
    }

}