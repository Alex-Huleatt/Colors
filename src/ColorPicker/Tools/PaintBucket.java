/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ColorPicker.Tools;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import struct.Delta;
import struct.Point;

/**
 *
 * @author Alex
 */
public class PaintBucket implements Tool {

    Point temp;
    private ArrayList<Delta> dMap;
    private Color[][] colorArr;
    private LinkedList<Point> q;

    @Override
    /**
     * This function has to apply some change to colorArr Color c is curColor in
     * GridPanel
     */
    public ArrayList<Delta> apply(Color c, int x, int y, Color[][] colorArr) {
        dMap = new ArrayList<>();
        this.colorArr = colorArr;
        q = new LinkedList<>();
        q.push(new Point(x, y));
        while (!q.isEmpty()) {
            bucketHelper(c);
        }
        return dMap;
    }

    public void bucketHelper(Color c) {
        Point p = q.pop();
        Color curColor = colorArr[p.x][p.y];
        if (curColor.equals(c)) {
            return;
        }
        addDelta(p.x, p.y, c);
        colorArr[p.x][p.y] = c;

        if (p.x > 0 && curColor.equals(colorArr[p.x - 1][p.y])) {
            q.push(new Point(p.x - 1, p.y));

        }
        if (p.x < colorArr[0].length - 1 && curColor.equals(colorArr[p.x + 1][p.y])) {
            q.push(new Point(p.x + 1, p.y));
        }
        if (p.y > 0 && curColor.equals(colorArr[p.x][p.y - 1])) {
            q.push(new Point(p.x, p.y - 1));
        }
        if (p.y < colorArr.length - 1 && curColor.equals(colorArr[p.x][p.y + 1])) {
            q.push(new Point(p.x, p.y + 1));
        }
    }

    @Override
    public void paintSelf(Graphics g, int pixelSize, Color[][] colorArr, Color curColor) {
//        ArrayList<Delta> del = apply(curColor, temp.x, temp.y, colorArr);
//        g.setColor(curColor);
//        for (Delta d : del) {
//            g.fillRect(d.x*pixelSize+1, d.y*pixelSize+1, pixelSize-1, pixelSize-1);
//            d.undo(colorArr);
//        }

    }

    private void addDelta(int x, int y, Color newC) {
        Delta d = new Delta(x, y, colorArr[x][y], newC);
        if (!d.amUseless()) {
            dMap.add(d);
        }
    }

    @Override
    public void mouseMoved(Point p) {
        temp = p;
    }

    @Override
    public void mouseDragged(Point p) {
    }
}
