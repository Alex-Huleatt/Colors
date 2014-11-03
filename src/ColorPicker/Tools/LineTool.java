/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ColorPicker.Tools;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import struct.Delta;
import struct.Point;

/**
 *
 * @author Alex
 */
public class LineTool implements Tool {

    private Point start;
    private Point temp;

    @Override
    public ArrayList<Delta> apply(Color c, int x, int y, Color[][] colorArr) {
        if (start == null) {
            start = new Point(x, y);
            return new ArrayList<>();
        } else {
            Point p1 = start;
            Point p2 = new Point(x,y);
            ArrayList<Point> ch = bresenham(p1,p2);
            ArrayList<Delta> deltas = new ArrayList<>();
            for (Point d : ch) {
                Delta del = new Delta(d.x,d.y,colorArr[d.x][d.y],c);
                deltas.add(del);
                colorArr[d.x][d.y] = c;
            }
            start = null;
            return deltas;
            
        }
    }

    private ArrayList<Point> bresenham(Point p1, Point p2) {
        ArrayList<Point> del = new ArrayList<>();
        int x1 = p1.x;
        int y1 = p1.y;
        int x2 = p2.x;
        int y2 = p2.y;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;
        while (true) {
            del.add(new Point(x1,y1));

            int e2 = err << 1;
            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }
            if (x1 == x2 && y1 == y2) {
                break;
            }

            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
            if (x1 == x2 && y1 == y2) {
                break;
            }
        }
        del.add(p2);
        return del;
    }

    public double mag(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public void paintSelf(Graphics g, int pixelSize, Color[][] colorArr, Color curColor) {
        if (start == null) return;
        ArrayList<Point> ch = bresenham(start, temp);
        g.setColor(curColor);
        for (Point p : ch) {
            
            g.fillRect(p.x*pixelSize+1, p.y*pixelSize+1, pixelSize-1, pixelSize-1);
        }
    }

    @Override
    public void mouseMoved(Point p) {
        this.temp = p;
    }

    @Override
    public void mouseDragged(Point p) {
        this.start = p;
    }

}
