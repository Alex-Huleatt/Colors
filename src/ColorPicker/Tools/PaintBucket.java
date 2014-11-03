/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ColorPicker.Tools;


import struct.Point;
import struct.Delta;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class PaintBucket implements Tool {
    Point temp;
    @Override
    /**
     * This function has to apply some change to colorArr
     * Color c is curColor in GridPanel
     */
    public ArrayList<Delta> apply(Color c, int x, int y, Color[][] colorArr) {
        ArrayList<Delta> deltaMap = new ArrayList<>();
        bucketHelper(c,x,y,colorArr,deltaMap);   
        return deltaMap;
    }
    
    public void bucketHelper(Color c, int x, int y, Color[][] colorArr, ArrayList<Delta> deltaMap){
        Color curColor = colorArr[x][y]; 
        if(curColor.equals(c)) return;

        colorArr[x][y] = c;
        
        deltaMap.add(new Delta(x,y,curColor,c));
        
        if(x > 0 && curColor.equals(colorArr[x-1][y])) {
            bucketHelper(c,x - 1,y,colorArr,deltaMap);
        }
        if(x < colorArr[0].length - 1 && curColor.equals(colorArr[x+1][y])) {
            bucketHelper(c,x + 1,y,colorArr,deltaMap);
        }
        if(y > 0 && curColor.equals(colorArr[x][y-1])){
            bucketHelper(c,x,y - 1,colorArr,deltaMap);
        }
        if(y < colorArr.length - 1 && curColor.equals(colorArr[x][y+1])) {
            bucketHelper(c,x,y + 1,colorArr,deltaMap);
        }
    }

    @Override
    public void paintSelf(Graphics g, int pixelSize, Color[][] colorArr, Color curColor) {
        ArrayList<Delta> del = apply(curColor, temp.x, temp.y, colorArr);
        g.setColor(curColor);
        for (Delta d : del) {
            g.fillRect(d.x*pixelSize+1, d.y*pixelSize+1, pixelSize-1, pixelSize-1);
            d.undo(colorArr);
        }
        
    }

    @Override
    public void mouseMoved(Point p) {
        temp = p;
    }

    @Override
    public void mouseDragged(Point p) {
    }
}
