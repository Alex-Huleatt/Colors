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
import java.awt.Color;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GridFrame frame = new GridFrame();
        PFrame cFrame = new PFrame();
        ColorListener cl = new ColorListener(frame);
        cFrame.giveCL(cl);
    }
    
}
