package Linear;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class LinearPanel extends JPanel {

    private double x1, y1, x2, y2;
    private double[][] titikDDA;
    private boolean adaData = false;

    private double minViewX, maxViewX, minViewY, maxViewY;
    private double scale;
    private int margin = 80;

    private DecimalFormat df = new DecimalFormat("#.##");

    public LinearPanel() {
        setBackground(Color.WHITE);
    }

    public void setData(double x1, double y1, double x2, double y2, double[][] titikDDA) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.titikDDA = titikDDA;
        this.adaData = true;

        repaint();
    }

    public void clearData() {
        this.titikDDA = null;
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
            g2.drawString("Masukkan titik awal dan titik akhir lalu klik tombol Gambar Garis", 220, 250);
            return;
        }

        hitungSkala();

        gambarGridDanSumbu(g2);

        g2.setColor(Color.BLACK);
        g2.drawString("Merah = Titik awal dan titik akhir", 20, 25);
        g2.drawString("Biru = Garis hasil algoritma DDA", 20, 45);
        g2.drawString("Hitam kecil = Titik-titik hasil perhitungan DDA", 20, 65);

        // Gambar garis DDA
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(3));

        for (int i = 0; i < titikDDA.length - 1; i++) {
            g2.drawLine(
                    toScreenX(titikDDA[i][0]),
                    toScreenY(titikDDA[i][1]),
                    toScreenX(titikDDA[i + 1][0]),
                    toScreenY(titikDDA[i + 1][1])
            );
        }

        // Gambar titik-titik DDA
        g2.setColor(Color.BLACK);

        for (int i = 0; i < titikDDA.length; i++) {
            int sx = toScreenX(titikDDA[i][0]);
            int sy = toScreenY(titikDDA[i][1]);

            g2.fillOval(sx - 2, sy - 2, 4, 4);
        }

        // Gambar titik awal dan akhir
        g2.setColor(Color.RED);
        gambarTitik(g2, x1, y1, "A");
        gambarTitik(g2, x2, y2, "B");

        g2.setColor(Color.BLACK);
        g2.drawString(
                "Jumlah titik DDA: " + titikDDA.length,
                20,
                getHeight() - 45
        );

        g2.drawString(
                "dx = " + df.format(x2 - x1) +
                        ", dy = " + df.format(y2 - y1) +
                        ", steps = " + (titikDDA.length - 1),
                20,
                getHeight() - 25
        );
    }

    private void hitungSkala() {
        minViewX = Math.min(x1, x2);
        maxViewX = Math.max(x1, x2);
        minViewY = Math.min(y1, y2);
        maxViewY = Math.max(y1, y2);

        for (int i = 0; i < titikDDA.length; i++) {
            minViewX = Math.min(minViewX, titikDDA[i][0]);
            maxViewX = Math.max(maxViewX, titikDDA[i][0]);
            minViewY = Math.min(minViewY, titikDDA[i][1]);
            maxViewY = Math.max(maxViewY, titikDDA[i][1]);
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

        g2.fillOval(sx - 6, sy - 6, 12, 12);
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