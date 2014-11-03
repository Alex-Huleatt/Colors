package GridPanel;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ninjakl and also Alex
 */
import ColorPicker.ColorEvent;
import ColorPicker.ColorListener;
import ColorPicker.ColorObserver;
import ColorPicker.Tools.Pencil;
import ColorPicker.Tools.Tool;
import ColorPicker.Tools.ToolEvent;
import ColorPicker.Tools.ToolObserver;
import GridPanel.LayerStuff.LayerEvent;
import GridPanel.LayerStuff.LayerObserver;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;
import javax.swing.*;
import struct.Delta;
import struct.Event;
import struct.Point;

public class GridPanel extends JPanel implements ColorObserver, ToolObserver, LayerObserver {

    int height = 500;
    int width = 500;
    int gridSizeX = 10;
    int gridSizeY = 10;
    ArrayList<Color[][]> layers; //ogre.
    Color[][] colorArr = new Color[gridSizeX][gridSizeY];

    Color curColor = Color.BLACK;
    ColorListener cl;

    boolean showGrid = true;
    boolean connectBox = false;
    boolean backgroundShow = true;

    int[][] colorIntArr;
    Stack<ArrayList<Delta>> history;
    Stack<ArrayList<Delta>> redo;
    ArrayList<Delta> dragging_total;

    Tool currentTool = new Pencil();

    int selected_layer;

    public GridPanel() {
        setPreferredSize(new Dimension(height, width));
        setFocusable(true);
        history = new Stack<>();
        redo = new Stack<>();
        dragging_total = new ArrayList<>();
        layers = new ArrayList<>();
        selected_layer = -1;
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                dragging_total = new ArrayList<>();
                int gridX = (getWidth()) / gridSizeX;
                int gridY = (getHeight()) / gridSizeY;
                int actual_grid_size = Math.min(gridX, gridY);
                int x = e.getX() / actual_grid_size;
                int y = e.getY() / actual_grid_size;
                if (SwingUtilities.isRightMouseButton(e) || e.isShiftDown()) {
                    if (colorArr[x][y] == null) {
                        cl.alertObservers(new ColorEvent(Color.WHITE));
                        curColor = Color.WHITE;
                        return;
                    }
                    cl.alertObservers(new ColorEvent(colorArr[x][y]));
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
                if (dragging_total.size() > 0) {
                    history.push(dragging_total);
                }
                dragging_total = new ArrayList<>();
                for (int i = 0; i < gridSizeX; i++) {
                    for (int j = 0; j < gridSizeY; j++) {
                        if (colorArr[i][j] != null) {
                            //colorIntArr[i][j] = colorArr[i][j].getRGB();
                        } else {
                            //colorIntArr[i][j] = -1;
                        }
                    }
                }
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
                fillArr_drag(x, y);
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int gridX = (getWidth()) / gridSizeX;
                int gridY = (getHeight()) / gridSizeY;
                int actual_grid_size = Math.min(gridX, gridY);
                int x = e.getX() / actual_grid_size;
                int y = e.getY() / actual_grid_size;
                currentTool.mouseMoved(new Point(x, y));
                repaint();
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
                    case KeyEvent.VK_Z:
                        if (e.isControlDown()) {
                            if (history.empty()) {
                                return;
                            }
                            ArrayList<Delta> toUndo = history.pop();
                            System.out.println("Undoing " + toUndo.size() + " things.");
                            int count = 0;
                            System.out.println(history.size());
                            for (Delta d : toUndo) {
                                d.undo(colorArr);
                                count++;
                            }
                            System.out.println(history.size());
                            redo.push(toUndo);
                            break;
                        }
                    case KeyEvent.VK_Y:
                        if (e.isControlDown()) {
                            if (redo.empty()) {
                                return;
                            }
                            ArrayList<Delta> toUnUndo = redo.pop();
                            if (toUnUndo == null) {
                                break;
                            }
                            for (Delta d : toUnUndo) {
                                d.unundo(colorArr);
                            }
                            history.push(toUnUndo);
                        }
                    default:
                        break;
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight()); //clears the screen
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        int gridX = getWidth() / gridSizeX;
        int gridY = getHeight() / gridSizeY;
        int actual_grid_size = Math.min(gridX, gridY);
        if (actual_grid_size == 0) {
            return;
        }
        int tWidth = actual_grid_size * gridSizeX;
        int tHeight = actual_grid_size * gridSizeY;

        g.setColor(Color.WHITE);
        if (backgroundShow) {
            g.fillRect(0, 0, tWidth, tHeight);
        }
        if (selected_layer == -1) {
            for (int i = 0; i < layers.size(); i++) {
                drawLayer(layers.get(i), g, actual_grid_size);
            }

        } else {
            drawLayer(layers.get(selected_layer), g, actual_grid_size);
        }
        drawLayer(colorArr, g, actual_grid_size);
        currentTool.paintSelf(g, actual_grid_size, colorArr, curColor);

        if (showGrid) {
            g.setColor(Color.black);
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

    private void drawLayer(Color[][] layer, Graphics g, int grid_size) {
        if (layer != null) {
            for (int i = 0; i < gridSizeX; i++) {
                for (int j = 0; j < gridSizeY; j++) {
                    if (layer[i][j] != null) {
                        g.setColor(layer[i][j]);
                        if (connectBox) {
                            g.fillRect((i * grid_size) + 1, (j * grid_size) + 1, grid_size, grid_size);
                        } else {
                            g.fillRect((i * grid_size) + 1, (j * grid_size) + 1, grid_size - 1, grid_size - 1);
                        }
                    }
                }
            }
        }
    }

    public void fillArr(int x, int y) {
        ArrayList<Delta> changes = currentTool.apply(curColor, x, y, colorArr);
        ArrayList<Delta> accepted = new ArrayList<>();
        for (Delta d : changes) {
            if (!d.amUseless()) {
                accepted.add(d);
                System.out.println(d);
            }

        }
        redo = new Stack<>();
        if (accepted.size() > 0) {
            history.push(accepted);
        }
    }

    public void fillArr_drag(int x, int y) {
        ArrayList<Delta> changes = currentTool.apply(curColor, x, y, colorArr);
        ArrayList<Delta> accepted = new ArrayList<>();
        for (Delta d : changes) {
            if (!d.amUseless()) {
                accepted.add(d);
                System.out.println(d);
            }

        }
        redo = new Stack<>();
        dragging_total.addAll(accepted);
    }

    @Override
    public void alert(ColorEvent c) {
        curColor = c.c;
        System.out.println("GridPanel was alerted to color change.");
    }

    @Override
    public void alert(ToolEvent t) {
        currentTool = t.t;
        System.out.println("GridPanel was alerted to tool change");
    }

    @Override
    public void alert(Event e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alert(LayerEvent l) {
        if (l.type == LayerEvent.LAYER_SELECTED) {
            selected_layer = Math.min(l.layer, layers.size() - 1);
            colorArr = layers.get(selected_layer);

        }
        if (l.type == LayerEvent.LAYER_CREATED) {
            colorArr = new Color[gridSizeX][gridSizeY];
            for (Color[] arr : colorArr) {
                Arrays.fill(arr, new Color(255, 255, 255, 0));
            }
            layers.add(colorArr);
            selected_layer = layers.size()-1;

        }
        if (l.type == LayerEvent.LAYER_DESELECTED) {
            selected_layer = -1;
            colorArr = layers.get(layers.size()-1);
        }
        repaint();
    }
}
