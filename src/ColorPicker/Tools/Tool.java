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
public interface Tool {
    public void apply(Color c, int x, int y, Color[][] colorArr);
}
