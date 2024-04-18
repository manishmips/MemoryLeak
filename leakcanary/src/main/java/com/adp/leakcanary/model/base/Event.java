package com.adp.leakcanary.model.base;

public class Event<T> {

    private T data;
    private boolean isAlreadyHandled;

    public Event(T data) {
        this.data = data;
    }

    public T getData() {
        if (isAlreadyHandled) {
            return null;
        }

        isAlreadyHandled = true;
        return data;
    }

    public boolean isAlreadyHandled() {
        return isAlreadyHandled;
    }

    public T peekContent() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}