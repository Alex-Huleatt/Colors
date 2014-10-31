/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ColorPicker;
import GridPanel.*;
import java.awt.Color;
/**
 *
 * @author Alex
 */
public class ColorListener {
    public GridFrame wantsToKnow;
    public ColorListener(GridFrame l) {
        wantsToKnow = l;
    }
    public void alert(Color newColor) {
        wantsToKnow.setColor(newColor);
    }
}
