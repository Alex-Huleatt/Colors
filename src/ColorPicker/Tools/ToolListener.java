/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ColorPicker.Tools;

import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class ToolListener {
    ArrayList<ToolObserver> to;
    
    public ToolListener() {
        this.to = new ArrayList<>();
    }
    
    public void tell(Tool newTool) {
        for (ToolObserver t : to) {
            t.alert(newTool);
        }
    }
    
    public void listen(ToolObserver t) {
        to.add(t);
    }
}
