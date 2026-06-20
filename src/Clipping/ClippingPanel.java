package Clipping;

import javax.swing.*;
import java.awt.*;

public class ClippingPanel extends JPanel {

    private double x1, y1, x2, y2;
    private double xmin, ymin, xmax, ymax;
    private double[] hasilClipping;

    private boolean adaData = false;

    public ClippingPanel() {
        setBackground(Color.WHITE);
    }

    public void setData(double x1, double y1, double x2, double y2,
                        double xmin, double ymin, double xmax, double ymax,
                        double[] hasilClipping) {

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;

        this.hasilClipping = hasilClipping;
        this.adaData = true;

        repaint();
    }

    public void clearData() {
        adaData = false;
        hasilClipping = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        if (!adaData) {
            g2.setColor(Color.BLACK);
            g2.drawString("Masukkan data lalu klik tombol Proses Clipping", 270, 250);
            return;
        }

        // Gambar kotak clipping
        g2.setColor(Color.BLUE);
        int rectX = (int) xmin;
        int rectY = (int) ymin;
        int rectWidth = (int) (xmax - xmin);
        int rectHeight = (int) (ymax - ymin);

        g2.drawRect(rectX, rectY, rectWidth, rectHeight);
        g2.drawString("Area Clipping", rectX, rectY - 10);

        // Gambar garis awal sebelum clipping
        g2.setColor(Color.RED);
        g2.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        g2.drawString("Garis Awal", (int) x1, (int) y1 - 10);

        // Gambar titik awal dan akhir
        g2.fillOval((int) x1 - 4, (int) y1 - 4, 8, 8);
        g2.fillOval((int) x2 - 4, (int) y2 - 4, 8, 8);

        g2.drawString("A(" + (int) x1 + "," + (int) y1 + ")", (int) x1 + 5, (int) y1 + 15);
        g2.drawString("B(" + (int) x2 + "," + (int) y2 + ")", (int) x2 + 5, (int) y2 + 15);

        // Jika garis diterima / berhasil dipotong
        if (hasilClipping != null) {
            double cx1 = hasilClipping[0];
            double cy1 = hasilClipping[1];
            double cx2 = hasilClipping[2];
            double cy2 = hasilClipping[3];

            // Gambar hasil clipping
            g2.setColor(Color.GREEN);
            g2.setStroke(new BasicStroke(4));
            g2.drawLine((int) cx1, (int) cy1, (int) cx2, (int) cy2);

            // Titik hasil clipping
            g2.fillOval((int) cx1 - 5, (int) cy1 - 5, 10, 10);
            g2.fillOval((int) cx2 - 5, (int) cy2 - 5, 10, 10);

            g2.setColor(Color.BLACK);
            g2.drawString("Hasil Clipping", (int) cx1, (int) cy1 - 20);
            g2.drawString("P1(" + (int) cx1 + "," + (int) cy1 + ")", (int) cx1 + 8, (int) cy1);
            g2.drawString("P2(" + (int) cx2 + "," + (int) cy2 + ")", (int) cx2 + 8, (int) cy2);

            // Keterangan
            g2.drawString("Merah  = Garis sebelum clipping", 20, 25);
            g2.drawString("Biru   = Area clipping", 20, 45);
            g2.drawString("Hijau  = Garis hasil clipping", 20, 65);

        } else {
            g2.setColor(Color.BLACK);
            g2.drawString("Garis ditolak karena berada di luar area clipping", 20, 25);
            g2.drawString("Merah = Garis awal", 20, 45);
            g2.drawString("Biru  = Area clipping", 20, 65);
        }
    }
}