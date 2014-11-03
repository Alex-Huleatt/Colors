/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ColorPicker.Tools;

import struct.Delta;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class Pencil implements Tool {

    @Override
    public ArrayList<Delta> apply(Color c, int x, int y, Color[][] colorArr) {
        ArrayList<Delta> d = new ArrayList<>();
        if (x < colorArr.length && x >= 0 && y < colorArr[x].length && y >= 0) {
            d.add(new Delta(x,y,colorArr[x][y],c));
            colorArr[x][y] = c;
        }
        return d;
    }
    
    @Override
    public void paintSelf(Graphics g, int pixelSize) { }

}
