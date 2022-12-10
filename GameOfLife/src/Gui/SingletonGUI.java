package Gui;

import Board.Grid;
import Board.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SingletonGUI {

    // TODO: Panel which tells user what to do
    // TODO: Make confirm button setEnabled(false) if input is not valid -> Cedi
    // TODO: make ButtonGrid after user has klicked submit but have placeholder in frame
    // TODO: have placeholder in JTextFileds -> Adrian
    // TODO: make overall game logic

    JPanel board;
    public static SingletonGUI INSTANCE;
    private int size;
    private int iconSize = 10;
    private JButton[][] buttonArray;
    private JLabel output = new JLabel("Click somewhere on the GUI");
    // JTextField textField1 = new JTextField("Player_1");
    // JTextField textField2 = new JTextField("Player_2");
    TextFieldWithPrompt textField1 = new TextFieldWithPrompt("Player_1", 8, 18);
    TextFieldWithPrompt textField2 = new TextFieldWithPrompt("Player_2", 8, 18);
    TextFieldWithPrompt textField3 = new TextFieldWithPrompt("Enter Even Board Size (10-20)", 8, 26);

    // textField3.setPrompt("Enter Even Board Size (9<size<31");
    JButton confirmButton = new JButton("Confirm");
    JButton redButton1 = GuiUtils.getButton(iconSize, Color.RED);
    JButton redButton2 = GuiUtils.getButton(iconSize, Color.RED);
    JButton blueButton1 = GuiUtils.getButton(iconSize, Color.BLUE);
    JButton blueButton2 = GuiUtils.getButton(iconSize, Color.BLUE);
    String playerName1 = new String();
    String playerName2 = new String();
    Color playerColor1;
    Color playerColor2;
    Player player1;
    Player player2;
    JSplitPane splitPaneChartBoard;
    private JLabel chartLabelP1 = new JLabel();
    private JLabel chartLabelP2 = new JLabel();
    private JPanel chart = getJpanel();
    private JPanel gameContainer;

    private Grid aGrid;

    private SingletonGUI() {

        confirmButton.addActionListener(e -> {
            playerName1 = textField1.getText().toString();
            playerName2 = textField2.getText().toString();
            size = Integer.parseInt(textField3.getText().toString());
            disableAll();
        });

        redButton1.addActionListener(e -> action(Color.RED, Color.GRAY, Color.GRAY, Color.BLUE, Color.RED, Color.BLUE));
        redButton2.addActionListener(e -> action(Color.GRAY, Color.RED, Color.BLUE, Color.GRAY, Color.BLUE, Color.RED));
        blueButton1.addActionListener(e -> action(Color.GRAY, Color.RED, Color.BLUE, Color.GRAY, Color.BLUE, Color.RED));
        blueButton2.addActionListener(e -> action(Color.RED, Color.GRAY, Color.GRAY, Color.BLUE, Color.RED, Color.BLUE));

        chart.add(chartLabelP1);
        chart.add(chartLabelP2);

        splitPaneChartBoard = GuiUtils.getSplitPaneVertical(400, 450, 50, chart, new JPanel());
        JSplitPane colorDual1 = GuiUtils.getSplitPaneHorizontal(400, 30, 100, redButton1, blueButton1);
        JSplitPane colorDual2 = GuiUtils.getSplitPaneHorizontal(400, 30, 100, redButton2, blueButton2);
        JSplitPane splitPaneTextFieldsColor1 = GuiUtils.getSplitPaneVertical(400, 60, 30, textField1, colorDual1);
        JSplitPane splitPaneTextFieldsColor2 = GuiUtils.getSplitPaneVertical(400, 60, 30, textField2, colorDual2);
        JSplitPane splitPaneTextFields = GuiUtils.getSplitPaneHorizontal(400, 60, 200, splitPaneTextFieldsColor1, splitPaneTextFieldsColor2);
        JSplitPane splitPaneConfirmResolution = GuiUtils.getSplitPaneHorizontal(400, 30, 200, textField3, confirmButton);
        JSplitPane splitPaneButtonText = GuiUtils.getSplitPaneVertical(400, 90, 60, splitPaneTextFields, splitPaneConfirmResolution);
        JSplitPane splitPaneBoardFields = GuiUtils.getSplitPaneVertical(400, 700, 450, splitPaneChartBoard, splitPaneButtonText);
        getMainFrame(splitPaneBoardFields);
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
        board.add(output, BorderLayout.PAGE_END);
        gameContainer = new JPanel(new GridLayout(0, size, 2, 2));
        board.add(gameContainer);
        ActionListener actionListener = e -> output.setText(getButtonRowCol((JButton) e.getSource()));
        ActionListener actionListener1 = s -> aGrid.reviveACell(getButtonRowCol((JButton) s.getSource()));
        ActionListener actionListener2 = y -> showGrid();
        for (int ii = 0; ii < size * size; ii++) {
            JButton b = getButton(iconSize, actionListener, actionListener1, actionListener2);
            gameContainer.add(b);
            buttonArray[ii % size][ii / size] = b;
        }
        return board;
    }

    private void action(Color red1, Color red2, Color blue1, Color blue2, Color player1, Color player2) {
        redButton1.setBackground(red1);
        redButton2.setBackground(red2);
        blueButton1.setBackground(blue1);
        blueButton2.setBackground(blue2);
        playerColor1 = player1;
        playerColor2 = player2;
    }

    private void disableAll() {
        textField1.setEnabled(false);
        textField2.setEnabled(false);
        textField3.setEnabled(false);
        redButton1.setEnabled(false);
        redButton2.setEnabled(false);
        blueButton1.setEnabled(false);
        blueButton2.setEnabled(false);
        confirmButton.setEnabled(false);
        redButton1.setBackground(playerColor1);
        blueButton1.setBackground(playerColor1);
        redButton2.setBackground(playerColor2);
        blueButton2.setBackground(playerColor2);
        player1 = new Player(playerName1, playerColor1, 20, true); // cellCnt = 20 still has to be set differently
        setStats(true);
        player2 = new Player(playerName2, playerColor2, 20, false);
        setStats(false);
        aGrid = new Grid(size);
        splitPaneChartBoard.setBottomComponent(getBoard());
    }

    private String getButtonRowCol(JButton button) {
        StringBuilder sb = new StringBuilder();
        for (int xx = 0; xx < size; xx++) {
            for (int yy = 0; yy < size; yy++) {
                if (button.equals(buttonArray[xx][yy])) {
                    sb.append("User selected button at: ");
                    sb.append(xx);
                    sb.append(",");
                    sb.append(yy);
                    break;
                }
            }
        }
        return sb.toString();
    }

    public static JFrame getMainFrame(JSplitPane splitPane) {
        JFrame frame = new JFrame("Game Of Life");
        frame.setPreferredSize(new Dimension(400, 600));
        frame.getContentPane().setLayout(new GridLayout());
        frame.add(splitPane);
        frame.pack();
        frame.setSize(400, 600);
        frame.setResizable(false);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }

    private void setButtonGrid() {

    }

    public JButton getButton(int iconSize, ActionListener actionListener, ActionListener actionListener1, ActionListener actionListener2) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_ARGB)));
        button.setRolloverIcon(new ImageIcon(new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_RGB)));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.addActionListener(actionListener);
        button.addActionListener(actionListener1);
        button.addActionListener(actionListener2);
        return button;
    }

    public JPanel getJpanel() {
        JPanel panel = new JPanel();
        panel.setSize(400, 25);
        panel.setBackground(Color.WHITE);
        panel.setVisible(true);
        panel.setBorder(BorderFactory.createTitledBorder("Chart"));
        return panel;
    }

    public void setStats(boolean forPlayer1) {
        if (forPlayer1)
            chartLabelP1.setText(player1.getPlayerName() + ": " + String.valueOf(player1.getCellCnt()) + " Cells       \n");
        else
            chartLabelP2.setText("       " + player2.getPlayerName() + ": " + String.valueOf(player2.getCellCnt()) + " Cells\n");
    }

    private void showGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Color tmp = aGrid.aGrid[i][j].getColor().toColor();
                buttonArray[i][j].setContentAreaFilled(false);
                buttonArray[i][j].setOpaque(true);
                buttonArray[i][j].setBackground(tmp);
            }
        }
    }
}
