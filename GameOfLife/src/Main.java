import Gui.Gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {
        Runnable r = () -> new Main();
        SwingUtilities.invokeLater(r);
    }

    // TODO: farbe assignen via coordinates, Adrian
    // TODO: dashboard statistik neben Grid, mit fields die assignt werden können, Adrian

    // TODO: User Input at beginning, Color and Name, Sandrin

    private int size = 20;
    private int iconSize = 10;
    private JButton[][] buttonArray = new JButton[size][size];
    private ActionListener actionListener;
    private JLabel output = new JLabel("Click somewhere on the GUI");
    JTextField textField1 = new JTextField("Player_1");
    JTextField textField2 = new JTextField("Player_2");
    JButton button1 = new JButton("Confirm");
    String playerName1 = new String();
    String playerName2 = new String();

    Main() {
        button1.addActionListener(e -> {
            playerName1 = textField1.getText().toString();
            playerName2 = textField2.getText().toString();
            textField1.setEnabled(false);
            textField2.setEnabled(false);
            button1.setEnabled(false);
        });
        JPanel chart = Gui.getJpanel();
        JPanel board = getBoard();

        JSplitPane splitPaneChartBoard =  Gui.getSplitPaneVertical(400, 600, 50, chart, board);
        JSplitPane splitPaneTextFields = Gui.getSplitPaneHorizontal(400, 50, 200, textField1, textField2);
        JSplitPane splitPaneButtonText = Gui.getSplitPaneVertical(400, 100, 25, splitPaneTextFields, button1);
        JSplitPane splitPaneBoardFields = Gui.getSplitPaneVertical(400, 700, 500, splitPaneChartBoard, splitPaneButtonText);
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