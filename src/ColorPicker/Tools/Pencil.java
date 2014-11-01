/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ColorPicker.Tools;

import ColorPicker.Point;
import java.awt.Color;

/**
 *
 * @author Alex
 */
public class Pencil implements Tool {

    @Override
    public void apply(Color c, int x, int y, Color[][] colorArr) {
        if (x < colorArr.length && x >= 0 && y < colorArr[x].length && y >= 0) {
            colorArr[x][y] = c;
        }
    }

}
