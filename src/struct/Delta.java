/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package struct;

import java.awt.Color;
import java.util.Arrays;

/**
 *
 * @author Alex
 */
public class Delta {

    private int x;
    private int y;
    private int[] delta;

    public Delta(int x, int y, int[] delta) {
        this.x = x;
        this.y = y;
        this.delta = delta;
    }

    public Delta(int x, int y, Color old, Color newC) {
        this.x = x;
        this.y = y;
        if (old == null) {
            old = Color.WHITE;
        }
        this.delta = new int[]{old.getRed() - newC.getRed(), old.getGreen() - newC.getGreen(), old.getBlue() - newC.getBlue(), old.getAlpha() - newC.getAlpha()};
    }

    public void undo(Color[][] colorArr) {
        Color c = colorArr[x][y];
        int r = c.getRed() + delta[0];
        int g =  c.getGreen() + delta[1];
        int b =  c.getBlue() + delta[2];
        int a = c.getAlpha() + delta[3];
        System.out.println(r + " " + g + " "+ b + " "+ a);
        colorArr[x][y] = new Color(c.getRed() + delta[0], c.getGreen() + delta[1], c.getBlue() + delta[2], c.getAlpha() + delta[3]);
    }

    public void unundo(Color[][] colorArr) {
        Color c = colorArr[x][y];
        colorArr[x][y] = new Color(c.getRed() - delta[0], c.getGreen() - delta[1], c.getBlue() - delta[2], c.getAlpha() - delta[3]);
    }

    public boolean amUseless() {
        for (int i = 0; i < delta.length; i++) {
            if (delta[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return "(" + x + "," + y + ")" + ":" + Arrays.toString(delta);
    }
}
