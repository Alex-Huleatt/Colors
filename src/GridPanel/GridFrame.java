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
import java.awt.*;
import javax.swing.*;

public class GridFrame {
    JFrame frame = new JFrame("Grid");
    GridPanel gp;
    public GridFrame(){
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(gp = new GridPanel(),BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);   
    }
    
    public void setColor(Color c) {
        gp.curColor = c;
    }
}