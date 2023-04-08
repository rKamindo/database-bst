package database;

import java.util.Arrays;
import java.util.EmptyStackException;

public class ArrayStack<T> {
    private T[] array;
    private int size;
    private int capacity;

    // default capacity is 16
    public ArrayStack() {
        this(16);
    }

    public ArrayStack(int capacity) {
        array = (T[]) new Object[capacity];
        this.capacity = capacity;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // adds an element to the top of the array
    public void push(T elem) {
        if (size == capacity) // time to resize
            increaseCapacity();
        array[size++] = elem;
    }

    // removes and returns the element at the top of the array
    public T pop() {
        if (isEmpty())
            throw new EmptyStackException();
        T data = array[--size];
        array[size] = null;
        return data;
    }

    // returns the element at the top of the array
    public T peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return array[size - 1];
    }

    // increases the capacity of the stack
    private void increaseCapacity() {
        capacity *= 2;
        array = Arrays.copyOf(array, capacity);
    }
}