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
import struct.Point;

/**
 *
 * @author ninjakl
 */
public class Square implements Tool{


    @Override
    public ArrayList<Delta> apply(Color c, int x, int y, Color[][] colorArr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void paintSelf(Graphics g, int pixelSize, Color[][] colorArr, Color curColor) {
        
    }

    @Override
    public void mouseMoved(Point p) {
    }

    @Override
    public void mouseDragged(Point p) {
    }
    
    
    
}
