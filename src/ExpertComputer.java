/**
 * @author Srikar
 *
 * <p>
 * This class is for a computer playing TicTacToe.
 * Chooses moves by simulating various moves and picking the best one according to the minimax algorithm.
 */
public class ExpertComputer implements Player
{
    private final char letter;
    private static final int INF = Integer.MAX_VALUE;

    public ExpertComputer(char letter)
    {
        this.letter = letter;
    }

    @Override
    public int getMove(TicTacToe game)
    {
        System.out.println(letter + "'s Move Now!");
        game.sleep(1250);

        int[] calls = {0};
        State optimal = minimax(game, new State(-1, 0), game.getBoard(), -INF, INF, this.letter, calls);
        System.out.println("Num Calls: " + calls[0]);
        return optimal.square;
    }

    /**
     * The minimax algorithm that simulates all possible game states from the current game state. Assigns a value to
     * each game state in order to choose the most optimal move for the current player. Uses alpha-beta pruning in
     * order to optimize the algorithm and ignore unnecessary game states.
     *
     * @param game The current state of the TicTacToe game
     * @param state A simulated state of the game
     * @param board A char array representing a state of the game
     * @param alpha An int to optimize states for maximizing player
     * @param beta An int to optimize state for minimizing player
     * @param player The player next to move
     * @param calls A mutable int for debugging purposes
     * @return The best state containing the next most optimal move for the current player.
     */
    public State minimax(TicTacToe game, State state, char[] board, int alpha, int beta, char player, int[] calls)
    {
        calls[0]++;
        char max = this.letter;
        char otherPlayer = player == 'X' ? 'O' : 'X';

        // if simulated game state has winner, assign a score to state and return
        if (state.square != -1 && game.getEmptySquares(board).length <= 4 && game.checkWinner(board, state.square))
        {
            int score = game.getEmptySquares(board).length + 1;
            return new State(state.square, otherPlayer == max ? score : -score);
        }
        // if simulated game state is a tie, assign a score of 0 and return
        else if (game.getEmptySquares(board).length == 0)
            return new State(state.square, 0);

        // go through each possible move
        State best = new State(-1, player == max ? -INF : INF);
        for (int square : game.getEmptySquares(board))
        {
            // play a move and simulate a game with a call to minimax with the other player
            board[square] = player;
            State temp = minimax(game, new State(square, 0), board, alpha, beta, otherPlayer, calls);

            // undo move
            board[square] = ' ';

            if (temp.square != -1)
            {
                // assign the optimal state to best
                if (player == max && temp.score > best.score)
                    best = new State(square, temp.score);
                else if (player != max && temp.score < best.score)
                    best = new State(square, temp.score);

                // update alpha and beta values to optimize searches
                if (player == max)
                    alpha = Math.max(alpha, temp.score);
                else
                    beta = Math.min(beta, temp.score);

                // we don't need to keep searching since the most optimal move is already explored
                if (beta <= alpha)
                    break;
            }
        }

        return best;
    }

    /**
     * Private static class representing a simulated game state. Contains the square played to get to a game state,
     * and the score assigned to that state.
     */
    private static class State
    {
        private final int square;
        private final int score;

        public State(int square, int score)
        {
            this.square = square;
            this.score = score;
        }
    }
}