package Clipping;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClippingFrame().setVisible(true);
        });
    }
}