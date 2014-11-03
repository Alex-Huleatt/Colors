/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ColorPicker.Tools;

import struct.Event;

/**
 *
 * @author Alex
 */
public class ToolEvent implements Event {
    public Tool t;
    public ToolEvent(Tool t) {this.t=t;}
    
}
