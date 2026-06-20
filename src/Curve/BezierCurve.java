package Curve;

public class BezierCurve {

    public static double[][] hitungBezier(double[][] titikKontrol) {

        int jumlahTitik = 101;
        double[][] hasil = new double[jumlahTitik][2];

        double x0 = titikKontrol[0][0];
        double y0 = titikKontrol[0][1];

        double x1 = titikKontrol[1][0];
        double y1 = titikKontrol[1][1];

        double x2 = titikKontrol[2][0];
        double y2 = titikKontrol[2][1];

        double x3 = titikKontrol[3][0];
        double y3 = titikKontrol[3][1];

        for (int i = 0; i < jumlahTitik; i++) {
            double t = i / 100.0;

            double x = Math.pow(1 - t, 3) * x0
                    + 3 * Math.pow(1 - t, 2) * t * x1
                    + 3 * (1 - t) * Math.pow(t, 2) * x2
                    + Math.pow(t, 3) * x3;

            double y = Math.pow(1 - t, 3) * y0
                    + 3 * Math.pow(1 - t, 2) * t * y1
                    + 3 * (1 - t) * Math.pow(t, 2) * y2
                    + Math.pow(t, 3) * y3;

            hasil[i][0] = x;
            hasil[i][1] = y;
        }

        return hasil;
    }
}