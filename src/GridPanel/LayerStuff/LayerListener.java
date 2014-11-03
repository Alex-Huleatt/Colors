/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GridPanel.LayerStuff;

import java.util.ArrayList;
import struct.Event;
import struct.Listener;
import struct.Observer;

/**
 *
 * @author Alex
 */
public class LayerListener implements Listener {

    public ArrayList<LayerObserver> wantsToKnow;

    public LayerListener() {
        wantsToKnow = new ArrayList<>();
    }

    public void listen(LayerObserver co) {
        wantsToKnow.add(co);
    }

    public void alertObservers(LayerEvent l) {
        for (LayerObserver co : wantsToKnow) {
            System.out.println("Alerted.");
            co.alert(l);
        }
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
