package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    static class Entry<T> implements Serializable {
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        void checkChildren() {
            if (leftChild != null) availableToAddLeftChildren = false;
            else availableToAddLeftChildren = true;
            if (rightChild != null) availableToAddRightChildren = false;
            else availableToAddRightChildren = true;
        }

        boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }

    public static void main(String[] args) {
        List<String> list = new CustomTree();
        for (int i = 1; i < 16; i++) {
            list.add(String.valueOf(i));
        }
        System.out.println("Size " + list.size());
        System.out.println("Expected 3, actual is " + ((CustomTree) list).getParent("8"));
        list.remove("5");
        System.out.println("Expected null, actual is " + ((CustomTree) list).getParent("11"));
        System.out.println(list.size());
    }

    Entry<String> root = new Entry<>("0");

    public boolean add(String element) {
        Entry top = root;
        Entry newEntry = new Entry(element);
        Queue<Entry> queue = new LinkedList<>();
        do {
            if (top.leftChild != null) queue.add(top.leftChild);
            else {
                newEntry.parent = top;
                top.leftChild = newEntry;
                top.checkChildren();
                return true;
            }
            if (top.rightChild != null) queue.add(top.rightChild);
            else {
                newEntry.parent = top;
                top.rightChild = newEntry;
                top.checkChildren();
                return true;
            }
            if (!queue.isEmpty()) top = queue.poll();

        } while (!queue.isEmpty());
        return false;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    public String getParent(String s) {
        Entry top = root;
        String st = null;
        Queue<Entry> queue = new LinkedList<>();
        do {
            if (top.leftChild != null) queue.add(top.leftChild);
            if (top.rightChild != null) queue.add(top.rightChild);
            if (!queue.isEmpty()) top = queue.poll();
            if (top.elementName.equals(s)) {
                st = top.parent.elementName;
                return st;
            }

        } while (!queue.isEmpty());
        return st;
    }

    public boolean remove(Object o) {
        Entry top = root;
        String nameDeletedObject = (String) o;
        Queue<Entry> queue = new LinkedList<>();
        do {
            if (top.leftChild != null) queue.add(top.leftChild);
            if (top.rightChild != null) queue.add(top.rightChild);
            if (!queue.isEmpty()) top = queue.poll();

            if (top.elementName.equals(nameDeletedObject)) {
                Entry parent = top.parent;
                if (parent.leftChild.equals(top)) parent.leftChild = null;
                else parent.rightChild = null;
                parent.checkChildren();
                return true;
            }
        } while (!queue.isEmpty());
        return false;
    }

    @Override
    public int size() {
        if (root == null) {
            return 0;
        }
        int count = 0;
        Queue<Entry> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()){
            count++;
            Entry<String> node = queue.poll();
            if(node.leftChild != null) queue.add(node.leftChild);
            if(node.rightChild != null) queue.add(node.rightChild);
        }
        return count-1;
    }

    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    public String remove(int index) {
        throw new UnsupportedOperationException();
    }


    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }
}
