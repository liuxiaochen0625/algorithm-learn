/**
 * reus
 * Copyright (C), 2011 - 2019, reus.
 */
package org.reus.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @version $Id: BST.java, v 0.1 2019-12-26 reus Exp $
 * @ClassName: BST
 * @Description: 二叉搜索树
 * @author: reus
 */
public class BST<E extends Comparable> {

    /** 节点内部类 */
    private class Node {
        private E    e;
        private Node left;
        private Node right;

        public Node(E e) {
            this.e = e;
        }
    }

    /** 根节点 */
    private Node root;

    /** 元素个数 */
    private int  size;

    public BST() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Getter method for property <tt>size</tt>.
     *
     * @return property value of size
     */
    public int getSize() {
        return size;
    }

    /** 二叉搜索树是否为空 */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * 向树中添加节点
     * @param e
     */
    public void add(E e) {
        root = add(root, e);
    }

    /**
     * 向某个节点中添加节点 递归实现
     * @param node
     * @param e
     * @return 返回根结点
     */
    private Node add(Node node, E e) {
        //当前节点为空，将元素放到当前节点
        if (node == null) {
            size++;
            return new Node(e);
        } else if (e.compareTo(node.e) < 0) {
            node.left = add(node.left, e);
        } else {
            node.right = add(node.right, e);
        }
        return node;
    }

    /**
     * 查询树中是否含有e
     * @param e
     * @return
     */
    public boolean contains(E e) {
        return contains(root, e);
    }

    /**
     * 查询节点中是否存在e
     * @param node
     * @param e
     * @return
     */
    private boolean contains(Node node, E e) {
        if (node == null)
            return false;
        else if (e.compareTo(node.e) == 0)
            return true;
        else if (e.compareTo(node.e) > 0)
            return contains(node.right, e);
        else
            return contains(node.left, e);
    }

    /**
     * 查询最小节点
     * @param node
     * @return
     */
    public Node mininum(Node node) {
        if (node.left == null)
            return node;
        else
            return mininum(node.left);
    }

    /**
     * 获取最小的节点
     * @param node
     * @return
     */
    public E min(Node node) {
        return mininum(node).e;
    }

    /**
     * 获取最大的节点
     * @param node
     * @return
     */
    public E maxinum(Node node) {
        return max(node).e;
    }

    /**
     * 查询最大节点
     * @param node
     * @return
     */
    public Node max(Node node) {
        if (node.right == null)
            return node;
        else
            return max(node.right);
    }

    /**
     * 移除最小的节点
     * @return
     */
    public E removeMin() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");
        E e = min(root);
        root = removeMin(root);
        return e;
    }

    /**
     * 移除最小的节点
     * @param node
     * @return
     */
    public Node removeMin(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        } else {
            node.left = removeMin(node.left);
            return node;
        }
    }

    /**
     * 移除最大的节点
     * @return
     */
    public E removeMax() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");
        root = removeMax(root);
        E e = maxinum(root);
        return e;
    }

    /**
     * 移除最大的节点
     * @param node
     * @return
     */
    public Node removeMax(Node node) {
        if (node.right == null) {
            Node leftNode = node.left;
            size--;
            node.left = null;
            return leftNode;
        } else {
            node.right = removeMax(node.right);
            return node;
        }
    }

    /**
     * 删除树中的某个节点
     * @param e
     */
    public void remove(E e) {
        root = remove(root, e);
    }

    /**
     * 删除树中的某个节点
     * @param node
     * @param e
     * @return
     */
    public Node remove(Node node, E e) {
        if (node == null)
            return null;
        else if (e.compareTo(node.e) < 0) {
            node.left = remove(node.left, e);
            return node;
        } else if (e.compareTo(node.e) > 0) {
            node.right = remove(node.right, e);
            return node;
        } else {
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            } else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            } else {
                //找出右子树的最小节点作为根结点
                Node successor = mininum(node.right);
                //根结点的右子树为原右子树移除最小节点
                successor.right = removeMin(node.right);
                //根结点的左子树为原节点左子树
                successor.left = node.left;
                //将node节点左右子树置空以便gc
                node.left = node.right = null;
                return successor;
            }
        }
    }

    /**
     * 先序遍历
     */
    public void preOrder() {
        preOrder(root);
    }

    /**
     * 先序遍历
     * @param node
     */
    private void preOrder(Node node) {
        if (node == null)
            return;
        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 先序遍历  非递归
     */
    public void preOrderNR() {
        preOrderNR(root);
    }

    /**
     * 先序遍历  非递归
     * @param node
     */
    private void preOrderNR(Node node) {
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            System.out.println(cur.e);

            if (cur.right != null)
                stack.push(node.right);

            if (cur.left != null)
                stack.push(node.left);
        }
    }

    /**
     * 中序遍历
     */
    public void inOrder() {
        inOrder(root);
    }

    /**
     * 中序遍历
     * @param node
     */
    private void inOrder(Node node) {
        if (node == null)
            return;
        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }

    /**
     * 中序遍历   非递归
     */
    public void inOrderNR() {
        inOrderNR(root);
    }

    /**
     * 中序遍历   非递归
     * @param node
     */
    public void inOrderNR(Node node) {
        Stack<Node> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            System.out.println(node.e);
            node = node.right;
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        postOrder(root);
    }

    /**
     * 后序遍历
     * @param node
     */
    public void postOrder(Node node) {
        if (node == null)
            return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);
    }

    /**
     * 后序遍历   非递归
     */
    public void postOrderNR() {
        postOrderNR(root);
    }

    /**
     * 后序遍历   非递归
     * @param node
     */
    public void postOrderNR(Node node) {
        Stack<Node> stack = new Stack<>();
        Stack<Node> out = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            out.push(cur);
            if (cur.left != null)
                stack.push(cur.left);
            if (cur.right != null)
                stack.push(cur.right);
        }
        while (!out.isEmpty())
            System.out.println(out.pop().e);
    }

    /**
     * 层次优先遍历
     */
    public void levelOrder() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.println(node.e);
            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
        }
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
    private Node rightRoate(Node y){
        Node x = y.left;
        Node T3 = x.right;
        x.right = x;
        y.left = T3;
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
    private Node leftRoate(Node y){
        Node x = y.right;
        Node T2 = x.left;
        x.left = y;
        y.right = T2;
        return x;
    }

    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        bst.add(3);
        bst.add(8);
        bst.add(1);
        bst.add(2);
        bst.add(10);
        bst.add(30);
        bst.add(8);
        bst.preOrder();
    }

}