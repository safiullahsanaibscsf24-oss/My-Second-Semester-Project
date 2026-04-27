import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel MAIN_PANEL = new JPanel(cardLayout);

    public static final String TITLE_SCREEN_CONST = "TITLE";
    public static final String GET_INFO_CONST = "GET-INFO";
    public static final String BOARD_SCREEN_CONST = "BOARD";
    public static final String WIN_SCREEN_CONST = "WIN";

    private JPanel TITLE_SCREEN;
    private JPanel GET_INFO;
    private JPanel BOARD_SCREEN;
    private JPanel WIN_SCREEN;

    // from createBOARD
    public JButton[] buttons = new JButton[9];
    JPanel nameMarkMessagePanel;
    JLabel p1Label;
    JLabel p1MessageLabel;
    JLabel p2Label;
    JLabel p2MessageLabel;

    //From GET_INFO
    JTextField p1Name, p1Mark, p2Name;

    //from WIN_SCREEN
    JLabel winnerLabel;

    //creates GameWindow and TITLE_SCREEN
    GameWindow() {
        super("Tik-Tac-Move!");
        ImageIcon icon = new ImageIcon("Icon.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(500, 500);

        MAIN_PANEL.setSize(500, 500);
        MAIN_PANEL.setBackground(new Color(255, 229, 180));

        setContentPane(MAIN_PANEL);

        createTITLE_SCREEN();
        MAIN_PANEL.add(TITLE_SCREEN, TITLE_SCREEN_CONST);

        createGET_INFO();
        MAIN_PANEL.add(GET_INFO, GET_INFO_CONST);

        createBOARD_SCREEN();
        MAIN_PANEL.add(BOARD_SCREEN, BOARD_SCREEN_CONST);

        createWIN_SCREEN();
        MAIN_PANEL.add(WIN_SCREEN, WIN_SCREEN_CONST);

        setVisible(true);

    }

    private void createTITLE_SCREEN() {
        TITLE_SCREEN = new JPanel(new GridLayout(2, 1));
        TITLE_SCREEN.setSize(500, 500);

        JLabel logoLabel = new JLabel(new ImageIcon("newLogo.png"));
        logoLabel.setSize(500, 250);

        TITLE_SCREEN.add(logoLabel);

        JPanel startButtonPanel = new JPanel();
        startButtonPanel.setSize(500, 250);
        startButtonPanel.setLayout(new BorderLayout(0, 0));

        JButton startButton = createButton();
        startButton.setBorder(null);
        startButton.setText("Start Game!");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchScreen(GET_INFO_CONST);
            }
        });

        startButtonPanel.setBackground(new Color(255, 229, 180));
        startButtonPanel.add(startButton, BorderLayout.NORTH);

        TITLE_SCREEN.add(startButtonPanel);
    }

    public void createGET_INFO() {

        GET_INFO = new JPanel(new GridLayout(2, 1));
        GET_INFO.setSize(500, 500);
        GET_INFO.setBackground(new Color(255, 229, 180));

        JLabel logoLabel = new JLabel(new ImageIcon("newLogo.png"));
        logoLabel.setSize(500, 250);

        GET_INFO.add(logoLabel);

        JPanel componentPanel = new JPanel(new GridLayout(4, 3));
        componentPanel.setSize(500, 250);
        componentPanel.setBackground(new Color(255, 229, 180));

        JLabel p1NameLabel = new JLabel("Player 1 Name: ", SwingConstants.CENTER),
                p2NameLabel = new JLabel("Player 2 Name: ", SwingConstants.CENTER),
                p1MarkLabel = new JLabel("Player 1 Mark (X or O)", SwingConstants.CENTER);

        p1NameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        p1MarkLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        p2NameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));

        p1Name = new JTextField();
        p1Mark = new JTextField();
        p2Name = new JTextField();
        //Made 4x4 grid, what we need is the 2 and 3 row. 1 and 4 row can remain empty.

        //first row empty
        for (int i = 0; i < 3; i++) {
            JPanel tempPanel = new JPanel();
            tempPanel.setBackground(new Color(255, 229, 180));
            componentPanel.add(tempPanel);
        }

        //Added labels for TextFields
        componentPanel.add(p1NameLabel);
        componentPanel.add(p1MarkLabel);
        componentPanel.add(p2NameLabel);

        componentPanel.add(p1Name);
        componentPanel.add(p1Mark);
        componentPanel.add(p2Name);

        //last row empty
        for (int i = 0; i < 3; i++) {

            if (i == 1) {
                JButton submitButton = createButton();
                submitButton.setText("Submit!");

                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        boolean[] detailsFlag = {false, false, false};

                        if (p1Name.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Player 1 Name Field Can't Be Empty!");
                        } else {
                            detailsFlag[0] = true;
                        }

                        if (p2Name.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Player 2 Name Field Can't Be Empty!");
                        } else {
                            detailsFlag[1] = true;
                        }
                        if (!(p1Mark.getText().equalsIgnoreCase("X") || p1Mark.getText().equalsIgnoreCase("O"))) {
                            JOptionPane.showMessageDialog(null, "Player 1 Mark Is Not Valid! (X or O)");
                        } else {
                            detailsFlag[2] = true;
                        }

                        if (detailsFlag[0] && detailsFlag[1] && detailsFlag[2]) {
                            Game.startGameLoop();
                        }

                    }
                });

                submitButton.setBorder(null);
                componentPanel.add(submitButton);
                continue;
            }

            JPanel tempPanel = new JPanel();
            tempPanel.setBackground(new Color(255, 229, 180));
            componentPanel.add(tempPanel);
        }

        GET_INFO.add(componentPanel);
    }

    private void createBOARD_SCREEN() {

        BOARD_SCREEN = new JPanel(new BorderLayout());

        // Top panel holding p1Label 1 and p2Label
        nameMarkMessagePanel = new JPanel(new GridLayout(2, 2));

        p1Label = new JLabel("player 1, Mark", SwingConstants.CENTER);
        p1Label.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        nameMarkMessagePanel.add(p1Label);

        p2Label = new JLabel("player 2, Mark", SwingConstants.CENTER);
        p2Label.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        nameMarkMessagePanel.add(p2Label);

        p1MessageLabel = new JLabel("player 1, Mark", SwingConstants.CENTER);
        p1MessageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        nameMarkMessagePanel.add(p1MessageLabel);

        p2MessageLabel = new JLabel("player 2, Mark", SwingConstants.CENTER);
        p2MessageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        nameMarkMessagePanel.add(p2MessageLabel);

        nameMarkMessagePanel.setPreferredSize(new Dimension(500, 50));
        nameMarkMessagePanel.setBackground(new Color(160, 82, 45));
        // JLabel.setFont(new Font("Courier New", Font.ITALIC, 20));

        // Center panel with 3x3 buttons
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 0, 0));
        // gridPanel.setPreferredSize(new Dimension(500, 700));

        //create 9 buttons
        for (int i = 0; i < 9; i++) {
            buttons[i] = createButton();

            int cellNo = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    theResolver(cellNo);
                }

                void theResolver(int index) {

                    if (Game.gameBoard.moveCount % 2 == 0) {
                        Game.gameBoard.p1.playTurn(index);
                    } else {
                        Game.gameBoard.p2.playTurn(index);
                    }
                }


            });

            gridPanel.add(buttons[i]);
        }

        // Add panels to frame
        BOARD_SCREEN.add(nameMarkMessagePanel, BorderLayout.NORTH);
        BOARD_SCREEN.add(gridPanel, BorderLayout.CENTER);

    }

    private void createWIN_SCREEN() {
        WIN_SCREEN = new JPanel(new GridLayout(2, 1));

        WIN_SCREEN.setSize(500, 500);
        WIN_SCREEN.setBackground(new Color(255, 229, 180));

        JLabel logoLabel = new JLabel(new ImageIcon("newLogo.png"));
        logoLabel.setSize(500, 250);

        WIN_SCREEN.add(logoLabel);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 0));
        bottomPanel.setSize(500, 250);
        bottomPanel.setBackground(new Color(255, 229, 180));

        winnerLabel = new JLabel("player INT, Mark has won the game!!", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));

        bottomPanel.add(winnerLabel, BorderLayout.CENTER);

        WIN_SCREEN.add(winnerLabel);

    }


    private JButton createButton() {
        JButton tempButton = new JButton();
        tempButton.setBackground(new Color(255, 229, 180));
        //            button.setBackground(Color.MAGENTA);       // Background color
        tempButton.setForeground(Color.BLACK);      // Text color
        tempButton.setFocusPainted(true);
        tempButton.setBorderPainted(true);
        tempButton.setContentAreaFilled(true);
        tempButton.setForeground(Color.BLACK);
        //tempButton.setFont(new Font("Arial", Font.BOLD, 14));
        tempButton.setBorder(BorderFactory.createLineBorder(new Color(160, 82, 45), 4));
        tempButton.setPreferredSize(new Dimension(100, 40));
        tempButton.setFont(new Font("Comic Sans MS", Font.BOLD, 26));

        return tempButton;
    }

    public void switchScreen(String choice) {
        switch (choice) {
            case TITLE_SCREEN_CONST:
                cardLayout.show(MAIN_PANEL, TITLE_SCREEN_CONST);
                break;

            case GET_INFO_CONST:
                cardLayout.show(MAIN_PANEL, GET_INFO_CONST);
                break;

            case BOARD_SCREEN_CONST:
                cardLayout.show(MAIN_PANEL, BOARD_SCREEN_CONST);
                break;

            case WIN_SCREEN_CONST:
                cardLayout.show(MAIN_PANEL, WIN_SCREEN_CONST);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
    }


}
