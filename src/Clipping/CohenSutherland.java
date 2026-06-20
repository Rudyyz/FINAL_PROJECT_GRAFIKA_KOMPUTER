package Clipping;

public class CohenSutherland {

    private static final int INSIDE = 0;
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int BOTTOM = 4;
    private static final int TOP = 8;

    private static double xmin, ymin, xmax, ymax;

    private static int computeCode(double x, double y) {
        int code = INSIDE;

        if (x < xmin) {
            code |= LEFT;
        } else if (x > xmax) {
            code |= RIGHT;
        }

        if (y < ymin) {
            code |= TOP;
        } else if (y > ymax) {
            code |= BOTTOM;
        }

        return code;
    }

    public static double[] clip(double x1, double y1, double x2, double y2,
                                double clipXmin, double clipYmin,
                                double clipXmax, double clipYmax) {

        xmin = clipXmin;
        ymin = clipYmin;
        xmax = clipXmax;
        ymax = clipYmax;

        int code1 = computeCode(x1, y1);
        int code2 = computeCode(x2, y2);

        boolean accept = false;

        while (true) {

            // Jika kedua titik berada di dalam area clipping
            if ((code1 | code2) == 0) {
                accept = true;
                break;
            }

            // Jika kedua titik berada di luar area yang sama
            else if ((code1 & code2) != 0) {
                break;
            }

            // Jika sebagian garis berada di dalam dan sebagian di luar
            else {
                double x = 0;
                double y = 0;

                int codeOut;

                if (code1 != 0) {
                    codeOut = code1;
                } else {
                    codeOut = code2;
                }

                if ((codeOut & TOP) != 0) {
                    x = x1 + (x2 - x1) * (ymin - y1) / (y2 - y1);
                    y = ymin;
                } else if ((codeOut & BOTTOM) != 0) {
                    x = x1 + (x2 - x1) * (ymax - y1) / (y2 - y1);
                    y = ymax;
                } else if ((codeOut & RIGHT) != 0) {
                    y = y1 + (y2 - y1) * (xmax - x1) / (x2 - x1);
                    x = xmax;
                } else if ((codeOut & LEFT) != 0) {
                    y = y1 + (y2 - y1) * (xmin - x1) / (x2 - x1);
                    x = xmin;
                }

                if (codeOut == code1) {
                    x1 = x;
                    y1 = y;
                    code1 = computeCode(x1, y1);
                } else {
                    x2 = x;
                    y2 = y;
                    code2 = computeCode(x2, y2);
                }
            }
        }

        if (accept) {
            return new double[]{x1, y1, x2, y2};
        } else {
            return null;
        }
    }
}