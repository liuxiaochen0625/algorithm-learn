/**
 * reus
 * Copyright (C), 2011 - 2019, reus.
 */
package org.reus.tree;

import java.util.ArrayList;

/**
 * @version $Id: AVL.java, v 0.1 2019-12-26 reus Exp $
 * @ClassName: AVL
 * @Description: 平衡二叉树
 * @author: reus
 */
public class AVL<K extends Comparable<K>, V> {

    private class Node {
        public K     key;
        private V    value;
        private Node left, right;
        public int   height;     //记录节点高度

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            height = 1;
        }
    }

    private Node root;
    private int  size;

    public AVL() {
        root = null;
        size = 0;
    }

    /**
     * Getter method for property <tt>size</tt>.
     *
     * @return property value of size
     */
    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 获取节点的高度
     * @param node
     * @return
     */
    private int getHeight(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    /**
     * 获取节点的平衡因子
     * @param node
     * @return
     */
    public int getBalanceFactor(Node node) {
        if (node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    public void add(K key, V value) {
        root = add(root, key, value);
    }

    public Node add(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(node.key) < 0)
            node.left = add(node.left, key, value);
        else if (key.compareTo(node.key) > 0)
            node.right = add(node.right, key, value);
        else
            node.value = value;

        //计算节点高度
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        //计算平衡因子
        int balanceFlactor = getBalanceFactor(node);

        // 平衡维护
        //////////////////////////////////////////////////////
        // LL  T1<Z<T2< X <T3<Y<T4                          //
        //        y                              x          //
        //       / \                           /   \        //
        //      x   T4     向右旋转 (y)        z     y       //
        //     / \       - - - - - - - ->    / \   / \      //
        //    z   T3                        T1 T2 T3 T4     //
        //   / \                                            //
        // T1   T2                                          //
        //////////////////////////////////////////////////////
        if (balanceFlactor > 1 && getBalanceFactor(node.left) >= 0)
            return rightRoate(node);

        //////////////////////////////////////////////////////////////////////////////////////////
        //  LR  T1<X<T2< Z <T3<Y<T4                                                             //
        //         y                                y                              z            //
        //        / \                              / \                           /   \          //
        //       x  t4    向左旋转（x）             z   T4      向右旋转（y）       x     y         //
        //      / \     --------------->         / \        --------------->   / \   / \        //
        //     T1  z                            x   T3                        T1  T2 T3 T4      //
        //        / \                          / \                                              //
        //       T2  T3                      T1   T2                                            //
        //////////////////////////////////////////////////////////////////////////////////////////
        if (balanceFlactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRoate(node.left);
            return rightRoate(node);
        }

        //////////////////////////////////////////////////
        // RR: T1<Y<T2< X <T3<Z<T4                      //
        //    y                              x          //
        //  /  \                           /   \        //
        // T1   x      向左旋转 (y)        y     z       //
        //     / \   - - - - - - - ->    / \   / \      //
        //   T2   z                     T1 T2 T3 T4     //
        //       / \                                    //
        //      T3 T4                                   //
        //////////////////////////////////////////////////
        if (balanceFlactor < -1 && getBalanceFactor(node.right) <= 0)
            return leftRoate(node);

        //////////////////////////////////////////////////////////////////////////////////////////
        // RL: T1<Y<T2< Z <T3<X<T4                                                              //
        //      y                           y                                       z           //
        //     / \                         / \                                    /   \         //
        //    T1  x       向右旋转（x）     T1  z         向左旋转（y）              y     x        //
        //       / \    - - - - - - ->       / \      - - - - - - - - ->        / \   / \       //
        //      z  T4                       T2  x                              T1 T2 T3 T4      //
        //     / \                             / \                                              //
        //    T2  T3                          T3  T4                                            //
        //////////////////////////////////////////////////////////////////////////////////////////
        if (balanceFlactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRoate(node.right);
            return leftRoate(node);
        }
        return node;
    }

    public V remove(K key) {
        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    private Node remove(Node node, K key) {
        if (node == null)
            return null;
        Node retNode;
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            retNode = node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            retNode = node;
        } else {
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                retNode = rightNode;
            } else if (node.right == null) {
                Node nodeLeft = node.left;
                node.left = null;
                size--;
                retNode = nodeLeft;
            } else {
                Node successor = mininum(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;
                node.left = node.right = null;
                retNode = successor;
            }
        }

        if (retNode == null)
            return null;

        //更新height
        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));

        //平衡维护
        int balanceFactor = getBalanceFactor(retNode);

        //LL
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0)
            return rightRoate(retNode);

        //RR
        if (balanceFactor > 1 && getBalanceFactor(retNode.right) <= 0)
            return leftRoate(retNode);

        //LR
        if (balanceFactor < -1 && getBalanceFactor(retNode.left) < 0) {
            retNode.left = leftRoate(retNode.left);
            return rightRoate(retNode);
        }

        //RL
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            retNode.right = rightRoate(retNode.right);
            return leftRoate(retNode);
        }

        return retNode;
    }

    private Node mininum(Node node) {
        if (node.left == null)
            return node;
        return mininum(node.left);
    }

    /**
     * 查找节点
     * @param node
     * @param key
     * @return
     */
    public Node getNode(Node node, K key) {
        if (node == null)
            return null;
        if (key.equals(node.key))
            return node;
        else if (key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else
            return getNode(node.right, key);
    }

    /**
     * 是否存在
     * @param key
     * @return
     */
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V value) {
        Node node = getNode(root, key);
        if (node == null)
            throw new IllegalArgumentException(key + "doesn't exist!");
        node.value = value;
    }

    /**
     * 右旋调整
     * @param y
     * @return
     */
    ///////////////////////////////////////////////////
    // LL T1<Z<T2< X <T3<Y<T4                        //
    //        y                              x       //
    //       / \                           /   \     //
    //      x   T4     向右旋转 (y)        z     y    //
    //     / \       - - - - - - - ->    / \   / \   //
    //    z   T3                        T1 T2 T3 T4  //
    //   / \                                         //
    // T1   T2                                       //
    ///////////////////////////////////////////////////
    private Node rightRoate(Node y) {
        Node x = y.left;
        Node T3 = x.right;
        x.right = x;
        y.left = T3;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    /**
     * 左旋调整
     * @param y
     * @return
     */
    ////////////////////////////////////////////////
    // RR T1<Y<T2< X <T3<Z<T4                     //
    //    y                             x         //
    //  /  \                          /   \       //
    // T1   x      向左旋转 (y)       y     z      //
    //     / \   - - - - - - - ->   / \   / \     //
    //    T2  z                    T1 T2 T3 T4    //
    //       / \                                  //
    //      T3 T4                                 //
    ////////////////////////////////////////////////
    private Node leftRoate(Node y) {
        Node x = y.right;
        Node T2 = x.left;
        x.left = y;
        y.right = T2;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    private void inOrder(Node node, ArrayList<K> keys) {
        if (node == null)
            return;
        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }

    public boolean isBST() {
        ArrayList<K> keys = new ArrayList<K>();
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++)
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0)
                return false;
        return true;
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node node) {
        if (node == null)
            return true;
        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1)
            return false;
        return isBalanced(node.left) && isBalanced(node.right);
    }

    public static void main(String[] args) {
        AVL<Integer, Integer> avl = new AVL<>();
        for (int i = 0; i < 10; i++) {
            avl.add(i, i);
        }
        System.out.println(avl.isBST());
        System.out.println(avl.isBalanced());
        avl.remove(5);
        System.out.println(avl.isBST());
        System.out.println(avl.isBalanced());
    }
}