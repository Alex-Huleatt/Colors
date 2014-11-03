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
    final int gridSizeX;
    final int gridSizeY;
    ArrayList<Layer> layers; //ogre.
    Layer currentLayer;

    Color curColor = Color.BLACK;
    ColorListener cl;

    boolean showGrid = true;
    boolean connectBox = false;
    boolean backgroundShow = true;

    ArrayList<Delta> dragging_total;

    Tool currentTool = new Pencil();

    int selected_layer;

    public GridPanel(int gridSizeX, int gridSizeY) {
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        setPreferredSize(new Dimension(height, width));
        setFocusable(true);
        dragging_total = new ArrayList<>();
        layers = new ArrayList<>();
        selected_layer = -1;
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                dragging_total = new ArrayList<>();
                Point p = getDrawPoint(e.getX(),e.getY());
                if (SwingUtilities.isRightMouseButton(e) || e.isShiftDown()) {
                    cl.alertObservers(new ColorEvent(currentLayer.colorArr[p.x][p.y]));
                    curColor = currentLayer.colorArr[p.x][p.y];
                    return;
                }
                fillArr(p.x, p.y);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragging_total.size() > 0) {
                    currentLayer.undo.push(dragging_total);
                }
                dragging_total = new ArrayList<>();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                requestFocus(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = getDrawPoint(e.getX(), e.getY());
                fillArr_drag(p.x, p.y);
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                currentTool.mouseMoved(getDrawPoint(e.getX(), e.getY()));
                repaint();
            }

        });
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) { }

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
                            currentLayer.undo();
                            System.out.println("Trying to undo.");
                            break;
                        }
                    case KeyEvent.VK_Y:
                        if (e.isControlDown()) {
                            currentLayer.redo();
                            System.out.println("Trying to redo.");
                            break;
                        }
                    default:
                        break;
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) { }

        });
    }

    public Point getDrawPoint(int x, int y) {
        int gridX = (getWidth()) / gridSizeX;
        int gridY = (getHeight()) / gridSizeY;
        int actual_grid_size = Math.min(gridX, gridY);
        return new Point(x / actual_grid_size, y / actual_grid_size);
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight()); //clears the screen
        int gridX = getWidth() / gridSizeX;
        int gridY = getHeight() / gridSizeY;
        int actual_grid_size = Math.min(gridX, gridY);
        if (actual_grid_size == 0) {
            return;
        }
        int tWidth = actual_grid_size * gridSizeX;
        int tHeight = actual_grid_size * gridSizeY;

        if (backgroundShow) {
            g.setColor(Color.GRAY);
            for (int i = 0; i < gridSizeX; i++) {
                for (int j = 0; j < gridSizeY; j++) {
                    g.drawLine(i*actual_grid_size, j*actual_grid_size, (i+1)*actual_grid_size, (j+1)*actual_grid_size);
                    g.drawLine(i*actual_grid_size, (j+1)*actual_grid_size, (i+1)*actual_grid_size, j*actual_grid_size);
                }
            }
        }

        for (Layer l : layers) {
            l.drawLayer(g, actual_grid_size, connectBox, gridSizeX, gridSizeY);
        }
        
        currentTool.paintSelf(g, actual_grid_size, null, curColor);

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

    public void fillArr(int x, int y) {
        ArrayList<Delta> changes = currentTool.apply(curColor, x, y, currentLayer.colorArr);
        ArrayList<Delta> accepted = new ArrayList<>();
        for (Delta d : changes) {
            if (!d.amUseless()) {
                accepted.add(d);
            }

        }
        currentLayer.redo = new Stack<>();
        if (accepted.size() > 0) {
            currentLayer.undo.push(accepted);
        }
    }

    public void fillArr_drag(int x, int y) {
        ArrayList<Delta> changes = currentTool.apply(curColor, x, y, currentLayer.colorArr);
        ArrayList<Delta> accepted = new ArrayList<>();
        for (Delta d : changes) {
            if (!d.amUseless()) {
                accepted.add(d);
            }
        }
        currentLayer.redo = new Stack<>();
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
        switch (l.type) {
            case LayerEvent.LAYER_SELECTED: {
                layers.get(l.layer).displayable = true;
                currentLayer = layers.get(l.layer);
                break;
            }
            case LayerEvent.LAYER_CREATED: {
                Layer newLayer = new Layer(new Color[gridSizeX][gridSizeY]);
                for (Color[] arr : newLayer.colorArr) {
                    Arrays.fill(arr, new Color(255, 255, 255, 0));
                }
                layers.add(newLayer);
                currentLayer = newLayer;
                break;
            }
            case LayerEvent.LAYER_DESELECTED: {
                layers.get(l.layer).displayable = false;
                currentLayer = null;
                for (int i = layers.size() - 1; i >= 0; i--) {
                    if (layers.get(i).displayable) {
                        currentLayer = layers.get(i);
                        break;
                    }
                }
                break;
            }

        }
        repaint();
    }
}