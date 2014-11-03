package GridPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ninjakl
 */
import ColorPicker.*;
import ColorPicker.Tools.ToolListener;
import GridPanel.LayerStuff.LayerEvent;
import GridPanel.LayerStuff.LayerListener;
import java.awt.Color;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GridFrame frame = new GridFrame();
        PFrame cFrame = new PFrame();
        ColorListener cl = new ColorListener();
        ToolListener tl = new ToolListener();
        LayerListener ll = new LayerListener();
        cFrame.giveTL(tl);
        cFrame.giveCL(cl);
        frame.giveCL(cl);
        frame.giveTL(tl);
        frame.giveLL(ll);
        ll.alertObservers(new LayerEvent(LayerEvent.LAYER_CREATED,0));
        cl.alertObservers(new ColorEvent(Color.WHITE));
    }
    
}
