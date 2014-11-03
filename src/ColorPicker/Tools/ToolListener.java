/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ColorPicker.Tools;

import java.util.ArrayList;
import struct.Event;
import struct.Listener;
import struct.Observer;

/**
 *
 * @author Alex
 */
public class ToolListener implements Listener {
    ArrayList<ToolObserver> to;
    
    public ToolListener() {
        this.to = new ArrayList<>();
    }
    
    public void alertObservers(ToolEvent newTool) {
        for (ToolObserver t : to) {
            t.alert(newTool);
        }
    }
    
    public void listen(ToolObserver t) {
        to.add(t);
    }

    @Override
    public void alertObservers(Event e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void listen(Observer o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
