
public class Game {
	//plays game!

	public static GameWindow MainWindow;
	public static Board gameBoard;

	public static void main(String[] args) {
		showTitleScreen(); // DISPLAYS SIMPLE GAME TITLE SCREEN WITH A START GAME BUTTON
	}

    // NEEDS A COMPLETE GUI BASED REWRITE //Done
	static void showTitleScreen() {
		MainWindow = new GameWindow();
	}

	private static Board createBoard() {
		// Creates pieces for P1, P2. uses aggregation (gives objects in parameter and not directly creating inside non-parameterized constructor. not composition)
		Player player1 = new Player(1);
		Player player2 = new Player(2, player1.mark);

		return new Board(player1, player2);
	}

	public static void startGameLoop() {

		gameBoard = createBoard(); // creates the game board.
		MainWindow.p1Label.setText(gameBoard.p1.name +" " + gameBoard.p1.mark);
		MainWindow.p2Label.setText(gameBoard.p2.name +" " + gameBoard.p2.mark);

		gameBoard.displayBoard();

    }

	// NEEDS A COMPLETE GUI BASED REWRITE // DONE
	static public void displayWinner(Player player) {
		MainWindow.winnerLabel.setText((player.name + " " + player.mark + " has won the game!!").toUpperCase());
		MainWindow.switchScreen(GameWindow.WIN_SCREEN_CONST);
	}
	
	
}
