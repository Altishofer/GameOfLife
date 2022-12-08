package Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public abstract class Gui {

    private static JSplitPane getSplitPaneHorizontal(int width, int height, int divider){
        JSplitPane splitPane = new JSplitPane();
        splitPane.setSize(width, height);
        splitPane.setDividerSize(0);
        splitPane.setDividerLocation(divider);
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        return splitPane;
    }

    private static JSplitPane getSplitPaneVertical(int width, int height, int divider){
        JSplitPane splitPane = new JSplitPane();
        splitPane.setSize(width, height);
        splitPane.setDividerSize(0);
        splitPane.setDividerLocation(divider);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        return splitPane;
    }

    public static JSplitPane getSplitPaneHorizontal(int width, int height, int divider, JSplitPane left, JSplitPane right){
        JSplitPane splitPane = getSplitPaneHorizontal(width, height, divider);
        splitPane.setLeftComponent(left);
        splitPane.setRightComponent(right);
        return splitPane;
    }

    public static JSplitPane getSplitPaneVertical(int width, int height, int divider, JPanel top, JPanel bottom){
        JSplitPane splitPane = getSplitPaneVertical(width, height, divider);
        splitPane.setTopComponent(top);
        splitPane.setBottomComponent(bottom);
        return splitPane;
    }

    public static JSplitPane getSplitPaneVertical(int width, int height, int divider, JSplitPane top, JButton bottom){
        JSplitPane splitPane = getSplitPaneVertical(width, height, divider);
        splitPane.setTopComponent(top);
        splitPane.setBottomComponent(bottom);
        return splitPane;
    }

    public static JSplitPane getSplitPaneVertical(int width, int height, int divider, JSplitPane top, JSplitPane bottom){
        JSplitPane splitPane = getSplitPaneVertical(width, height, divider);
        splitPane.setTopComponent(top);
        splitPane.setBottomComponent(bottom);
        return splitPane;
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

    public static JButton getButton(int iconSize, ActionListener actionListener) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(new BufferedImage(iconSize,iconSize,BufferedImage.TYPE_INT_ARGB)));
        button.setRolloverIcon(new ImageIcon(new BufferedImage(iconSize,iconSize,BufferedImage.TYPE_INT_RGB)));
        button.setMargin(new Insets(0,0,0,0));
        //button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(actionListener);
        return button;
    }

    public static JButton getButton(int iconSize, ActionListener actionListener, Color color){
        JButton button = new JButton();
        button.setIcon(new ImageIcon(new BufferedImage(iconSize,iconSize,BufferedImage.TYPE_INT_ARGB)));
        button.setRolloverIcon(new ImageIcon(new BufferedImage(iconSize,iconSize,BufferedImage.TYPE_INT_RGB)));
        button.setMargin(new Insets(0,0,0,0));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setBackground(color);
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    public static JPanel getJpanel(){
        JPanel panel = new JPanel();
        panel.setSize(400, 25);
        panel.setBackground(Color.WHITE);
        panel.setVisible(true);
        panel.setBorder(BorderFactory.createTitledBorder("Chart"));
        panel.add(new JLabel("Player_1: 100 Cells\n", SwingConstants.LEFT));
        return panel;
    }

    public static JSplitPane getSplitPaneHorizontal(int width, int height, int divider, JButton left, JButton right) {
        JSplitPane splitPane = getSplitPaneHorizontal(width, height, divider);
        splitPane.setLeftComponent(left);
        splitPane.setRightComponent(right);
        return splitPane;
    }

    public static JSplitPane getSplitPaneVertical(int width, int height, int divider, JTextField top, JSplitPane bottom) {
        JSplitPane splitPane = getSplitPaneVertical(width, height, divider);
        splitPane.setTopComponent(top);
        splitPane.setBottomComponent(bottom);
        return splitPane;
    }
}
