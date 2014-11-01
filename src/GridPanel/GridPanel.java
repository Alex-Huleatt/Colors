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
import ColorPicker.ColorObserver;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

public class GridPanel extends JPanel implements ColorObserver {

    int height = 500;
    int width = 500;
    int gridSize = 20;
    int gridHoriz = ((width) / gridSize);
    int gridVert = ((height) / gridSize);
    int squareSize = height / gridSize;
    Color[][] colorArr = new Color[gridSize][gridSize];

    Color curColor = Color.BLACK;
    ColorListener cl;

    public GridPanel() {
        setPreferredSize(new Dimension(height, width));

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / squareSize;
                int y = e.getY() / squareSize;
                if (SwingUtilities.isRightMouseButton(e)) {
                    cl.alert(colorArr[x][y]);
                    curColor = colorArr[x][y];
                    return;
                }
                fillArr(x, y);
                repaint();
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
        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX() / squareSize;
                int y = e.getY() / squareSize;
                fillArr(x, y);
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }

        });
        addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
               //if (e.getKeyCode() == )
            }

            @Override
            public void keyPressed(KeyEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
    }

    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, height, width);
        g.setColor(Color.black);
        for (int i = 0; i <= width; i += gridHoriz) {
            g.drawLine(0, i, width, i);
        }
        g.drawLine(width - 1, 0, width - 1, height - 1);
        for (int i = 0; i <= height; i += gridVert) {
            g.drawLine(i, 0, i, height);
        }
        g.drawLine(0, height - 1, width - 1, height - 1);
        g.setColor(curColor);
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (colorArr[i][j] != null) {
                    g.setColor(colorArr[i][j]);
                    g.fillRect((i * squareSize) + 1, (j * squareSize) + 1, squareSize - 1, squareSize - 1);
                }
            }
        }
    }

    public void fillArr(int x, int y) {
        colorArr[x][y] = curColor;
    }

    @Override
    public void alert(Color c) {
        curColor = c;
        System.out.println("GridPanel was alerted to color change.");
    }
}