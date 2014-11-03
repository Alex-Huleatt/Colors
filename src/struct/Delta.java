/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package struct;

import java.awt.Color;

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
        this.delta = new int[] {old.getRed() - newC.getRed(), old.getGreen() - newC.getGreen(), old.getBlue() - newC.getBlue()};
    }
    
    
    public void undo(Color[][] colorArr) {
        Color c = colorArr[x][y];
        colorArr[x][y] = new Color(c.getRed()-delta[0],c.getGreen()-delta[1],c.getBlue()-delta[2]);
    }
}
