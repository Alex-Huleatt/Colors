/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ColorPicker.Tools;

import ColorPicker.Point;
import java.awt.Color;
import java.awt.Image;

/**
 *
 * @author Alex
 */
public class PaintBucket implements Tool {

    @Override
    /**
     * This function has to apply some change to colorArr
     * Color c is curColor in GridPanel
     */
    public void apply(Color c, int x, int y, Color[][] colorArr) {
        Color curColor = colorArr[x][y];
        colorArr[x][y] = c;
        
        //base case
        if(c == curColor) return;
        
        if(x > 0 && colorArr[x - 1][y] == curColor){
            apply(c,x - 1,y,colorArr);
        }
        if(x < colorArr[0].length - 1&& colorArr[x + 1][y] == curColor){
            apply(c,x + 1,y,colorArr);
        }
        if(y > 0 && colorArr[x][y - 1] == curColor){
            apply(c,x,y - 1,colorArr);
        }
        if(y < colorArr.length - 1&& colorArr[x][y + 1] == curColor){
            apply(c,x,y + 1,colorArr);
        }
        
        
    }

}
