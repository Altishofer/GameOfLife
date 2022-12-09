import Board.Player;
import Gui.GuiUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {
        Runnable r = () -> new Gui.GUI();
        SwingUtilities.invokeLater(r);
    }
}