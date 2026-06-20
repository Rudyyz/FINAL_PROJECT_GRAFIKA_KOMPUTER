package Curve;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class CurvePanel extends JPanel {

    private double[][] titikKontrol;
    private double[][] titikCurve;
    private boolean adaData = false;

    private double minViewX, maxViewX, minViewY, maxViewY;
    private double scale;
    private int margin = 80;

    private DecimalFormat df = new DecimalFormat("#.##");

    public CurvePanel() {
        setBackground(Color.WHITE);
    }

    public void setData(double[][] titikKontrol, double[][] titikCurve) {
        this.titikKontrol = titikKontrol;
        this.titikCurve = titikCurve;
        this.adaData = true;
        repaint();
    }

    public void clearData() {
        this.titikKontrol = null;
        this.titikCurve = null;
        this.adaData = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        if (!adaData) {
            g2.setColor(Color.BLACK);
            g2.drawString("Masukkan 4 titik kontrol lalu klik tombol Gambar Curve", 250, 250);
            return;
        }

        hitungSkala();

        gambarGridDanSumbu(g2);

        g2.setColor(Color.BLACK);
        g2.drawString("Merah = Titik kontrol", 20, 25);
        g2.drawString("Abu-abu = Garis bantu antar titik kontrol", 20, 45);
        g2.drawString("Biru = Hasil Cubic Bezier Curve", 20, 65);

        // Gambar garis bantu antar titik kontrol
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1));

        for (int i = 0; i < titikKontrol.length - 1; i++) {
            g2.drawLine(
                    toScreenX(titikKontrol[i][0]),
                    toScreenY(titikKontrol[i][1]),
                    toScreenX(titikKontrol[i + 1][0]),
                    toScreenY(titikKontrol[i + 1][1])
            );
        }

        // Gambar kurva Bezier
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(3));

        for (int i = 0; i < titikCurve.length - 1; i++) {
            g2.drawLine(
                    toScreenX(titikCurve[i][0]),
                    toScreenY(titikCurve[i][1]),
                    toScreenX(titikCurve[i + 1][0]),
                    toScreenY(titikCurve[i + 1][1])
            );
        }

        // Gambar titik kontrol
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(2));

        String[] labelTitik = {"A", "B", "C", "D"};

        for (int i = 0; i < titikKontrol.length; i++) {
            gambarTitik(g2, titikKontrol[i][0], titikKontrol[i][1], labelTitik[i]);
        }

        g2.setColor(Color.BLACK);
        g2.drawString(
                "Cubic Bezier Curve dihitung dari parameter t = 0 sampai t = 1",
                20,
                getHeight() - 25
        );
    }

    private void hitungSkala() {
        minViewX = titikKontrol[0][0];
        maxViewX = titikKontrol[0][0];
        minViewY = titikKontrol[0][1];
        maxViewY = titikKontrol[0][1];

        for (int i = 0; i < titikKontrol.length; i++) {
            minViewX = Math.min(minViewX, titikKontrol[i][0]);
            maxViewX = Math.max(maxViewX, titikKontrol[i][0]);
            minViewY = Math.min(minViewY, titikKontrol[i][1]);
            maxViewY = Math.max(maxViewY, titikKontrol[i][1]);
        }

        for (int i = 0; i < titikCurve.length; i++) {
            minViewX = Math.min(minViewX, titikCurve[i][0]);
            maxViewX = Math.max(maxViewX, titikCurve[i][0]);
            minViewY = Math.min(minViewY, titikCurve[i][1]);
            maxViewY = Math.max(maxViewY, titikCurve[i][1]);
        }

        double rangeX = maxViewX - minViewX;
        double rangeY = maxViewY - minViewY;

        if (rangeX == 0) rangeX = 1;
        if (rangeY == 0) rangeY = 1;

        double paddingX = rangeX * 0.2;
        double paddingY = rangeY * 0.2;

        if (paddingX < 1) paddingX = 1;
        if (paddingY < 1) paddingY = 1;

        minViewX -= paddingX;
        maxViewX += paddingX;
        minViewY -= paddingY;
        maxViewY += paddingY;

        double availableWidth = getWidth() - 2.0 * margin;
        double availableHeight = getHeight() - 2.0 * margin;

        double scaleX = availableWidth / (maxViewX - minViewX);
        double scaleY = availableHeight / (maxViewY - minViewY);

        scale = Math.min(scaleX, scaleY);
    }

    private int toScreenX(double x) {
        return (int) (margin + (x - minViewX) * scale);
    }

    private int toScreenY(double y) {
        return (int) (getHeight() - margin - (y - minViewY) * scale);
    }

    private void gambarTitik(Graphics2D g2, double x, double y, String label) {
        int sx = toScreenX(x);
        int sy = toScreenY(y);

        g2.fillOval(sx - 5, sy - 5, 10, 10);
        g2.drawString(label + "(" + df.format(x) + "," + df.format(y) + ")", sx + 8, sy - 8);
    }

    private void gambarGridDanSumbu(Graphics2D g2) {
        double rangeX = maxViewX - minViewX;
        double rangeY = maxViewY - minViewY;

        double step = cariStep(Math.max(rangeX, rangeY));

        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(230, 230, 230));

        double startX = Math.ceil(minViewX / step) * step;
        for (double x = startX; x <= maxViewX; x += step) {
            int sx = toScreenX(x);
            g2.drawLine(sx, margin, sx, getHeight() - margin);
        }

        double startY = Math.ceil(minViewY / step) * step;
        for (double y = startY; y <= maxViewY; y += step) {
            int sy = toScreenY(y);
            g2.drawLine(margin, sy, getWidth() - margin, sy);
        }

        // Sumbu X jika y = 0 terlihat
        g2.setColor(Color.GRAY);
        if (minViewY <= 0 && maxViewY >= 0) {
            int sy = toScreenY(0);
            g2.drawLine(margin, sy, getWidth() - margin, sy);
            g2.drawString("Sumbu X", getWidth() - margin - 60, sy - 8);
        }

        // Sumbu Y jika x = 0 terlihat
        if (minViewX <= 0 && maxViewX >= 0) {
            int sx = toScreenX(0);
            g2.drawLine(sx, margin, sx, getHeight() - margin);
            g2.drawString("Sumbu Y", sx + 8, margin + 15);
        }

        // Angka koordinat X
        g2.setColor(Color.BLACK);
        for (double x = startX; x <= maxViewX; x += step) {
            int sx = toScreenX(x);
            int sy;

            if (minViewY <= 0 && maxViewY >= 0) {
                sy = toScreenY(0);
            } else {
                sy = getHeight() - margin;
            }

            g2.drawLine(sx, sy - 4, sx, sy + 4);
            g2.drawString(df.format(x), sx - 10, sy + 18);
        }

        // Angka koordinat Y
        for (double y = startY; y <= maxViewY; y += step) {
            int sy = toScreenY(y);
            int sx;

            if (minViewX <= 0 && maxViewX >= 0) {
                sx = toScreenX(0);
            } else {
                sx = margin;
            }

            g2.drawLine(sx - 4, sy, sx + 4, sy);
            g2.drawString(df.format(y), sx + 8, sy + 5);
        }
    }

    private double cariStep(double range) {
        if (range <= 10) {
            return 1;
        } else if (range <= 30) {
            return 2;
        } else if (range <= 60) {
            return 5;
        } else if (range <= 150) {
            return 10;
        } else if (range <= 300) {
            return 20;
        } else if (range <= 700) {
            return 50;
        } else {
            return 100;
        }
    }
}