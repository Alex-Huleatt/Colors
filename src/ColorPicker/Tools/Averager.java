/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ColorPicker.Tools;

import java.awt.Color;

/**
 *
 * @author Alex
 */
public class Averager implements Tool {

    @Override
    public void apply(Color c, int x, int y, Color[][] colorArr) {
        int count = 0;
        int r = 0;
        int g = 0;
        int b = 0;
                
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (x+i >= 0 && x+i < colorArr.length && y >= 0 && y < colorArr[x+i].length) {
                    count++;
                    Color d = colorArr[x+i][y+j];
                    if (d == null) d = Color.WHITE;
                    r += d.getRed();
                    g += d.getGreen();
                    b += d.getBlue();
                }
            }
        }
        r/=count;
        g/=count;
        b/=count;
        colorArr[x][y] = new Color(r,g,b);
    }
    
}
