package com.dxogo;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TqsStack<T>{

private final ArrayList<T> list = new ArrayList<>();
  private Integer bound = null;

  public TqsStack() {}

  public TqsStack(int bound) { this.bound = bound; }

  // push(x): add an item on the top
  public void push(T x) {
    if (size() == bound) throw new IllegalStateException();
    list.add(x);
  }

  // pop: remove the item at the top
  public T pop() { 
    T elem;

    try {
      elem = list.remove(size() - 1);
    } catch (IndexOutOfBoundsException e) {
      throw new NoSuchElementException();
    }

    return elem;
  }

  // peek: return the item at the top (without removing it)
  public T peek() {
    T elem;

    try {
      elem = list.get(size() - 1);
    } catch (IndexOutOfBoundsException e) {
      throw new NoSuchElementException();
    }

    return elem;
  }

  // size: return the number of items in the stack
  public int size() { return list.size(); }

  // isEmpty: return whether the stack has no items
  public boolean isEmpty() { return list.isEmpty(); }


}