import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class GUI extends JFrame implements ActionListener {

    // TODO: assign colour after turns (via coords), Adrian
    // TODO: dashboard statistic: generations, cells of each player: content should be updatable after each turn, Adrian

    int size = 15;
    int iconSize = 10;

    JButton[][] buttonArray = new JButton[size][size];
    ActionListener actionListener; // Should we implement this differently?
    JLabel output = new JLabel("Click somewhere on the GUI");

    GUI() {
        setName("Game of Life");
        setPreferredSize(new Dimension(400, 600));
        getContentPane().setLayout(new GridLayout());

        JLabel statsP1 = new JLabel();
        statsP1.setText("Player_1: 100 Cells        ");
        statsP1.setVerticalAlignment(JLabel.TOP);

        JLabel statsP2 = new JLabel();
        statsP2.setText("        Player_1: 100 Cells\n");
        statsP2.setVerticalAlignment(JLabel.BOTTOM);

        JPanel chart = new JPanel();
        chart.setSize(400, 25);
        chart.setBackground(Color.WHITE);
        chart.setVisible(true);
        chart.setBorder(BorderFactory.createTitledBorder("Chart"));
        chart.add(statsP1);
        chart.add(statsP2);
        //chart.add(new JLabel("Player_1: 100 Cells       \n", SwingConstants.LEFT));
        //chart.add(new JLabel("       Player_2: 100 Cells\n", SwingConstants.RIGHT));

        JPanel board = new JPanel(new BorderLayout(2,2));
        board.setBorder(new EmptyBorder(4,4,4,4));
        output.setHorizontalAlignment(JLabel.CENTER);
        board.add(output,BorderLayout.PAGE_END);
        JPanel gameContainer = new JPanel(new GridLayout(0,size,2,2));
        board.add(gameContainer);
        actionListener = e -> output.setText(getButtonRowCol((JButton)e.getSource()));
        for (int ii=0; ii<size*size; ii++) {
            JButton b = getButton();
            gameContainer.add(b);
            buttonArray[ii%size][ii/size] = b;
        }

        JSplitPane splitPane1 = new JSplitPane();
        splitPane1.setSize(400, 600);
        splitPane1.setDividerSize(0);
        splitPane1.setDividerLocation(50);
        splitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane1.setTopComponent(chart);
        splitPane1.setBottomComponent(board);

        JTextField textField1 = new JTextField("Player_1");
        textField1.setHorizontalAlignment(JTextField.CENTER);

        JTextField textField2 = new JTextField("Player_2");
        textField2.setHorizontalAlignment(JTextField.CENTER);

        JButton button1 = new JButton("Confirm");

        JSplitPane splitPane3 = new JSplitPane();
        splitPane3.setSize(400, 50);
        splitPane3.setDividerSize(0);
        splitPane3.setDividerLocation(200);
        splitPane3.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane3.setLeftComponent(textField1);
        splitPane3.setRightComponent(textField2);

        JSplitPane splitPane2 = new JSplitPane();
        splitPane2.setSize(400, 100);
        splitPane2.setDividerSize(0);
        splitPane2.setDividerLocation(25);
        splitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane2.setTopComponent(splitPane3);
        splitPane2.setBottomComponent(button1);

        JSplitPane splitPane7 = new JSplitPane();
        splitPane7.setSize(400, 700);
        splitPane7.setDividerSize(0);
        splitPane7.setDividerLocation(500);
        splitPane7.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane7.setTopComponent(splitPane1);
        splitPane7.setBottomComponent(splitPane2);

        add(splitPane7);
        pack();
        setSize(400, 600);
        setMinimumSize(getSize());
        setMaximumSize(getSize());
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private String getButtonRowCol(JButton button) {
        String out = "";
        for (int xx=0; xx<size; xx++) {
            for (int yy=0; yy<size; yy++) {
                if (button.equals(buttonArray[xx][yy])) {
                    out = "User selected button at: " + Integer.toString(xx + 1) + ", " + Integer.toString(yy + 1);
                    break;
                }
            }
        }
        return out;
    }

    private JButton getButton() {
        JButton b = new JButton();
        b.setIcon(new ImageIcon(
                new BufferedImage(iconSize,iconSize,BufferedImage.TYPE_INT_ARGB)));
        b.setRolloverIcon(new ImageIcon(
                new BufferedImage(iconSize,iconSize,BufferedImage.TYPE_INT_RGB)));
        b.setMargin(new Insets(0,0,0,0));
        //b.setBorderPainted(false); //THIS ONE WAS DEACTIVATED
        b.setContentAreaFilled(false);
        b.addActionListener(actionListener);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
