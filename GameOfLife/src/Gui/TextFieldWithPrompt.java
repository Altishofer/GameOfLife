package Gui;

import javax.swing.*;
import java.awt.*;

public class TextFieldWithPrompt extends JTextField {

    private String placeholder;
    private int placeHolderPosX;
    private int placeHolderPosY;


    public TextFieldWithPrompt(String pPlaceholder, int pPlaceHolderPosX, int pPlaceHolderPosY) {
        placeholder = pPlaceholder;
        placeHolderPosX = pPlaceHolderPosX;
        placeHolderPosY = pPlaceHolderPosY;
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        if (getText().isEmpty() && !(FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)) {
            Graphics2D g2 = (Graphics2D) g.create();
            // g2.setBackground(Color.gray);
            // g2.setFont(getFont().deriveFont(Font.ITALIC));
            g2.drawString(placeholder, placeHolderPosX, placeHolderPosY); //figure out x, y from font's FontMetrics and size of component.
            g2.dispose();
        }
    }

    public void setPlaceholder() {

    }

    public void removePrompt() {

    }
}