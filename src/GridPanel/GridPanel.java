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
                    if (colorArr[x][y] == null) {
                        cl.alert(Color.WHITE);
                        curColor = Color.WHITE;
                        return;
                    }
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
        addKeyListener(new KeyListener() {

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
        int dim = Math.min(getWidth(), getHeight());
        dim -= dim % colorArr.length;
        int actual_grid_size = (dim / colorArr.length);
        System.out.println(actual_grid_size);
        g.setColor(Color.white);
        g.fillRect(0, 0, dim, dim);
        g.setColor(curColor);
        for (int i = 0; i < colorArr.length; i++) {
            for (int j = 0; j < colorArr[i].length; j++) {
                if (colorArr[i][j] != null) {
                    g.setColor(colorArr[i][j]);
                    g.fillRect((i * actual_grid_size) + 1, (j * actual_grid_size) + 1, actual_grid_size - 1, actual_grid_size - 1);
                }
            }
        }
        g.setColor(Color.black);
        for (int i = 0; i < dim; i += actual_grid_size) {
            g.drawLine(0, i, dim, i);
        }
        g.drawLine(dim - 1, 0, dim - 1, dim - 1);
        for (int i = 0; i < dim; i += actual_grid_size) {
            g.drawLine(i, 0, i, dim);
        }
        g.drawLine(0, dim - 1, dim - 1, dim - 1);
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
