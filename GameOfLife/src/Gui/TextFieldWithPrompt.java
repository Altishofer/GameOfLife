package Gui;

import javax.swing.*;
import java.awt.*;

public class TextFieldWithPrompt extends JTextField {

    private String aPlaceholder;
    private int aPlaceHolderPosX;
    private int aPlaceHolderPosY;

    public TextFieldWithPrompt(String pPlaceholder, int pPlaceHolderPosX, int pPlaceHolderPosY) {
        aPlaceholder = pPlaceholder;
        aPlaceHolderPosX = pPlaceHolderPosX;
        aPlaceHolderPosY = pPlaceHolderPosY;
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        if (getText().isEmpty() && !(FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.drawString(aPlaceholder, aPlaceHolderPosX, aPlaceHolderPosY); //figure out x, y from font's FontMetrics and size of component.
            g2.dispose();
        }
    }
}