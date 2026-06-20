package Linear;

import javax.swing.*;
import java.awt.*;

public class LinearFrame extends JFrame {

    private JTextField x1Field, y1Field, x2Field, y2Field;
    private LinearPanel linearPanel;

    public LinearFrame() {
        setTitle("DDA Line Drawing");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        linearPanel = new LinearPanel();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 1));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Visualisasi Algoritma DDA Line Drawing");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);

        JPanel titikPanel = new JPanel();

        titikPanel.add(new JLabel("x1:"));
        x1Field = new JTextField(6);
        titikPanel.add(x1Field);

        titikPanel.add(new JLabel("y1:"));
        y1Field = new JTextField(6);
        titikPanel.add(y1Field);

        titikPanel.add(new JLabel("x2:"));
        x2Field = new JTextField(6);
        titikPanel.add(x2Field);

        titikPanel.add(new JLabel("y2:"));
        y2Field = new JTextField(6);
        titikPanel.add(y2Field);

        JPanel buttonPanel = new JPanel();

        JButton gambarButton = new JButton("Gambar Garis");
        JButton clearButton = new JButton("Clear");

        buttonPanel.add(gambarButton);
        buttonPanel.add(clearButton);

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("Input titik awal dan titik akhir garis");
        infoPanel.add(infoLabel);

        inputPanel.add(titlePanel);
        inputPanel.add(titikPanel);
        inputPanel.add(buttonPanel);
        inputPanel.add(infoPanel);

        add(inputPanel, BorderLayout.NORTH);
        add(linearPanel, BorderLayout.CENTER);

        gambarButton.addActionListener(e -> prosesGaris());
        clearButton.addActionListener(e -> clearCanvas());
    }

    private void prosesGaris() {
        try {
            double x1 = Double.parseDouble(x1Field.getText());
            double y1 = Double.parseDouble(y1Field.getText());
            double x2 = Double.parseDouble(x2Field.getText());
            double y2 = Double.parseDouble(y2Field.getText());

            double[][] titikDDA = DDA.hitungDDA(x1, y1, x2, y2);

            linearPanel.setData(x1, y1, x2, y2, titikDDA);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Semua input harus diisi dengan angka!");
        }
    }

    private void clearCanvas() {
        x1Field.setText("");
        y1Field.setText("");
        x2Field.setText("");
        y2Field.setText("");

        linearPanel.clearData();
    }
}