package Gui;

import Board.*;
import GameState.*;
import Utils.InputUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SingletonGUI extends JFrame {
    public static SingletonGUI INSTANCE;
    private int size;
    private int iconSize = 10;
    private JButton[][] buttonArray;
    Player currentPlayer;
    TextFieldWithPrompt textField1 = new TextFieldWithPrompt("Player_1", 8, 18);
    TextFieldWithPrompt textField2 = new TextFieldWithPrompt("Player_2", 8, 18);
    TextFieldWithPrompt textField3 = new TextFieldWithPrompt("Enter Even Board Size (10-20)", 8, 27);
    JButton confirmButton = new JButton("Confirm");
    JButton redButton1 = GuiUtils.getButton(iconSize, ColorType.RED.toColor());
    JButton redButton2 = GuiUtils.getButton(iconSize, ColorType.RED.toColor());
    JButton blueButton1 = GuiUtils.getButton(iconSize, ColorType.BLUE.toColor());
    JButton blueButton2 = GuiUtils.getButton(iconSize, ColorType.BLUE.toColor());
    Player player1;
    Player player2;
    JSplitPane splitPaneChartBoard;
    private JLabel chartLabelP1 = new JLabel();
    private JLabel chartLabelP2 = new JLabel();
    private JLabel chartLabelMessage = new JLabel();
    private JPanel chart = getJpanel("Chart");
    private JPanel messages = getJpanel("Game Rules");
    private JPanel gameContainer;
    private Game game;
    private Grid aGrid;


    private SingletonGUI() {

        player1 = new Player();
        player2 = new Player();

        confirmButton.addActionListener(e -> {
            player1.setPlayerName(textField1.getText().toString());
            player2.setPlayerName(textField2.getText().toString());
            size = Integer.parseInt(textField3.getText().toString());
            disableAll();
            messages.revalidate();
            messages.repaint();
            chartLabelMessage.revalidate();
            chartLabelMessage.revalidate();
        });

        redButton1.addActionListener(e -> action(ColorType.RED, ColorType.GREY, ColorType.GREY, ColorType.BLUE, ColorType.RED, ColorType.BLUE));
        redButton2.addActionListener(e -> action(ColorType.GREY, ColorType.RED, ColorType.BLUE, ColorType.GREY, ColorType.BLUE, ColorType.RED));
        blueButton1.addActionListener(e -> action(ColorType.GREY, ColorType.RED, ColorType.BLUE, ColorType.GREY, ColorType.BLUE, ColorType.RED));
        blueButton2.addActionListener(e -> action(ColorType.RED, ColorType.GREY, ColorType.GREY, ColorType.BLUE, ColorType.RED, ColorType.BLUE));

        chart.add(chartLabelP1);
        chart.add(chartLabelP2);
        messages.add(chartLabelMessage);

        JSplitPane SplitPaneMessagesChars = GuiUtils.getSplitPaneVertical(400, 60, 30, chart, messages);
        splitPaneChartBoard = GuiUtils.getSplitPaneVertical(400, 450, 60, SplitPaneMessagesChars, new JPanel());
        JSplitPane colorDual1 = GuiUtils.getSplitPaneHorizontal(400, 30, 100, redButton1, blueButton1);
        JSplitPane colorDual2 = GuiUtils.getSplitPaneHorizontal(400, 30, 100, redButton2, blueButton2);
        JSplitPane splitPaneTextFieldsColor1 = GuiUtils.getSplitPaneVertical(400, 60, 30, textField1, colorDual1);
        JSplitPane splitPaneTextFieldsColor2 = GuiUtils.getSplitPaneVertical(400, 60, 30, textField2, colorDual2);
        JSplitPane splitPaneTextFields = GuiUtils.getSplitPaneHorizontal(400, 60, 200, splitPaneTextFieldsColor1, splitPaneTextFieldsColor2);
        JSplitPane splitPaneConfirmResolution = GuiUtils.getSplitPaneHorizontal(400, 30, 200, textField3, confirmButton);
        JSplitPane splitPaneButtonText = GuiUtils.getSplitPaneVertical(400, 90, 60, splitPaneTextFields, splitPaneConfirmResolution);
        JSplitPane splitPaneBoardFields = GuiUtils.getSplitPaneVertical(400, 700, 450, splitPaneChartBoard, splitPaneButtonText);

        setName("Game Of Life");
        setPreferredSize(new Dimension(400, 600));
        getContentPane().setLayout(new GridLayout());
        add(splitPaneBoardFields);
        pack();
        setSize(400, 600);
        setResizable(false);
        setLocationByPlatform(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static synchronized SingletonGUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonGUI();
        }
        return INSTANCE;
    }

    private JPanel getBoard() {
        buttonArray = new JButton[size][size];
        JPanel board = new JPanel(new BorderLayout(2, 2));
        board.setBorder(new EmptyBorder(4, 4, 4, 4));
        gameContainer = new JPanel(new GridLayout(0, size, 2, 2));
        board.add(gameContainer);
        ActionListener actionListener1 = s -> gameLogic(getButtonRowCol((JButton) s.getSource()));
        for (int ii = 0; ii < size * size; ii++) {
            JButton b = getButton(iconSize, actionListener1);
            gameContainer.add(b);
            buttonArray[ii % size][ii / size] = b;
        }
        return board;
    }

    private void gameLogic(int[] buttonRowCol) {
        int y = buttonRowCol[0];
        int x = buttonRowCol[1];
        setStats();
        if (!game.initOver()) {
            if (!aGrid.cellIsAlive(y, x)) {
                game.clickedEmptyCell(y, x, currentPlayer.getPlayerColor());
            }
            setMessage("An Existing Cell Was Clicked!");
            if (!aGrid.cellhasColor(y, x, currentPlayer.getPlayerColor())) {
                game.clickedExistingCell(y, x, currentPlayer.getPlayerColor());
                setMessage("An Empty Cell Was Clicked.");
            }
        }
        showGrid();
        checkIfLost();
    }

    private void checkIfLost(){
        if (player1.getCellCnt() == 0 && player2.getCellCnt() == 0){
            disableAll();
            setMessage("Both player have lost the game -> TIE");
        }
        if (player1.getCellCnt() == 0){
            disableAll();
            setMessage("Player " + player1.getPlayerName() + "has lost! -> " + player2.getPlayerName());
        }
        if (player2.getCellCnt() == 0){
            disableAll();
            setMessage("Player " + player2.getPlayerName() + "has lost! -> " + player1.getPlayerName());
        }
    }

    private void action(ColorType red1, ColorType red2, ColorType blue1, ColorType blue2, ColorType colourP1, ColorType colourP2) {
        redButton1.setBackground(red1.toColor());
        redButton2.setBackground(red2.toColor());
        blueButton1.setBackground(blue1.toColor());
        blueButton2.setBackground(blue2.toColor());
        player1.setPlayerColor(colourP1);
        player2.setPlayerColor(colourP2);
    }

    private void disableAllFinished(){
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                buttonArray[row][col].setEnabled(false);
            }
        }
    }

    private void disableAll() {
        if (textField1.getText().isBlank() || textField2.getText().isBlank() || textField3.getText().isBlank()) {
            setMessage("Please do not leave a field blank.");
            return;
        }
        int cleanUpText3;
        try {
            cleanUpText3 = Integer.parseInt(InputUtils.cleanUpString(textField3.getText()));
        } catch (NumberFormatException e) {
            setMessage("Please fill in a Integer. Only even numbers are allowed.");
            return;
        }
        if (cleanUpText3 % 2 != 0 || cleanUpText3 == 0 || cleanUpText3<10 || cleanUpText3>20) {
            setMessage("Please select a even Integer between 10 and 20.");
            return;
        }
        if(player1.getPlayerColor() == null || player2.getPlayerColor() == null){
            setMessage("Please select a color for the players.");
            return;
        }
        textField1.setEnabled(false);
        textField2.setEnabled(false);
        textField3.setEnabled(false);
        redButton1.setEnabled(false);
        redButton2.setEnabled(false);
        blueButton1.setEnabled(false);
        blueButton2.setEnabled(false);
        confirmButton.setEnabled(false);
        redButton1.setBackground(player1.getPlayerColor().toColor());
        blueButton1.setBackground(player1.getPlayerColor().toColor());
        redButton2.setBackground(player2.getPlayerColor().toColor());
        blueButton2.setBackground(player2.getPlayerColor().toColor());
        player1.setPlayerName(textField1.getText());
        player1.setPlayerName(textField2.getText());
        aGrid = new Grid(size, player1, player2);
        game = new Game(player1, player2, aGrid);
        splitPaneChartBoard.setBottomComponent(getBoard());
        currentPlayer = player1;
        if (player1.compareTo(player2) == 1) {
            currentPlayer = player2;
        }
    }

    private void setMessage(String message) {
        chartLabelMessage.setText(message);
    }

    private int[] getButtonRowCol(JButton button) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (button.equals(buttonArray[x][y])) {
                    return new int[]{x, y};
                }
            }
        }
        return new int[]{0, 0};
    }

    private JButton getButton(int iconSize, ActionListener actionListener1) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_ARGB)));
        button.setRolloverIcon(new ImageIcon(new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_RGB)));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.addActionListener(actionListener1);
        return button;
    }

    private JPanel getJpanel(String title) {
        JPanel panel = new JPanel();
        panel.setSize(400, 25);
        panel.setBackground(ColorType.WHITE.toColor());
        panel.setVisible(true);
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    private void setStats() {
        chartLabelP1.setText(player1.getPlayerName() + ": " + player1.getCellCnt() + " Cells\t\t" + player2.getPlayerName() + " : " + player2.getCellCnt() + " Cells");
    }

    private void showGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Color tmp = aGrid.aGrid[i][j].getColor().toColor();
                final int z = i;
                final int c = j;
                buttonArray[i][j].setContentAreaFilled(false);
                buttonArray[i][j].setOpaque(true);
                buttonArray[i][j].setBackground(tmp);
                buttonArray[i][j].repaint();
            }
        }
    }
}
