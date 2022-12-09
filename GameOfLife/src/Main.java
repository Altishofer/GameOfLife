import Board.Grid;
import Board.Player;
import Gui.GuiUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


public class Main {

    public static void main(String[] args) {
        Runnable r = () -> new Main();
        SwingUtilities.invokeLater(r);
        /*Grid grid = new Grid(10);
        grid.paintCell(1,1,1);
        grid.paintCell(1,2,1);
        grid.paintCell(1,3,1);
        grid.paintCell(5,5,2);
        grid.paintCell(5,6,2);
        grid.paintCell(6,5,2);
        grid.printGrid();
        grid.evolveCells();
        System.out.print("\n");
        grid.printGrid();*/

    }

    // TODO: farbe assignen via coordinates, Adrian
    // TODO: dashboard statistik neben Grid, mit fields die assignt werden können, Adrian

    // TODO: User Input at beginning, Color and Name, Sandrin
    
    private int size = 20;
    private int iconSize = 10;
    private JButton[][] buttonArray = new JButton[size][size];
    private JLabel output = new JLabel("Click somewhere on the GUI");
    JTextField textField1 = new JTextField("Player_1");
    JTextField textField2 = new JTextField("Player_2");
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

    Main() {

        confirmButton.addActionListener(e -> {
            playerName1 = textField1.getText().toString();
            playerName2 = textField2.getText().toString();
            disableAll();
        });

        redButton1.addActionListener(e -> action(Color.RED, Color.GRAY, Color.GRAY, Color.BLUE, Color.RED, Color.BLUE));
        redButton2.addActionListener(e -> action(Color.GRAY, Color.RED, Color.BLUE, Color.GRAY, Color.BLUE, Color.RED));
        blueButton1.addActionListener(e -> action(Color.GRAY, Color.RED, Color.BLUE, Color.GRAY, Color.BLUE, Color.RED));
        blueButton2.addActionListener(e -> action(Color.RED, Color.GRAY, Color.GRAY, Color.BLUE, Color.RED, Color.BLUE));

        JPanel chart = getJpanel();
        JPanel board = getBoard();

        JSplitPane splitPaneChartBoard =  GuiUtils.getSplitPaneVertical(400, 450, 50, chart, board);
        JSplitPane colorDual1 = GuiUtils.getSplitPaneHorizontal(400, 30, 100, redButton1, blueButton1);
        JSplitPane colorDual2 = GuiUtils.getSplitPaneHorizontal(400, 30, 100, redButton2, blueButton2);
        JSplitPane splitPaneTextFieldsColor1 = GuiUtils.getSplitPaneVertical(400, 60, 30, textField1, colorDual1);
        JSplitPane splitPaneTextFieldsColor2 = GuiUtils.getSplitPaneVertical(400, 60, 30, textField2, colorDual2);
        JSplitPane splitPaneTextFields = GuiUtils.getSplitPaneHorizontal(400, 60, 200, splitPaneTextFieldsColor1, splitPaneTextFieldsColor2);
        JSplitPane splitPaneButtonText = GuiUtils.getSplitPaneVertical(400, 90, 60, splitPaneTextFields, confirmButton);
        JSplitPane splitPaneBoardFields = GuiUtils.getSplitPaneVertical(400, 700, 450, splitPaneChartBoard, splitPaneButtonText);
        getMainFrame(splitPaneBoardFields);
    }

    private JPanel getBoard(){
        ActionListener actionListener;
        JPanel board = new JPanel(new BorderLayout(2,2));
        board.setBorder(new EmptyBorder(4,4,4,4));
        board.add(output,BorderLayout.PAGE_END);
        JPanel gameContainer = new JPanel(new GridLayout(0,size,2,2));
        board.add(gameContainer);
        actionListener = e -> output.setText(getButtonRowCol((JButton)e.getSource()));
        for (int ii=0; ii<size*size; ii++) {
            JButton b = getButton(iconSize, actionListener);
            gameContainer.add(b);
            buttonArray[ii%size][ii/size] = b;
        }
        return board;
    }

    private void action(Color red1, Color red2, Color blue1, Color blue2, Color player1, Color player2){
        redButton1.setBackground(red1);
        redButton2.setBackground(red2);
        blueButton1.setBackground(blue1);
        blueButton2.setBackground(blue2);
        playerColor1 = player1;
        playerColor2 = player2;
    }

    private void disableAll(){
        textField1.setEnabled(false);
        textField2.setEnabled(false);
        redButton1.setEnabled(false);
        redButton2.setEnabled(false);
        blueButton1.setEnabled(false);
        blueButton2.setEnabled(false);
        confirmButton.setEnabled(false);
        player1 = new Player(playerName1, playerColor1);
        player2 = new Player(playerName1, playerColor2);
    }

    private String getButtonRowCol(JButton button) {
        StringBuilder sb = new StringBuilder();
        for (int xx=0; xx<size; xx++) {
            for (int yy=0; yy<size; yy++) {
                if (button.equals(buttonArray[xx][yy])) {
                    sb.append("User selected button at: ");
                    sb.append(xx+1);
                    sb.append(",");
                    sb.append(yy+1);
                    break;
                }
            }
        }
        return sb.toString();
    }

    public static JFrame getMainFrame(JSplitPane splitPane){
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

    public JButton getButton(int iconSize, ActionListener actionListener) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_ARGB)));
        button.setRolloverIcon(new ImageIcon(new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_RGB)));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setContentAreaFilled(false);
        button.addActionListener(actionListener);
        return button;
    }

    public JPanel getJpanel(){
        JPanel panel = new JPanel();
        panel.setSize(400, 25);
        panel.setBackground(Color.WHITE);
        panel.setVisible(true);
        panel.setBorder(BorderFactory.createTitledBorder("Chart"));
        panel.add(new JLabel("Player_1: 100 Cells\n", SwingConstants.LEFT));
        return panel;
    }
}