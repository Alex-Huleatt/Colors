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
import ColorPicker.ColorListener;
import ColorPicker.Tools.ToolListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class GridFrame {
    JFrame frame = new JFrame("Grid");
    GridPanel gp;
    public GridFrame(){
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(gp = new GridPanel(),BorderLayout.CENTER);
        JButton exp = new JButton("Export");
        exp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    IO io = new IO();
                    io.drawImg(gp.colorArr);
                } catch (IOException ex) {
                    Logger.getLogger(GridFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        frame.add(exp, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);   
    }
    
    public void setColor(Color c) {
        gp.curColor = c;
    }
    
    public void giveCL(ColorListener cl) {
        cl.listenToThis(gp);
        gp.cl = cl;
    }
    
    public void giveTL(ToolListener tl) {
        tl.listen(gp);
    }
}
