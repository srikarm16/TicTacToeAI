import java.util.Random;

/**
 * @author Srikar
 *
 * <p>
 * This class is for a computer playing TicTacToe.
 * Plays moves by choosing a random square on the board.
 */
public class NoviceComputer implements Player
{
    private final char letter;

    public NoviceComputer(char letter)
    {
        this.letter = letter;
    }

    @Override
    public int getMove(TicTacToe game)
    {
        System.out.println(letter + "'s Move Now!");
        game.sleep(1250);

        Random rand = new Random();
        int[] moves = game.getEmptySquares(game.getBoard());
        int square = rand.nextInt(moves.length);

        while (!game.isSquareEmpty(moves[square]))
            square = rand.nextInt(moves.length);

        System.out.println("Computer " + letter + " made move to " + moves[square]);
        return moves[square];
    }
}
