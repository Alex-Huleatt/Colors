/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ColorPicker;

import colors.Colors;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

/**
 *
 * @author Alex
 */
public class PPanel extends javax.swing.JPanel implements ColorObserver {

    private Point mousePosn;
    public Color selectedColor;
    private double saturation;
    private ColorListener cl;

    /**
     * Creates new form PPnael
     */
    public PPanel() {
        initComponents();
        mousePosn = new Point(-1, -1);
        saturation = 1;
        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mousePosn = new Point(e.getX(), e.getY());
                repaint();
            }

        });

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                selectColor(getColor(new Point(e.getX(), e.getY())));
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });
    }

    public void giveCL(ColorListener cl) {
        this.cl = cl;
        cl.listenToThis(this);

    }

    public void selectColor(Color c) {
        selectedColor = c;
        cl.alert(selectedColor);
    }

    public void setSaturation(double d) {
        this.saturation = d;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        int square_size = 4;
        int radius = Math.min(getWidth() / 2, getHeight() / 2) - 20;
        double dis_inc = Math.sqrt(square_size * square_size);
        double circum = 2 * Math.PI * radius;
        double numSquaresOnOuterEdge = (int) (((circum) / (square_size / 2)) + .5); //rounded up
        double rad_inc = ((2 * Math.PI) / numSquaresOnOuterEdge);
        for (double j = 0; j <= radius; j += dis_inc) {
            for (double i = 0; i < Math.PI * 2; i += rad_inc) {
                double x_offset = Math.cos(i) * j;
                double[] rgb = Colors.toRGB(Math.toDegrees(i), saturation, j / radius);
                g.setColor(new Color((int) rgb[0], (int) rgb[1], (int) rgb[2]));
                g.fillRect((int) (getWidth() / 2 + x_offset) + square_size / 2, getHeight() / 2 + (int) (Math.sin(i) * j) + square_size / 2, square_size, square_size);
            }
        }

        try {
            double r = euclid(mousePosn, new Point(getWidth() / 2, getHeight() / 2));
            double deg = Math.atan2(getHeight() / 2 - mousePosn.y, getWidth() / 2 - mousePosn.x) * 180 / Math.PI + 180;
            double[] d = Colors.toRGB(deg, saturation, r / radius);
            for (int i = 0; i < d.length; i++) {
                if (d[i] > 255) {
                    return;
                }
            }
            String str = "";
            for (int i = 0; i < 3; i++) {
                str += String.format("%.0f", Math.abs(d[i])) + ((i < 2) ? " " : "");
            }
            FontMetrics gp = g.getFontMetrics();
            int x = gp.stringWidth(str);
            g.setColor(Color.BLACK);
            int tx;
            if (mousePosn.x > getWidth() / 2) {
                tx = mousePosn.x - 10 - x;
            } else {
                tx = mousePosn.x + 10;
            }
            int ty;
            if (Math.abs(mousePosn.y - getHeight() / 2) < 30) {
                if (mousePosn.y < getHeight() / 2) {
                    ty = mousePosn.y - 20;
                } else {
                    ty = mousePosn.y + 20;
                }
            } else {
                ty = mousePosn.y;
            }

            g.drawString(str, tx, ty);
        } catch (Exception e) {
            //do nothing
        }

    }

    public Color getColor(Point p) {
        int radius = Math.min(getWidth() / 2, getHeight() / 2) - 20;
        double r = euclid(mousePosn, new Point(getWidth() / 2, getHeight() / 2));
        double deg = Math.atan2(getHeight() / 2 - mousePosn.y, getWidth() / 2 - mousePosn.x) * 180 / Math.PI + 180;
        try {
            double[] d = Colors.toRGB(deg, saturation, r / radius);
            return new Color((int) d[0], (int) d[1], (int) d[2]);
        } catch (Exception e) {
            return Color.WHITE;
        }
    }

    public double euclid(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void alert(Color c) {
        System.out.println("PPanel was alerted to color change.");
        //do nothing
    }
}
