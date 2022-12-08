import Gui.Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        Runnable r = () -> new Main();
        SwingUtilities.invokeLater(r);
    }

    // TODO: farbe assignen via coordinates, Adrian
    // TODO: dashboard statistik neben Grid, mit fields die assignt werden können, Adrian

    // TODO: User Input at beginning, Color and Name, Sandrin

    private int size = 15;
    private int iconSize = 10;
    private JButton[][] buttonArray = new JButton[size][size];
    private ActionListener actionListener;
    private JLabel output = new JLabel("Click somewhere on the GUI");
    JTextField textField1 = new JTextField("Player_1");
    JTextField textField2 = new JTextField("Player_2");
    JButton confirmButton = new JButton("Confirm");
    JButton redButton1 = Gui.getButton(iconSize, actionListener);

    JButton blueButton1 = Gui.getButton(iconSize, actionListener);

    JButton redButton2 = Gui.getButton(iconSize, actionListener);
    JButton greenButton2 = Gui.getButton(iconSize, actionListener);
    JButton blueButton2 = Gui.getButton(iconSize, actionListener);
    String playerName1 = new String();
    String playerName2 = new String();

    Main() {

        redButton1.setBackground(new Color(222, 23, 56));
        redButton1.setOpaque(true);
        redButton1.setBorderPainted(false);
        redButton2.setBackground(new Color(222, 23, 56));
        redButton2.setOpaque(true);
        redButton2.setBorderPainted(false);

        blueButton1.setBackground(new Color(51, 102, 153));
        blueButton1.setOpaque(true);
        blueButton1.setBorderPainted(false);
        blueButton2.setBackground(new Color(51, 102, 153));
        blueButton2.setOpaque(true);
        blueButton2.setBorderPainted(false);



        confirmButton.addActionListener(e -> {
            playerName1 = textField1.getText().toString();
            playerName2 = textField2.getText().toString();
            textField1.setEnabled(false);
            textField2.setEnabled(false);
            redButton1.setEnabled(false);
            redButton2.setEnabled(false);
            blueButton1.setEnabled(false);
            blueButton2.setEnabled(false);
            confirmButton.setEnabled(false);
        });
        JPanel chart = Gui.getJpanel();
        JPanel board = getBoard();

        JSplitPane splitPaneChartBoard =  Gui.getSplitPaneVertical(400, 450, 50, chart, board);

        JSplitPane colorDual1 = Gui.getSplitPaneHorizontal(400, 30, 100, redButton1, blueButton1);
        JSplitPane colorDual2 = Gui.getSplitPaneHorizontal(400, 30, 100, redButton2, blueButton2);
        JSplitPane splitPaneTextFieldsColor1 = Gui.getSplitPaneVertical(400, 60, 30, textField1, colorDual1);
        JSplitPane splitPaneTextFieldsColor2 = Gui.getSplitPaneVertical(400, 60, 30, textField2, colorDual2);
        JSplitPane splitPaneTextFields = Gui.getSplitPaneHorizontal(400, 60, 200, splitPaneTextFieldsColor1, splitPaneTextFieldsColor2);
        JSplitPane splitPaneButtonText = Gui.getSplitPaneVertical(400, 90, 60, splitPaneTextFields, confirmButton);
        JSplitPane splitPaneBoardFields = Gui.getSplitPaneVertical(400, 700, 450, splitPaneChartBoard, splitPaneButtonText);
        JFrame frame = Gui.getMainFrame(splitPaneBoardFields);

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
            JButton b = Gui.getButton(iconSize, actionListener);
            gameContainer.add(b);
            buttonArray[ii%size][ii/size] = b;
        }
        return board;
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
}