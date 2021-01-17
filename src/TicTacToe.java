import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @author Srikar
 *
 * The main class for TicTacToe.
 * Handles running game, maintaining the current state of the game, and determining winners.
 */
public class TicTacToe
{
    private final char[] board;
    private char winner;

    public TicTacToe()
    {
        winner = ' ';
        board = new char[9];
        Arrays.fill(board, ' ');
    }

    /**
     * Makes a valid move on the TicTacToe board.
     *
     * @param letter The letter being placed on the board
     * @param square The square which the move is being made on
     */
    public void makeMove(char letter, int square)
    {
        if (board[square] == ' ')
        {
            board[square] = letter;
            if (getEmptySquares(board).length <= 4 && checkWinner(board, square))
                winner = letter;
        }
    }

    /**
     * Checks if there is a winner for the current game state.
     *
     * @param board A char array representing the game state
     * @param square The square which the last move was played on
     * @return true if there's a winner, false otherwise
     */
    public boolean checkWinner(char[] board, int square)
    {
        // check row
        int rowStart = (square / 3) * 3;
        if (board[rowStart] == board[rowStart + 1] && board[rowStart + 1] == board[rowStart + 2])
            return true;

        // check column
        int colStart = square % 3;
        if (board[colStart] == board[colStart + 3] && board[colStart + 3] == board[colStart + 6])
            return true;

        // check diagonals
        if (square % 2 == 0)
        {
            if ("048".contains(square + ""))
                if (board[0] == board[4] && board[4] == board[8])
                    return true;

            if ("246".contains(square + ""))
                return board[2] == board[4] && board[4] == board[6];
        }

        return false;
    }

    /**
     * Gets the current game state.
     *
     * @return A char array representing the current game state.
     */
    public char[] getBoard()
    {
        return board.clone();
    }

    /**
     * Checks if the square is empty or not.
     *
     * @param square The square on the board
     * @return true if square is empty, false otherwise
     */
    public boolean isSquareEmpty(int square)
    {
        return board[square] == ' ';
    }

    /**
     * Checks whether the board still has empty squares.
     *
     * @return true if empty squares exist, false otherwise.
     */
    public boolean hasEmptySquares()
    {
        return new String(board).chars().filter(i -> i == ' ').count() != 0;
    }

    /**
     * Gets an array of all the empty square positions.
     *
     * @param board A char array representing the current game state
     * @return an int array with all the empty square positions for the current game state
     */
    public int[] getEmptySquares(char[] board)
    {
        return IntStream.range(0, board.length).filter(i -> board[i] == ' ').toArray();
    }

    /**
     * Prints the current game state.
     */
    public void printBoard()
    {
        int i = 0;
        do
        {
            System.out.print(" " + board[i * 3] + " | " + board[i * 3 + 1] + " | " + board[i * 3 + 2]);
            if (i < 2)
                System.out.println("\n---+---+---");
        } while (++i < 3);
        System.out.println("\n");
    }

    /**
     * Prints a TicTacToe board with numbers representing square positions.
     */
    public void printBoardNums()
    {
        int i = 0;
        do
        {
            System.out.print(" " + (i * 3) + " | " + (i * 3 + 1) + " | " + (i * 3 + 2));
            if (i < 2)
                System.out.println("\n---+---+---");
        } while (++i < 3);
        System.out.println("\n");
    }

    /**
     * Creates a pause in the program
     *
     * @param ms The number of milliseconds needed to sleep
     */
    public void sleep(int ms)
    {
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        while (end - start < ms)
            end = System.currentTimeMillis();
    }

    /**
     * Starts playing the game with two players.
     *
     * @param X The player representing 'X'
     * @param O The player representing 'O'
     */
    public void playGame(Player X, Player O)
    {
        System.out.println("\nThere are the square positions!");
        printBoardNums();
        System.out.println();
        printBoard();

        char move = 'X';
        while (hasEmptySquares())
        {
            System.out.print("List of Empty Squares: ");
            Arrays.stream(getEmptySquares(board)).mapToObj(i -> i + " ").forEach(System.out::print);
            System.out.println();

            int square = move == 'X' ? X.getMove(this) : O.getMove(this);
            makeMove(move, square);
            printBoard();

            if (winner != ' ')
            {
                System.out.println("Player " + move + " WON!");
                return;
            }

            move = move == 'X' ? 'O' : 'X';
        }

        System.out.println("Tie Game!!");
    }

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        do
        {
            TicTacToe game = new TicTacToe();

            System.out.print("Is Player X Human (H), Computer (C), SmartComputer (S)? ");
            char response = input.next().charAt(0);
            Player X = getPlayer(response, 'X');

            System.out.print("Is Player O Human (H), Computer (C), SmartComputer (S)? ");
            response = input.next().charAt(0);
            Player O = getPlayer(response, 'O');

            game.playGame(X, O);

            System.out.print("Press (Y) to play again or (Q) to quit! ");
        } while (input.next().charAt(0) != 'Q');
    }

    /**
     * Creates a player object based on what user wanted.
     *
     * @param player The input char given by user
     * @param letter The letter to assign to the player object
     * @return a Player object based on the user input
     */
    public static Player getPlayer(char player, char letter)
    {
        if (player == 'H')
            return new Human(letter);
        else if (player == 'C')
            return new NoviceComputer(letter);

        return new ExpertComputer(letter);
    }
}
