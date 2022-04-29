package com.bobocode.cs;

import com.bobocode.util.ExerciseNotCompletedException;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * {@link RecursiveBinarySearchTree} is an implementation of a {@link BinarySearchTree} that is based on a linked nodes
 * and recursion. A tree node is represented as a nested class {@link Node}. It holds an element (a value) and
 * two references to the left and right child nodes.
 *
 * @param <T> a type of elements that are stored in the tree
 * @author Taras Boychuk
 * @author Maksym Stasiuk
 */
public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    private Node<T> root;
    private int size;

    private static class Node<T> {
        T element;
        Node<T> left;
        Node<T> right;

        public Node(T element) {
            this.element = element;
        }
    }

    public static <T extends Comparable<T>> RecursiveBinarySearchTree<T> of(T... elements) {
        RecursiveBinarySearchTree<T> recursiveBinarySearchTree = new RecursiveBinarySearchTree<>();
        Arrays.stream(elements).forEach(recursiveBinarySearchTree::insert);
        return recursiveBinarySearchTree;
    }

    @Override
    public boolean insert(T element) {
        Objects.requireNonNull(element);

        if (isEmpty()) {
            root = new Node<>(element);
            size++;
            return true;
        }
        return insert(element, root);
    }

    private boolean insert(T element, Node<T> current) {
        Node<T> newNode = new Node<>(element);
        if (current.element.compareTo(element) > 0) { // element are greater then element in current node -> go left
            if (current.left == null) {
                current.left = newNode;
                size++;
            } else {
                return insert(element, current.left);
            }
        } else if (current.element.compareTo(element) < 0) { // element are less then element in current node -> go right
            if (current.right == null) {
                current.right = newNode;
                size++;
            } else {
                return insert(element, current.right);
            }
        } else { // elements are equal
            return false;
        }
        return true;
    }

    @Override
    public boolean contains(T element) {
        Objects.requireNonNull(element);

        if (isEmpty()) return false;
        return contains(element, root);
    }

    private boolean contains(T element, Node<T> current) {
        if (current.element.compareTo(element) > 0) { // element are greater then element in current node -> go left
            if (current.left == null) return false;
            return contains(element, current.left);
        }
        if (current.element.compareTo(element) < 0) { // element are less then element in current node -> go right
            if (current.right == null) return false;
            return contains(element, current.right);
        }
        return true; // elements are equal
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int depth() {
        return root != null ? depth(root) - 1 : 0;
    }

    private int depth(Node<T> current) {
        if (current == null) return 0;
        return 1 + Math.max(depth(current.left), depth(current.right));
    }

    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        inOrderTraversal(consumer, root);
    }

    private void inOrderTraversal(Consumer<T> consumer, Node<T> current) {
        if (current != null) {
            inOrderTraversal(consumer, current.left);
            consumer.accept(current.element);
            inOrderTraversal(consumer, current.right);
        }
    }
}
