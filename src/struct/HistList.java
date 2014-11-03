/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package struct;

/**
 *
 * @author Alex
 */
public class HistList<E> {
    private Node<E> root;
    private int len;
    private int cap;
    
    public HistList(int capacity) {
        this.root = null;
        this.len = 0;
        this.cap = capacity;
    }
    
    private class Node<E> {
        Node<E> next;
        Node<E> prev;
        E val;
        
        public Node(Node<E> next, Node<E> prev, E val) {
            this.next = next;
            this.prev = prev;
            this.val = val;
        }
    }
    
    public void push(E val) {
        Node<E> new_val = new Node<>(null,null,val);
        
        if (root == null) {
            root=new_val;
            len++;
        }
        if (len > cap) {
            new_val.prev = root.prev.prev;
            new_val.prev.next = new_val;
            new_val.next = root;
            root.prev = new_val;
            root = new_val;
        } else if (len <= cap) {
            new_val.prev = root.prev;
            root.prev = new_val;
            new_val.next = root;
            new_val.prev.next = new_val;
            root = new_val;
            len++;
        }
    }
    
    public E pop() {
        Node<E> toRet = root;
        Node<E> newRoot = root.next;
        root.prev.next = newRoot;
        root.next.prev = newRoot;
        root = newRoot;
        if (len == 0) root = null;
        len = Math.max(len-1, 0);
        return toRet.val;
    }
    
    public E removeOld() {
        if (root == null) return null;
        Node<E> toRet = root.prev;
        root.prev = toRet.prev;
        toRet.prev.next = root;
        len--;
        return toRet.val;
    }
    
    public int size() {
        return len;
    }
    
    public String toString() {
        String s = "";
        Node<E> curr = root;
        while (true) {
            s += curr.val.toString() + " ";
            curr = curr.next;
            if (curr == root) return s;
        }
    }
    
    
    
}
