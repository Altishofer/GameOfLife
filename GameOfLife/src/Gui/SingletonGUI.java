package Gui;

import Board.*;
import GameState.*;
import Utils.InputUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SingletonGUI extends JFrame {
    public static SingletonGUI INSTANCE;
    private int aSize;
    private int aIconSize = 10;
    private JButton[][] aButtonArray;
    Player aCurrentPlayer;
    TextFieldWithPrompt aTextField1 = new TextFieldWithPrompt("Player_1", 8, 18);
    TextFieldWithPrompt aTextField2 = new TextFieldWithPrompt("Player_2", 8, 18);
    TextFieldWithPrompt aTextField3 = new TextFieldWithPrompt("Enter Even Board Size (10-20)", 8, 27);
    JButton aConfirmButton = new JButton("Confirm");
    JButton aRedButton1 = GuiUtils.getButton(aIconSize, ColorType.RED.toColor());
    JButton aRedButton2 = GuiUtils.getButton(aIconSize, ColorType.RED.toColor());
    JButton aBlueButton1 = GuiUtils.getButton(aIconSize, ColorType.BLUE.toColor());
    JButton aBlueButton2 = GuiUtils.getButton(aIconSize, ColorType.BLUE.toColor());
    Player aPlayer1;
    Player aPlayer2;
    JSplitPane aSplitPaneChartBoard;
    private JLabel aChartLabelP1 = new JLabel();
    private JLabel aChartLabelP2 = new JLabel();
    private JLabel aChartLabelMessage = new JLabel();
    private JPanel aChart = getJpanel("Chart");
    private JPanel aMessages = getJpanel("Game Rules");
    private JPanel aGameContainer;
    private Game aGame;
    private Grid aGrid;


    private SingletonGUI() {

        aPlayer1 = new Player();
        aPlayer2 = new Player();

        aConfirmButton.addActionListener(e -> {
            aPlayer1.setPlayerName(aTextField1.getText().toString());
            aPlayer2.setPlayerName(aTextField2.getText().toString());
            aSize = Integer.parseInt(aTextField3.getText().toString());
            disableAll();
            aMessages.revalidate();
            aMessages.repaint();
            aChartLabelMessage.revalidate();
            aChartLabelMessage.revalidate();
        });

        aRedButton1.addActionListener(e -> action(ColorType.RED, ColorType.GREY, ColorType.GREY, ColorType.BLUE, ColorType.RED, ColorType.BLUE));
        aRedButton2.addActionListener(e -> action(ColorType.GREY, ColorType.RED, ColorType.BLUE, ColorType.GREY, ColorType.BLUE, ColorType.RED));
        aBlueButton1.addActionListener(e -> action(ColorType.GREY, ColorType.RED, ColorType.BLUE, ColorType.GREY, ColorType.BLUE, ColorType.RED));
        aBlueButton2.addActionListener(e -> action(ColorType.RED, ColorType.GREY, ColorType.GREY, ColorType.BLUE, ColorType.RED, ColorType.BLUE));

        aChart.add(aChartLabelP1);
        aChart.add(aChartLabelP2);
        aMessages.add(aChartLabelMessage);

        JSplitPane SplitPaneMessagesChars = GuiUtils.getSplitPaneVertical(400, 60, 30, aChart, aMessages);
        aSplitPaneChartBoard = GuiUtils.getSplitPaneVertical(400, 450, 60, SplitPaneMessagesChars, new JPanel());
        JSplitPane colorDual1 = GuiUtils.getSplitPaneHorizontal(400, 30, 100, aRedButton1, aBlueButton1);
        JSplitPane colorDual2 = GuiUtils.getSplitPaneHorizontal(400, 30, 100, aRedButton2, aBlueButton2);
        JSplitPane splitPaneTextFieldsColor1 = GuiUtils.getSplitPaneVertical(400, 60, 30, aTextField1, colorDual1);
        JSplitPane splitPaneTextFieldsColor2 = GuiUtils.getSplitPaneVertical(400, 60, 30, aTextField2, colorDual2);
        JSplitPane splitPaneTextFields = GuiUtils.getSplitPaneHorizontal(400, 60, 200, splitPaneTextFieldsColor1, splitPaneTextFieldsColor2);
        JSplitPane splitPaneConfirmResolution = GuiUtils.getSplitPaneHorizontal(400, 30, 200, aTextField3, aConfirmButton);
        JSplitPane splitPaneButtonText = GuiUtils.getSplitPaneVertical(400, 90, 60, splitPaneTextFields, splitPaneConfirmResolution);
        JSplitPane splitPaneBoardFields = GuiUtils.getSplitPaneVertical(400, 700, 450, aSplitPaneChartBoard, splitPaneButtonText);

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
        aButtonArray = new JButton[aSize][aSize];
        JPanel board = new JPanel(new BorderLayout(2, 2));
        board.setBorder(new EmptyBorder(4, 4, 4, 4));
        aGameContainer = new JPanel(new GridLayout(0, aSize, 2, 2));
        board.add(aGameContainer);
        ActionListener actionListener1 = s -> gameLogic(getButtonRowCol((JButton) s.getSource()));
        for (int ii = 0; ii < aSize * aSize; ii++) {
            JButton b = getButton(aIconSize, actionListener1);
            aGameContainer.add(b);
            aButtonArray[ii % aSize][ii / aSize] = b;
        }
        return board;
    }

    private void gameLogic(int[] buttonRowCol) {
        int y = buttonRowCol[0];
        int x = buttonRowCol[1];
        setStats();
        if (!aGame.initOver()) {
            if (!aGrid.cellIsAlive(y, x)) {
                aGame.clickedEmptyCell(y, x, aCurrentPlayer.getPlayerColor());
            }
            setMessage("An Existing Cell Was Clicked!");
            if (!aGrid.cellhasColor(y, x, aCurrentPlayer.getPlayerColor())) {
                aGame.clickedExistingCell(y, x, aCurrentPlayer.getPlayerColor());
                setMessage("An Empty Cell Was Clicked.");
            }
        }
        showGrid();
        checkIfLost();
    }

    private void checkIfLost(){
        if (aPlayer1.getCellCnt() == 0 && aPlayer2.getCellCnt() == 0){
            disableAll();
            setMessage("Both player have lost the game -> TIE");
        }
        if (aPlayer1.getCellCnt() == 0){
            disableAll();
            setMessage("Player " + aPlayer1.getPlayerName() + "has lost! -> " + aPlayer2.getPlayerName());
        }
        if (aPlayer2.getCellCnt() == 0){
            disableAll();
            setMessage("Player " + aPlayer2.getPlayerName() + "has lost! -> " + aPlayer1.getPlayerName());
        }
    }

    private void action(ColorType red1, ColorType red2, ColorType blue1, ColorType blue2, ColorType colourP1, ColorType colourP2) {
        aRedButton1.setBackground(red1.toColor());
        aRedButton2.setBackground(red2.toColor());
        aBlueButton1.setBackground(blue1.toColor());
        aBlueButton2.setBackground(blue2.toColor());
        aPlayer1.setPlayerColor(colourP1);
        aPlayer2.setPlayerColor(colourP2);
    }

    private void disableAllFinished(){
        for (int row = 0; row < aSize; row++) {
            for (int col = 0; col < aSize; col++) {
                aButtonArray[row][col].setEnabled(false);
            }
        }
    }

    private void disableAll() {
        if (aTextField1.getText().isBlank() || aTextField2.getText().isBlank() || aTextField3.getText().isBlank()) {
            setMessage("Please fill in your usernames.");
            return;
        }
        int cleanUpText3;
        try {
            cleanUpText3 = Integer.parseInt(InputUtils.cleanUpString(aTextField3.getText()));
        } catch (NumberFormatException e) {
            setMessage("Please select a valid resolution type. Only even numbers are allowed.");
            return;
        }
        if (cleanUpText3 % 2 != 0 && cleanUpText3 == 0 && aPlayer1.getPlayerColor() == null) {
            setMessage("Please select a color for the players.");
            return;
        }
        aTextField1.setEnabled(false);
        aTextField2.setEnabled(false);
        aTextField3.setEnabled(false);
        aRedButton1.setEnabled(false);
        aRedButton2.setEnabled(false);
        aBlueButton1.setEnabled(false);
        aBlueButton2.setEnabled(false);
        aConfirmButton.setEnabled(false);
        aRedButton1.setBackground(aPlayer1.getPlayerColor().toColor());
        aBlueButton1.setBackground(aPlayer1.getPlayerColor().toColor());
        aRedButton2.setBackground(aPlayer2.getPlayerColor().toColor());
        aBlueButton2.setBackground(aPlayer2.getPlayerColor().toColor());
        aPlayer1.setPlayerName(aTextField1.getText());
        aPlayer1.setPlayerName(aTextField2.getText());
        aGrid = new Grid(aSize, aPlayer1, aPlayer2);
        aGame = new Game(aPlayer1, aPlayer2, aGrid);
        aSplitPaneChartBoard.setBottomComponent(getBoard());
        aCurrentPlayer = aPlayer1;
        if (aPlayer1.compareTo(aPlayer2) == 1) {
            aCurrentPlayer = aPlayer2;
        }
    }

    private void setMessage(String message) {
        aChartLabelMessage.setText(message);
    }

    private int[] getButtonRowCol(JButton button) {
        for (int y = 0; y < aSize; y++) {
            for (int x = 0; x < aSize; x++) {
                if (button.equals(aButtonArray[x][y])) {
                    return new int[]{x, y};
                }
            }
        }
        return new int[]{0, 0};
    }

    public JButton getButton(int iconSize, ActionListener actionListener1) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_ARGB)));
        button.setRolloverIcon(new ImageIcon(new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_RGB)));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.addActionListener(actionListener1);
        return button;
    }

    public JPanel getJpanel(String title) {
        JPanel panel = new JPanel();
        panel.setSize(400, 25);
        panel.setBackground(ColorType.WHITE.toColor());
        panel.setVisible(true);
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    public void setStats() {
        aChartLabelP1.setText(aPlayer1.getPlayerName() + ": " + aPlayer1.getCellCnt() + " Cells\t\t" + aPlayer2.getPlayerName() + " : " + aPlayer2.getCellCnt() + " Cells");
    }

    private void showGrid() {
        for (int i = 0; i < aSize; i++) {
            for (int j = 0; j < aSize; j++) {
                Color tmp = aGrid.aGrid[i][j].getColor().toColor();
                final int z = i;
                final int c = j;
                aButtonArray[i][j].setContentAreaFilled(false);
                aButtonArray[i][j].setOpaque(true);
                aButtonArray[i][j].setBackground(tmp);
                aButtonArray[i][j].repaint();
            }
        }
    }
}
