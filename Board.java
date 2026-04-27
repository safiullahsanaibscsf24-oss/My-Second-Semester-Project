import javax.swing.*;


public class Board {

    int moveCount = 0; //even value means p1 turn, odd value means p2 turn.
    final int SIZE = 3;

    char[][] board = {

            {'.', '.', '.'}, //'.' represents an empty space
            {'.', '.', '.'},
            {'.', '.', '.'}
    };


    Player p1, p2;
    boolean twiceTurn = false; //flag for a game rule. when active:
    // allows the player to skip enemy turn simply by incrementing moveCount.


    Board(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    // NEEDS A COMPLETE GUI BASED REWRITE //DONE
    void displayBoard() {
        updateBoardPanelGUI();
        updateBoardButtonGUI();
        Game.MainWindow.switchScreen(GameWindow.BOARD_SCREEN_CONST);
    }

    public void updateBoardPanelGUI() {
        if (moveCount < 6) {
            if (moveCount % 2 == 0) {
                Game.MainWindow.p2MessageLabel.setText("");
                Game.MainWindow.p1MessageLabel.setText("Click to place your mark!!");

            } else {
                Game.MainWindow.p1MessageLabel.setText("");
                Game.MainWindow.p2MessageLabel.setText("Click to place your mark!!");
            }
        } else {
            if (moveCount % 2 == 0) {
                if (Game.gameBoard.p1.cordsSelected[0]) {
                    Game.MainWindow.p1MessageLabel.setText("Click to place your mark!!");
                } else {
                    Game.MainWindow.p2MessageLabel.setText("");
                    Game.MainWindow.p1MessageLabel.setText("Click your piece to move!!");
                }

            } else {
                if (Game.gameBoard.p2.cordsSelected[0]) {
                    Game.MainWindow.p2MessageLabel.setText("Click to place your mark!!");
                } else {
                    Game.MainWindow.p1MessageLabel.setText("");
                    Game.MainWindow.p2MessageLabel.setText("Click your piece to move!!");
                }
            }
        }
    }

    public void updateBoardButtonGUI() {
        for (int i = 0; i < 9; i++) {
            Game.MainWindow.buttons[i].setText(Character.toString( Game.gameBoard.board[(i/3)][(i%3)] == '.' ? ' ' : Game.gameBoard.board[(i/3)][(i%3)] ));
        }
    }

    // Checks bound for initial and advance game.
    //If this returns false = input good! else input is wrong! try again prompt to player.

    //after 2 bad inputs skip turn as penalty.
    // NEEDS A COMPLETE GUI BASED REWRITE

    boolean validateMove(Player player) {

        //Initial game.
        if (moveCount < 6) {
            player.x_cord -= 1;
            player.y_cord -= 1;
            //to enable skipping, add this line below with '.' condition  (board[player.x_cord][player.y_cord] == player.mark ||
            return (player.x_cord < 0 || player.x_cord > 2) || (player.y_cord < 0 || player.y_cord > 2) || (board[player.x_cord][player.y_cord] != '.');
        } else {

            if (!player.cordsSelected[2]) {
                player.previous_x_cord -= 1;
                player.previous_y_cord -= 1;
                player.cordsSelected[2] = true;
            } else if (!player.cordsSelected[3]) {
                player.x_cord -= 1;
                player.y_cord -= 1;
                player.cordsSelected[3] = true;
            }

            boolean newCords = ((player.x_cord < 0 || player.x_cord > 2) || (player.y_cord < 0 || player.y_cord > 2) || (board[player.x_cord][player.y_cord] != '.'));
            boolean previousCords = ((player.previous_x_cord < 0 || player.previous_x_cord > 2) || (player.previous_y_cord < 0 || player.previous_y_cord > 2) || (board[player.previous_x_cord][player.previous_y_cord] != player.mark));

            if (previousCords) {player.cordsSelected[0] = false; player.cordsSelected[2] = false; return previousCords;}
            if (newCords) { player.cordsSelected[1] = false; player.cordsSelected[3] = false; return newCords;}

             return false; //means both are good!


        }
    }
    //AFTER GUI IMPLEMENTATION WE WOULD ONLY HAVE TO MATCH THE GUI COMPONENTS REPRESENTING CELLS WITH THE ARRAY'S ROW AND COLUMN
    //PROBABLY JUST MAKE A METHOD THAT DOES THAT WITH IFS AND CALL IT WHERE IT CURRENTLY PLACES PIECE.
    //BY GUI COMPONENTS I MEAN: TOP_LEFT = [0][0] TOP_MIDDLE = [0][1] TOP_RIGHT = [0][2] AND SO ON...
    void placePiece(Player player) {

        //initial game
        //normal moveCount++
        if (moveCount < 6) {
            board[player.x_cord][player.y_cord] = player.mark;
        }
        //advance game
        else {
            int temp_x_cord = player.previous_x_cord - player.x_cord, temp_y_cord = player.previous_y_cord - player.y_cord;

            boolean conditionsMet = false; //flag for twiceTurn, if none of the valid move executes it remains false and penalty is incremented (in player piece).

            if (temp_y_cord == 0) {   //--> it means column is same
                //row switching logic.

                if (temp_x_cord == 1 || temp_x_cord == -1) {

                    if (player.previous_x_cord < player.x_cord) {
                        conditionsMet = true;  //(temp_x_cord == -1) going down
                        board[player.x_cord][player.y_cord] = player.mark;
                        board[player.previous_x_cord][player.previous_y_cord] = '.';
                    }

                    if (player.previous_x_cord > player.x_cord) {
                        conditionsMet = true; //(temp_x_cord == 1) // going up
                        board[player.x_cord][player.y_cord] = player.mark;
                        board[player.previous_x_cord][player.previous_y_cord] = '.';
                    }

                }
                if (temp_x_cord == 2 || temp_x_cord == -2) {
                    if (player.previous_x_cord < player.x_cord) {
                       //(temp_x_cord == -2) going down
                        if (board[player.previous_x_cord + 1][player.previous_y_cord] == '.') {
                            conditionsMet = true;
                            board[player.x_cord][player.y_cord] = player.mark;
                            board[player.previous_x_cord][player.previous_y_cord] = '.';
                        }
                    }
                    if (player.previous_y_cord > player.y_cord) {
                         //(temp_x_cord == 2) going up
                        if (board[player.previous_x_cord - 1][player.previous_y_cord] == '.') {
                            conditionsMet = true;
                            board[player.x_cord][player.y_cord] = player.mark;
                            board[player.previous_x_cord][player.previous_y_cord] = '.';
                        }
                    }
                }
            }

            if (temp_x_cord == 0) {   //--> it means row is same
                //column switching logic.

                if (temp_y_cord == 1 || temp_y_cord == -1) {

                    if (player.previous_y_cord < player.y_cord) {
                        conditionsMet = true; //(temp_y_cord == -1) going right
                        board[player.x_cord][player.y_cord] = player.mark;
                        board[player.previous_x_cord][player.previous_y_cord] = '.';
                    }

                    if (player.previous_y_cord > player.y_cord) {
                        conditionsMet = true; //(temp_y_cord == 1) going left
                        board[player.x_cord][player.y_cord] = player.mark;
                        board[player.previous_x_cord][player.previous_y_cord] = '.';
                    }
                }
                if (temp_y_cord == 2 || temp_y_cord == -2) {
                    if (player.previous_y_cord < player.y_cord) {
                        conditionsMet = true; //(temp_y_cord == -2) going right
                        if (board[player.previous_x_cord][player.previous_y_cord + 1] == '.') {
                            board[player.x_cord][player.y_cord] = player.mark;
                            board[player.previous_x_cord][player.previous_y_cord] = '.';
                        }
                    }
                    if (player.previous_y_cord > player.y_cord) {
                        conditionsMet = true; //(temp_y_cord == 2) going left
                        if (board[player.previous_x_cord][player.previous_y_cord - 1] == '.') {
                            board[player.x_cord][player.y_cord] = player.mark;
                            board[player.previous_x_cord][player.previous_y_cord] = '.';
                        }
                    }
                }
            }

            // if row or column difference isn't zero, then it's a diagonal move.

            //for diagonal movement.
            temp_x_cord = Math.abs(player.previous_x_cord - player.x_cord);
            temp_y_cord = Math.abs(player.previous_y_cord - player.y_cord);

            if (temp_x_cord == 1 && temp_y_cord == 1) {
                conditionsMet = true; // moving by 1 unit from corner to center or center to corner.
                board[player.x_cord][player.y_cord] = player.mark;
                board[player.previous_x_cord][player.previous_y_cord] = '.';
            }

            if ((temp_x_cord == 2 && temp_y_cord == 2) && (board[1][1] == '.')) {
                conditionsMet = true; // moving by 2 units from corner to center or center to corner.
                board[player.x_cord][player.y_cord] = player.mark;
                board[player.previous_x_cord][player.previous_y_cord] = '.';
            }

            //after player plays their move, prompt user to accept or decline twiceTurn, if yes +1 moveCount to skip the enemy's turn.
            // and reset twice turn regardless of accept or decline as players can't stack these to break game.
            //NEEDS A COMPLETE GUI BASED REWRITE //DONE
            handleTwiceTurn();

            //gives penalty on illegal (if the above move conditions aren't executed.) moves , totaling to gives enemy twiceTurn.
            //NEEDS A COMPLETE GUI BASED REWRITE //DONE
            handlePenalty(player, conditionsMet);

        }
        moveCount++;
    }
    //after player plays their move, prompt user to accept or decline twiceTurn, if yes +1 moveCount to skip the enemy's turn.
    // and reset twice turn regardless of accept or decline as players can't stack these to break game.
    //NEEDS A COMPLETE GUI BASED REWRITE //DONE

    private void handleTwiceTurn() {
        if (twiceTurn) {
            displayBoard();
            int result = JOptionPane.showConfirmDialog(
                    null,
                    "Do you want to accept twice turn?",
                    "Twice Turn",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                moveCount++;
            }
            twiceTurn = false;
        }
    }
    //show in GUI you got +1 penalty, totaling 2 allows enemy to skip your turn, and after it resets to 0.
    //gives penalty on illegal (if the above move conditions aren't executed.) moves , totaling to gives enemy twiceTurn.
    //NEEDS A COMPLETE GUI BASED REWRITE //DONE

    private void handlePenalty(Player player, boolean conditionsMet) {
        if (!conditionsMet) {
            player.penaltyCount += 1;
            JOptionPane.showMessageDialog(null,
                    player.name+ " " + player.mark + ", You have played an illegal move, so +1 penalty, totaling 2 gives your opponent choice to skip your turn! [current penalty count: " + player.penaltyCount + "]\n");

            //show in GUI penalty has been hit and enemy has been given his turn twice.
            if (player.penaltyCount % 2 == 0) {
                twiceTurn = true;
                JOptionPane.showMessageDialog(null,player.name+ " " + player.mark + ", Penalty has been hit! Your opponent will have a choice to skip your next turn!");
                player.penaltyCount = 0;
            }
        }
    }
    //REMAINS SAME AFTER GUI REWORK.PROBABLY...

    void checkWIN(Player player) {
        for (int i = 0; i < SIZE; i++) {
            // check for row win
            if ((board[i][0] == player.mark) && (board[i][1] == player.mark) && (board[i][2] == player.mark)) {
                Game.displayWinner(player); //ask the game to exit as the winner has been decided!
            }
            // check for column win
            if ((board[0][i] == player.mark) && (board[1][i] == player.mark) && (board[2][i] == player.mark)) {
                Game.displayWinner(player); //ask the game to exit as the winner has been decided!
            }
        }

        //check diagonal win 1
        if ((board[0][0] == player.mark) && (board[1][1] == player.mark) && (board[2][2] == player.mark)) {
            Game.displayWinner(player); //ask the game to exit as the winner has been decided!
        }
        //check for diagonal win 2
        if ((board[0][2] == player.mark) && (board[1][1] == player.mark) && (board[2][0] == player.mark)) {
            Game.displayWinner(player); //ask the game to exit as the winner has been decided!
        }

    }

}
