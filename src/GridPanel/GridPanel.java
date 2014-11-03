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
import ColorPicker.Tools.Pencil;
import ColorPicker.Tools.Tool;
import ColorPicker.Tools.ToolObserver;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import java.util.LinkedList;

public class GridPanel extends JPanel implements ColorObserver, ToolObserver {

    int height = 500;
    int width = 500;
    int gridSizeX = 10;
    int gridSizeY = 10;
    Color[][] colorArr = new Color[gridSizeX][gridSizeY];

    Color curColor = Color.BLACK;
    ColorListener cl;

    boolean showGrid = true;
    boolean connectBox = false;
    boolean backgroundShow = true;
    
    boolean controlDown = false;
    
    LinkedList<int[][]>history = new LinkedList();
    int[][] colorIntArr = new int[gridSizeX][gridSizeY];
    

    Tool currentTool = new Pencil();

    public GridPanel() {
        setPreferredSize(new Dimension(height, width));
        setFocusable(true);
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int gridX = (getWidth()) / gridSizeX;
                int gridY = (getHeight()) / gridSizeY;
                int actual_grid_size = Math.min(gridX, gridY);
                int x = e.getX() / actual_grid_size;
                int y = e.getY() / actual_grid_size;
                if (SwingUtilities.isRightMouseButton(e) || e.isShiftDown()) {
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
                for(int i = 0; i < gridSizeX; i++){
                    for(int j = 0; j < gridSizeY;j++){
                        if(colorArr[i][j]!=null) colorIntArr[i][j] = colorArr[i][j].getRGB();
                        else colorIntArr[i][j] = -1;
                    }
                }
                history.add(colorIntArr);
                System.out.println("added");
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
                int gridX = (getWidth()) / gridSizeX;
                int gridY = (getHeight()) / gridSizeY;
                int actual_grid_size = Math.min(gridX, gridY);
                int x = e.getX() / actual_grid_size;
                int y = e.getY() / actual_grid_size;
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

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_1:
                        showGrid = !showGrid;
                        break;
                    case KeyEvent.VK_2:
                        connectBox = !connectBox;
                        break;
                    case KeyEvent.VK_3:
                        backgroundShow = !backgroundShow;
                        break;
                    case KeyEvent.VK_CONTROL:
                        controlDown = true;
                        break;
                    case KeyEvent.VK_Z:
                        if(controlDown == true && history.peekFirst() != null){
                            colorIntArr = history.poll();
                             for(int i = 0; i < gridSizeX; i++){
                                for (int j = 0; j < gridSizeY; j++){
                                    if(colorIntArr[i][j] != -1){
                                        colorArr[i][j] = new Color(colorIntArr[i][j]);
                                    }
                                }
                            
                            }
                             System.out.println("delete");
                        }
                    default:break;
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_CONTROL:
                        controlDown = false;
                    default:break;
                }
                
            }

        });
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight()); //clears the screen
        int gridX = getWidth() / gridSizeX;
        int gridY = getHeight() / gridSizeY;
        int actual_grid_size = Math.min(gridX, gridY);
        int tWidth = actual_grid_size * gridSizeX;
        int tHeight = actual_grid_size * gridSizeY;
        g.setColor(Color.white);
        if (backgroundShow) {
            g.fillRect(0, 0, tWidth, tHeight);
        }
        g.setColor(curColor);
        for (int i = 0; i < gridSizeX; i++) {
            for (int j = 0; j < gridSizeY; j++) {
                if (colorArr[i][j] != null) {
                    g.setColor(colorArr[i][j]);
                    if (connectBox) {
                        g.fillRect((i * actual_grid_size) + 1, (j * actual_grid_size) + 1, actual_grid_size, actual_grid_size);
                    } else {
                        g.fillRect((i * actual_grid_size) + 1, (j * actual_grid_size) + 1, actual_grid_size - 1, actual_grid_size - 1);
                    }
                }
            }
        }
        g.setColor(Color.black);
        if (showGrid) {
            for (int i = 0; i <= actual_grid_size * gridSizeX; i += actual_grid_size) {
                g.drawLine(0, i, tWidth, i);
            }
            g.drawLine(tWidth - 1, 0, tWidth - 1, tHeight - 1);
            for (int i = 0; i <= actual_grid_size * gridSizeY; i += actual_grid_size) {
                g.drawLine(i, 0, i, tHeight);
            }
            g.drawLine(0, tHeight - 1, tWidth - 1, tHeight - 1);
        }
    }

    public void fillArr(int x, int y) {
        currentTool.apply(curColor, x, y, colorArr);
    }

    @Override
    public void alert(Color c) {
        curColor = c;
        System.out.println("GridPanel was alerted to color change.");
    }

    @Override
    public void alert(Tool t) {
        currentTool = t;
        System.out.println("GridPanel was alerted to tool change");
    }
}
