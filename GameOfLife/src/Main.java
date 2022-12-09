import Gui.SingletonGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Runnable r = () -> SingletonGUI.getInstance();
        SwingUtilities.invokeLater(r);
    }
}

