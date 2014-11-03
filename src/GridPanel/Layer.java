/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GridPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Stack;
import struct.Delta;

/**
 *
 * @author Alex
 */
public class Layer {

    boolean displayable;
    Color[][] colorArr;
    Stack<ArrayList<Delta>> undo;
    Stack<ArrayList<Delta>> redo;

    public Layer(Color[][] colorArr) {
        displayable = true;
        this.colorArr = colorArr;
        undo = new Stack<>();
        redo = new Stack<>();
    }

    public void drawLayer(Graphics g, int grid_size, boolean connected, int gx, int gy) {
        if (displayable) {
            for (int i = 0; i < gx; i++) {
                for (int j = 0; j < gy; j++) {
                    if (colorArr[i][j] != null) {
                        g.setColor(colorArr[i][j]);
                        if (connected) {
                            g.fillRect((i * grid_size) + 1, (j * grid_size) + 1, grid_size, grid_size);
                        } else {
                            g.fillRect((i * grid_size) + 1, (j * grid_size) + 1, grid_size - 1, grid_size - 1);
                        }
                    }
                }
            }
        }
    }

    public void undo() {
        if (!undo.empty()) {
            ArrayList<Delta> toUndo = undo.pop();
            for (Delta d : toUndo) {
                d.undo(colorArr);
            }
            redo.push(toUndo);
        }
    }

    public void redo() {
        if (!redo.empty()) {
            ArrayList<Delta> toDo = redo.pop();
            for (Delta d : toDo) {
                d.unundo(colorArr);
            }
            undo.push(toDo);
        }
    }

}
