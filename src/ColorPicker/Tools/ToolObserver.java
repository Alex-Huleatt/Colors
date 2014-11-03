/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ColorPicker.Tools;

import struct.Observer;

/**
 *
 * @author Alex
 */
public interface ToolObserver extends Observer {
    
    public void alert(ToolEvent t);
    
}
