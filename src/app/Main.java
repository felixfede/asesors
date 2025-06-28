package app;

import gui.LoginFrameV2;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrameV2().setVisible(true);
        });
    }
}