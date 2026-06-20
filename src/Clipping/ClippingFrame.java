package Clipping;

import javax.swing.*;
import java.awt.*;

public class ClippingFrame extends JFrame {

    private JTextField x1Field, y1Field, x2Field, y2Field;
    private JTextField xminField, yminField, xmaxField, ymaxField;
    private ClippingPanel clippingPanel;

    public ClippingFrame() {
        setTitle("Cohen-Sutherland Line Clipping");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        clippingPanel = new ClippingPanel();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 1));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Visualisasi Cohen-Sutherland Line Clipping");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);

        JPanel linePanel = new JPanel();

        linePanel.add(new JLabel("x1:"));
        x1Field = new JTextField(6);
        linePanel.add(x1Field);

        linePanel.add(new JLabel("y1:"));
        y1Field = new JTextField(6);
        linePanel.add(y1Field);

        linePanel.add(new JLabel("x2:"));
        x2Field = new JTextField(6);
        linePanel.add(x2Field);

        linePanel.add(new JLabel("y2:"));
        y2Field = new JTextField(6);
        linePanel.add(y2Field);

        JPanel clipPanel = new JPanel();

        clipPanel.add(new JLabel("xmin:"));
        xminField = new JTextField(6);
        clipPanel.add(xminField);

        clipPanel.add(new JLabel("ymin:"));
        yminField = new JTextField(6);
        clipPanel.add(yminField);

        clipPanel.add(new JLabel("xmax:"));
        xmaxField = new JTextField(6);
        clipPanel.add(xmaxField);

        clipPanel.add(new JLabel("ymax:"));
        ymaxField = new JTextField(6);
        clipPanel.add(ymaxField);

        JPanel buttonPanel = new JPanel();

        JButton processButton = new JButton("Proses Clipping");
        JButton clearButton = new JButton("Clear");

        buttonPanel.add(processButton);
        buttonPanel.add(clearButton);

        inputPanel.add(titlePanel);
        inputPanel.add(linePanel);
        inputPanel.add(clipPanel);
        inputPanel.add(buttonPanel);

        add(inputPanel, BorderLayout.NORTH);
        add(clippingPanel, BorderLayout.CENTER);

        processButton.addActionListener(e -> prosesClipping());
        clearButton.addActionListener(e -> clearCanvas());
    }

    private void prosesClipping() {
        try {
            double x1 = Double.parseDouble(x1Field.getText());
            double y1 = Double.parseDouble(y1Field.getText());
            double x2 = Double.parseDouble(x2Field.getText());
            double y2 = Double.parseDouble(y2Field.getText());

            double xmin = Double.parseDouble(xminField.getText());
            double ymin = Double.parseDouble(yminField.getText());
            double xmax = Double.parseDouble(xmaxField.getText());
            double ymax = Double.parseDouble(ymaxField.getText());

            if (xmin >= xmax || ymin >= ymax) {
                JOptionPane.showMessageDialog(this, "Nilai xmin harus < xmax dan ymin harus < ymax");
                return;
            }

            double[] hasilClipping = CohenSutherland.clip(
                    x1, y1, x2, y2,
                    xmin, ymin, xmax, ymax
            );

            clippingPanel.setData(
                    x1, y1, x2, y2,
                    xmin, ymin, xmax, ymax,
                    hasilClipping
            );

            if (hasilClipping == null) {
                JOptionPane.showMessageDialog(this, "Garis ditolak karena berada di luar area clipping");
            } else {
                JOptionPane.showMessageDialog(this, "Garis berhasil dipotong / diterima");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Semua input harus diisi dengan angka!");
        }
    }

    private void clearCanvas() {
        x1Field.setText("");
        y1Field.setText("");
        x2Field.setText("");
        y2Field.setText("");

        xminField.setText("");
        yminField.setText("");
        xmaxField.setText("");
        ymaxField.setText("");

        clippingPanel.clearData();
    }
}