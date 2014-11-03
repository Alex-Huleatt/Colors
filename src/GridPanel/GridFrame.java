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
import GridPanel.LayerStuff.LayerListener;
import GridPanel.LayerStuff.LayerObserver;
import GridPanel.LayerStuff.LayerPanel;
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
    LayerPanel lp;
    public GridFrame(){
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        JButton exp = new JButton("Export");
        exp.setFocusable(false);
        exp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = fc.showOpenDialog(frame);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    System.out.println(fc.getSelectedFile());
                    String dir = fc.getSelectedFile().toString();
                    String fileName = JOptionPane.showInputDialog(frame,"File name pls");                    
                    System.out.println(dir);
                    IO io = new IO(dir + "/" + fileName);
                    try {
                        io.drawImg(gp.layers);
                    } catch (IOException ex) {
                        Logger.getLogger(GridFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                }
            }
            
        });
        
        frame.add(exp, BorderLayout.NORTH);
        frame.add(gp = new GridPanel(),BorderLayout.CENTER);
        
        lp = new LayerPanel();
        frame.add(lp,BorderLayout.SOUTH);
        
        frame.pack();
        frame.setVisible(true);  
        
        
    }
    
    public void setColor(Color c) {
        gp.curColor = c;
    }
    
    public void giveCL(ColorListener cl) {
        cl.listen(gp);
        gp.cl = cl;
    }
    
    public void giveTL(ToolListener tl) {
        tl.listen(gp);
    }
    
    public void giveLL(LayerListener ll) {
        lp.giveLL(ll);
        ll.listen((LayerObserver)gp);
    }
}
