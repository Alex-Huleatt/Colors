/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colors;

import java.util.Arrays;

/**
 *
 * @author Alex
 */
public class Colors {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int r = 255;
        int g = 0;
        int b = 255;
        double[] arr = toHSL(r,g,b);
        System.out.println(Arrays.toString(arr));
        arr = toRGB(arr[0],arr[1],arr[2]);
        System.out.println(Arrays.toString(arr));
    }

    public static double[] toHSL(int r, int g, int b) {
        double rp = r;
        double gp = g;
        double bp = b;
        rp /= 255;
        gp /= 255;
        bp /= 255;
        double cmax = Math.max(rp, Math.max(gp, bp));
        double cmin = Math.min(rp, Math.min(gp, bp));
        double delta = cmax - cmin;
        System.out.println(cmax + " " + cmin + " " + delta);
        double hue;
        if (cmax == rp) {
            hue = 60 * (negMod((int) ((gp - bp) / delta), 6));
        } else if (cmax == gp) {
            hue = 60 * (((bp - rp) / delta) + 2);
        } else {
            hue = 60 * (((rp - gp) / delta) + 4);
        }
        double lightness = (cmax + cmin) / 2;
        double saturation = (delta == 0) ? 0 : (delta / (1 - Math.abs(2 * lightness - 1)));
        return new double[]{hue, saturation, lightness};
    }

    public static double[] toRGB(double h, double s, double l) {
        double c = (1 - Math.abs(2 * l - 1)) * s;
        double x = c * (1 - Math.abs((h / 60) % 2 - 1));
        double m = l - c / 2;
        double[] arr = new double[0];
        if (h < 60) {
            arr = new double[]{c, x, 0};
        } else if (h < 120) {
            arr = new double[]{x, c, 0};
        } else if (h < 180) {
            arr = new double[]{0, c, x};
        } else if (h < 240) {
            arr = new double[]{0, x, c};
        } else if (h < 300) {
            arr = new double[]{x, 0, c};
        } else if (h < 360) {
            arr = new double[]{c, 0, x};
        }
        for (int i = 0; i < 3; i++) {
            arr[i] += m;
            arr[i] *= 255;
        }
        return arr;
    }

    public static int negMod(int a, int mod) {
        return ((a % mod + mod) % mod);
    }

    public static double[] getColor(double n, double range) {
        //0 to range
        n = n % range;
        double mag = 255.0/2;
        System.out.println(n);
        //lightness goes from 0 to 100
        //each color then oscillates from -100 to 155
        double[] r = new double[3];
        //each color offset by 2pi/3 = 60 degrees
        //@n = 0 --> offset so that they are all -1 sin(3pi/2)
        //@n = range --> offset so that they are all 1 sin(pi/2)

        for (int i = 0; i < 3; i++) {
            r[i] = mag * Math.sin((n / (range / 3)) + ((i + 1) * (Math.PI * 2.0 / 3))) + mag - (range - n) / range * 255 / 2;
            if (r[i] < 0) {
                r[i] = 0;
            }
        }
        System.out.println(Arrays.toString(r));
        return r;
    }

    public static double[] getColor2(double n, double range) {
        n %= range;
        double[] r = new double[3];
        //1 @ n == 0.
        //0 @ n == range
        double GAMMA = .85;
        double mag = 255 / 2 - (255/2* (range - n)/range) * GAMMA;
        for (int i = 0; i < 3; i++) {
            double offset = i * Math.PI * 2.0/3;
            r[i] = mag * Math.sin((n / (range / 6)) + offset) + mag;
            if (r[i] < 0) {
                r[i] = 0;
            }
        }
        return r;

    }
}
