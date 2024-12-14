package mx.ed.utez.api_supermercado.Custom;

import java.util.LinkedList;
import java.util.Queue;

public class CustomQueue<T> {
    private Queue<T> queue;

    public CustomQueue() {
        this.queue = new LinkedList<>();
    }

    public void enqueue(T item) {
        queue.add(item);
    }

    public T dequeue() {
        if (queue.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return queue.poll();
    }

    public T peek() {
        if (queue.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return queue.peek();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    // Getters and Setters
    public Queue<T> getQueue() {
        return queue;
    }

}