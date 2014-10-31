/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ColorPicker;
import GridPanel.*;
import java.awt.Color;
import java.util.ArrayList;
/**
 *
 * @author Alex
 */
public class ColorListener {
    public ArrayList<ColorObserver> wantsToKnow;
    public ColorListener(GridFrame l) {
        wantsToKnow = new ArrayList<>();
    }
    public void listenToThis(ColorObserver co) {
        wantsToKnow.add(co);
    }
    public void alert(Color newColor) {
        for (ColorObserver co : wantsToKnow) {
            System.out.println("Alerted.");
            co.alert(newColor);
        }
    }
}
