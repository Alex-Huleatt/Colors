/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GridPanel.LayerStuff;

import struct.Event;

/**
 *
 * @author Alex
 */
public class LayerEvent implements Event {
    public static int LAYER_SELECTED = 0;
    public static int LAYER_CREATED = 1;
    public static int LAYER_DELETED = 2;
    
    public int type;
    public int layer;
    
    public LayerEvent(int type, int layer) {
        this.type = type;
        this.layer = layer;
    }
    
}
