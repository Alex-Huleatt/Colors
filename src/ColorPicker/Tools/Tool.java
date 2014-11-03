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
import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public interface Tool {
    public ArrayList<Delta> apply(Color c, int x, int y, Color[][] colorArr);
    
    public void paintSelf(Graphics g, int pixelSize);
}
