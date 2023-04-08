package index;

import java.util.Iterator;

public class DoubleThreadedBinarySearchTree implements Iterator {
    // tracks the number of nodes in this DTBST
    private int nodeCount = 0;

    // this DTBST is a rooted tree, so we maintain a reference to the root node
    private Node root = null;

    private Node iterator = root;
    private int iteratorCounter = 0;

    // internal node class containing node references
    // and actual node data
    private static class Node {
        IndexRecord data;
        Node left, right;
        boolean leftThread;
        boolean rightThread;

        public Node(IndexRecord data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return this.data.toString();
        }
    }

    // check if this binary tree is empty
    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return nodeCount;
    }

    // add an element to this binary tree. returns true
    // if we successfully perform an insertion
    public boolean insert(IndexRecord elem) {

        // check if this value already exists in this binary tree

        Node cur = root;
        Node parent = null; // parent of elem to be added
        while (cur != null) {

            if (cur.data.equals(elem)) {
                System.out.println("Duplicate key");
                return false;
            }

            parent = cur;

            // If the new element is less than the current node's data, move left
            if (elem.compareTo(cur.data) < 0) {
                if (!cur.leftThread)
                    cur = cur.left;
                else
                    break;
            }

            // If the new element is greater than or equal to the current node's data, move right
            else {
                if (!cur.rightThread)
                    cur = cur.right;
                else
                    break;
            }
        }

        // create a new node
        Node newNode = new Node(elem);
        newNode.data = elem;
        newNode.leftThread = true;
        newNode.rightThread = true;

        if (parent == null) {
            root = newNode;
            root.left = null;
            root.right = null;
            iterator = root;
        } else if (elem.compareTo(parent.data) < 0) {
            newNode.left = parent.left;
            newNode.right = parent;
            parent.leftThread = false;
            parent.left = newNode;
        } else {
            newNode.left = parent;
            newNode.right = parent.right;
            parent.rightThread = false;
            parent.right = newNode;
        }
        nodeCount++;
        return true;
    }

    // remove a value from this binary tree, if it exists
    // returns the new root of bst
    public Node remove(String obj) {

        // initialize parent as null, cur to root
        Node parent = null;
        Node cur = root;

        // set true if key
        boolean found = false;

        // search key in tree, find node, and it's parent
        while (cur != null) {
            if (cur.data.equals(obj)) {
                found = true;
                break;
            }

            parent = cur;

            if (cur.data.compareTo(obj) > 0) {
                if (!parent.leftThread)
                    cur = cur.left;
                else
                    break;
            } else {
                if (!parent.rightThread)
                    cur = cur.right;
                else
                    break;
            }
        }

        if (!found) {
            System.out.println("The record was not found in tree");
        }

        // deletion handling

        // node has 2 children
        else if (!cur.leftThread && !cur.rightThread)
            deleteWithTwoChild(parent, cur);
        // node has left child
        else if (!cur.leftThread)
            deleteWithOneChild(parent, cur);
        // node has right child
        else if (!cur.rightThread)
            deleteWithOneChild(parent, cur);
        // node has no children
        else
            deleteWithNoChild(parent, cur);
        nodeCount--;
        return cur;
    }

    // uses the inorder successor to replace the deleted node
    private void deleteWithTwoChild(Node parent, Node cur) {

        // find inorder successor and its parent
        Node parentOfSuccessor = cur;
        Node successor = cur.right;

        // find leftmost child of successor
        while (!successor.leftThread) {
            parentOfSuccessor = successor;
            successor = successor.left;
        }

        cur.data = successor.data;
        if (successor.leftThread && successor.rightThread)
            deleteWithNoChild(parentOfSuccessor, successor);
        else
            deleteWithOneChild(parentOfSuccessor,successor);
    }

    private void deleteWithOneChild(Node parent, Node cur) {
        Node child;

        // node has left child
        if (!cur.leftThread)
            child = cur.left;
        // node has right child
        else
            child = cur.right;

        // node is root node
        if (parent == null) {
            root = child;
        }

        // node is left child of it's parent
        else if (cur == parent.left)
            parent.left = child;
        else
            parent.right = child;

        // Find successor and predecessor
        Node successor = inorderSuccessor(cur);
        Node predecessor = inorderPredecessor(cur);

        // if cur has left subtree
        if (!cur.leftThread)
            predecessor.right = successor;

        // cur has right subtree
        else {
            if (!cur.rightThread) {
                successor.left = predecessor;
            }
        }
    }

    private Node deleteWithNoChild(Node parent, Node cur) {
        // delete root node
        if (parent == null)
            root = null;

        // if node is left child of it's parent
        else if (cur == parent.left) {
            parent.leftThread = true;
            parent.left = cur.left;
        } else {
            parent.rightThread = true;
            parent.right = cur.right;
        }
        return cur;
    }

    // returns inorder successor
    private Node inorderSuccessor(Node cur) {

        // right child is inorder successor
        if (cur.rightThread)
            return cur.right;

        // return left most child of right subtree
        cur = cur.right;
        while (!cur.leftThread)
            cur = cur.left;
        return cur;
    }

    // returns the inorder predecessor
    private Node inorderPredecessor(Node cur) {

        // left child is inorder predecessor
        if (cur.leftThread)
            return cur.left;

        // return right most child of left subtree
        cur = cur.left;
        while (!cur.rightThread)
            cur = cur.right;
        return cur;
    }


    // print the threaded tree in order
    public void printInorder() {
        if (root == null)
            System.out.println("Tree is empty");

        // find the leftmost node to start
        Node cur = root;
        while (!cur.leftThread)
            cur = cur.left;

        // print successors
        while (cur != null) {
            System.out.println(cur.data);
            cur = inorderSuccessor(cur);
        }
    }

    public boolean contains(String obj) {
        if (isEmpty())
            return false;
        return get(obj).getKey().equals(obj);
    }

    public IndexRecord get(String obj) {

        // initialize parent as null, cur to root
        Node parent = null;
        Node cur = root;

        // search key in tree, find node, and it's parent
        while (cur != null) {
            if (cur.data.equals(obj)) {
                break;
            }

            parent = cur;

            if (cur.data.compareTo(obj) > 0) {
                if (!parent.leftThread)
                    cur = cur.left;
                else
                    break;
            } else {
                if (!parent.rightThread)
                    cur = cur.right;
                else
                    break;
            }
        }

        return cur.data;
    }

    public IndexRecord get(int index) {
        // Check if index is in bounds
        if (index < 0 || index >= nodeCount)
            throw new IndexOutOfBoundsException();

        int count = 0;

        Node cur = root;
        while (!cur.leftThread)
            cur = cur.left;

        // print successors
        while (cur != null && count < index) {
            cur = inorderSuccessor(cur);
            count++;
        }
        return cur.data;
    }

    public Node getIterator() {
        return iterator;
    }

    public void setIteratorHead() {
        if (root == null)
            System.out.println("Tree is empty");

        iteratorCounter = 0;
        // find the leftmost node to start
        iterator = root;
        while (!iterator.leftThread)
            iterator = iterator.left;
    }

    public void setIteratorTail() {
        if (root == null)
            System.out.println("Tree is empty");

        iteratorCounter = nodeCount;

        // find the rightmost node to start
        iterator = root;
        while (!iterator.rightThread)
            iterator = iterator.right;
    }

    @Override
    public boolean hasNext() {
        return iteratorCounter <= nodeCount - 1;
    }


    @Override
    public IndexRecord next() {
        IndexRecord indexRecord = iterator.data;
        iterator = inorderSuccessor(iterator);
        iteratorCounter++;
        return indexRecord;
    }

    public boolean hasPrevious() {
        return iteratorCounter > 0;
    }

    public IndexRecord previous() {
        IndexRecord indexRecord = iterator.data;
        iterator = inorderPredecessor(iterator);
        iteratorCounter--;
        return indexRecord;
    }
}
