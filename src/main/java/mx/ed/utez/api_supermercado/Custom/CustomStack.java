package mx.ed.utez.api_supermercado.Custom;

import java.util.ArrayList;
import java.util.List;

public class CustomStack<T> {
    private List<T> stack;

    public CustomStack() {
        this.stack = new ArrayList<>();
    }

    public void push(T item) {
        stack.add(item);
    }

    public T pop() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.remove(stack.size() - 1);
    }

    public T peek() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.get(stack.size() - 1);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    // Getters and Setters
    public List<T> getStack() {
        return stack;
    }

    public void setStack(List<T> stack) {
        this.stack = stack;
    }
}
