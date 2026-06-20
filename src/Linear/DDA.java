package Linear;

public class DDA {

    public static double[][] hitungDDA(double x1, double y1, double x2, double y2) {

        double dx = x2 - x1;
        double dy = y2 - y1;

        int steps = (int) Math.max(Math.abs(dx), Math.abs(dy));

        if (steps == 0) {
            return new double[][]{
                    {x1, y1}
            };
        }

        double xIncrement = dx / steps;
        double yIncrement = dy / steps;

        double[][] hasil = new double[steps + 1][2];

        double x = x1;
        double y = y1;

        for (int i = 0; i <= steps; i++) {
            hasil[i][0] = Math.round(x);
            hasil[i][1] = Math.round(y);

            x += xIncrement;
            y += yIncrement;
        }

        return hasil;
    }
}