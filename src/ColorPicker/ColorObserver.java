/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ColorPicker;

import java.awt.Color;
import struct.Observer;

/**
 *
 * @author Alex
 */
public interface ColorObserver extends Observer {
    
    public void alert(ColorEvent c);
    
}
