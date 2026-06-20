package Curve;

import javax.swing.*;
import java.awt.*;

public class CurveFrame extends JFrame {

    private JTextField x0Field, y0Field;
    private JTextField x1Field, y1Field;
    private JTextField x2Field, y2Field;
    private JTextField x3Field, y3Field;

    private CurvePanel curvePanel;

    public CurveFrame() {
        setTitle("Bezier Curve");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        curvePanel = new CurvePanel();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 1));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Visualisasi Cubic Bezier Curve");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);

        JPanel p0p1Panel = new JPanel();

        p0p1Panel.add(new JLabel("A: x:"));
        x0Field = new JTextField(6);
        p0p1Panel.add(x0Field);

        p0p1Panel.add(new JLabel("y:"));
        y0Field = new JTextField(6);
        p0p1Panel.add(y0Field);

        p0p1Panel.add(new JLabel("B: x:"));
        x1Field = new JTextField(6);
        p0p1Panel.add(x1Field);

        p0p1Panel.add(new JLabel("y:"));
        y1Field = new JTextField(6);
        p0p1Panel.add(y1Field);

        JPanel p2p3Panel = new JPanel();

        p2p3Panel.add(new JLabel("C: x:"));
        x2Field = new JTextField(6);
        p2p3Panel.add(x2Field);

        p2p3Panel.add(new JLabel("y:"));
        y2Field = new JTextField(6);
        p2p3Panel.add(y2Field);

        p2p3Panel.add(new JLabel("D: x:"));
        x3Field = new JTextField(6);
        p2p3Panel.add(x3Field);

        p2p3Panel.add(new JLabel("y:"));
        y3Field = new JTextField(6);
        p2p3Panel.add(y3Field);

        JPanel buttonPanel = new JPanel();

        JButton gambarButton = new JButton("Gambar Curve");
        JButton clearButton = new JButton("Clear");

        buttonPanel.add(gambarButton);
        buttonPanel.add(clearButton);

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("Input 4 titik kontrol: P0, P1, P2, P3");
        infoPanel.add(infoLabel);

        inputPanel.add(titlePanel);
        inputPanel.add(p0p1Panel);
        inputPanel.add(p2p3Panel);
        inputPanel.add(buttonPanel);
        inputPanel.add(infoPanel);

        add(inputPanel, BorderLayout.NORTH);
        add(curvePanel, BorderLayout.CENTER);

        gambarButton.addActionListener(e -> prosesCurve());
        clearButton.addActionListener(e -> clearCanvas());
    }

    private void prosesCurve() {
        try {
            double x0 = Double.parseDouble(x0Field.getText());
            double y0 = Double.parseDouble(y0Field.getText());

            double x1 = Double.parseDouble(x1Field.getText());
            double y1 = Double.parseDouble(y1Field.getText());

            double x2 = Double.parseDouble(x2Field.getText());
            double y2 = Double.parseDouble(y2Field.getText());

            double x3 = Double.parseDouble(x3Field.getText());
            double y3 = Double.parseDouble(y3Field.getText());

            double[][] titikKontrol = {
                    {x0, y0},
                    {x1, y1},
                    {x2, y2},
                    {x3, y3}
            };

            double[][] titikCurve = BezierCurve.hitungBezier(titikKontrol);

            curvePanel.setData(titikKontrol, titikCurve);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Semua input harus diisi dengan angka!");
        }
    }

    private void clearCanvas() {
        x0Field.setText("");
        y0Field.setText("");
        x1Field.setText("");
        y1Field.setText("");
        x2Field.setText("");
        y2Field.setText("");
        x3Field.setText("");
        y3Field.setText("");

        curvePanel.clearData();
    }
}