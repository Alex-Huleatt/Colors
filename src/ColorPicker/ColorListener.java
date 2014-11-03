/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ColorPicker;
import GridPanel.*;
import java.awt.Color;
import java.util.ArrayList;
import struct.Event;
import struct.Listener;
import struct.Observer;
/**
 *
 * @author Alex
 */
public class ColorListener implements Listener {
    public ArrayList<ColorObserver> wantsToKnow;
    public ColorListener() {
        wantsToKnow = new ArrayList<>();
    }
    public void listen(Observer co) {
        if (co instanceof ColorObserver) wantsToKnow.add((ColorObserver)co);
    }
    public void alertObservers(Event e) {
        for (ColorObserver co : wantsToKnow) {
            System.out.println("Alerted.");
            co.alert((ColorEvent) e);
        }
    }

}
