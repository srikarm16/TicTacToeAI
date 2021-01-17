/**
 * @author Srikar Mangalapalli
 *
 * <p>
 * This is an interface for adding different kinds of players for TicTacToe
 */
public interface Player
{
    /**
     * Gets the next move from the player.
     *
     * @param game The current state of the TicTacToe game.
     * @return an int representing which square is the next move
     */
    int getMove(TicTacToe game);
}
