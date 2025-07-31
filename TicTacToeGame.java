import java.util.Scanner;

public class TicTacToeGame 
{
    private GameBoard board;
    private Player currentPlayer;
    private GameState gameState;
    private Scanner scanner;

    private static final int BOARD_SIZE = 3;

    public TicTacToeGame() 
    {
        this.board = new GameBoard();
        this.currentPlayer = Player.X;
        this.gameState = GameState.PLAYING;
        this.scanner = new Scanner(System.in);
    }

    public void playGame() 
    {
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println("Players take turns entering row and column (1-3)");
        System.out.println();

        while (gameState == GameState.PLAYING) 
        {
            board.displayBoard();

            if (makePlayerMove()) 
            {
                gameState = checkGameState();

                if (gameState == GameState.PLAYING) 
                {
                    currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
                }
            }
        }

        displayGameResult();
        scanner.close();
    }

    private boolean makePlayerMove() 
    {
        System.out.printf("Player %s, enter your move (row column): ", currentPlayer.getSymbol());

        try 
        {
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (row < 1 || row > BOARD_SIZE || col < 1 || col > BOARD_SIZE) 
            {
                System.out.println("Invalid input! Please enter numbers between 1 and 3.");
                return false;
            }

            row--;
            col--;

            if (board.makeMove(row, col, currentPlayer)) 
            {
                return true;
            } 
            else 
            {
                System.out.println("That position is already taken! Try again.");
                return false;
            }

        } 
        catch (Exception e) 
        {
            System.out.println("Invalid input! Please enter two numbers separated by space.");
            scanner.nextLine(); // Clear invalid input
            return false;
        }
    }

    private GameState checkGameState() 
    {
        if (board.hasPlayerWon(currentPlayer)) 
        {
            return (currentPlayer == Player.X) ? GameState.X_WINS : GameState.O_WINS;
        }

        if (board.isBoardFull()) 
        {
            return GameState.DRAW;
        }

        return GameState.PLAYING;
    }

    private void displayGameResult() 
    {
        board.displayBoard();
        System.out.println();

        switch (gameState) 
        {
            case X_WINS:
                System.out.println("Hurray Player X wins! Congratulations!");
                break;
            case O_WINS:
                System.out.println("Hurray Player O wins! Congratulations!");
                break;
            case DRAW:
                System.out.println(" It's a draw! Good game!");
                break;
            default:
                System.out.println("Game ended unexpectedly.");
        }
    }

    public static void main(String[] args) 
    {
        TicTacToeGame game = new TicTacToeGame();
        game.playGame();
    }
}

enum GameState 
{
    PLAYING,
    X_WINS,
    O_WINS,
    DRAW
}

enum Player 
{
    X("X"), O("O");

    private final String symbol;

    Player(String symbol) 
    {
        this.symbol = symbol;
    }

    public String getSymbol() 
    {
        return symbol;
    }
}

class GameBoard 
{
    private Player[][] board;
    private static final int SIZE = 3;

    public GameBoard() 
    {
        board = new Player[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) 
        {
            for (int col = 0; col < SIZE; col++) 
            {
                board[row][col] = null;
            }
        }
    }

    public boolean makeMove(int row, int col, Player player) 
    {
        if (board[row][col] == null) 
        {
            board[row][col] = player;
            return true;
        }
        return false;
    }

    public boolean hasPlayerWon(Player player) 
    {
        for (int row = 0; row < SIZE; row++) 
        {
            if (board[row][0] == player && board[row][1] == player && board[row][2] == player) 
            {
                return true;
            }
        }

        for (int col = 0; col < SIZE; col++) 
        {
            if (board[0][col] == player && board[1][col] == player && board[2][col] == player) 
            {
                return true;
            }
        }

        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) 
        {
            return true;
        }

        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) 
        {
            return true;
        }

        return false;
    }

    public boolean isBoardFull() 
    {
        for (int row = 0; row < SIZE; row++) 
        {
            for (int col = 0; col < SIZE; col++) 
            {
                if (board[row][col] == null) 
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void displayBoard() 
    {
        System.out.println();
        System.out.println("Current Board:");
        System.out.println("   1   2   3");

        for (int row = 0; row < SIZE; row++) 
        {
            System.out.print((row + 1) + " ");

            for (int col = 0; col < SIZE; col++) 
            {
                String cellContent = (board[row][col] != null) ? board[row][col].getSymbol() : " ";
                System.out.print(" " + cellContent + " ");

                if (col < SIZE - 1) 
                {
                    System.out.print("|");
                }
            }

            System.out.println();

            if (row < SIZE - 1) 
            {
                System.out.println("  -----------");
            }
        }

        System.out.println();
    }
}
