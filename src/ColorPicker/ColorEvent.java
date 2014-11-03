/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ColorPicker;

import java.awt.Color;
import struct.Event;

/**
 *
 * @author Alex
 */
public class ColorEvent implements Event {
    public Color c;
    
    public ColorEvent(Color c) {
        this.c = c;
    }
    
}
