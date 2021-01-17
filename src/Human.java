import java.util.Scanner;

/**
 * @author Srikar
 *
 * <p>
 * This class is for a human player playing TicTacToe.
 */
public class Human implements Player
{
    private final char letter;

    public Human(char letter)
    {
        this.letter = letter;
    }

    @Override
    public int getMove(TicTacToe game)
    {
        Scanner input = new Scanner(System.in);

        int square = 0;
        boolean validMove = false;
        while (!validMove)
        {
            System.out.print(letter + "'s Move Now! Choose your move (0-8): ");

            try
            {
                square = input.nextInt();
                if (game.isSquareEmpty(square))
                    validMove = true;
                else
                    System.out.println("That Square is not open! Try Again!");
            }
            catch (Exception e)
            {
                System.out.println("Invalid Move! Try Again");
            }
        }

        return square;
    }
}
