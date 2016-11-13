import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

  private int F; // index referring to the first item of queue
  private int L; // index referring to the last item of queue
  private Item[] itemArray; // main object of the queue which stores the items
  // private int extra = 0;

  public Deque() {
    F = -1;
    L = 0;
    itemArray = (Item[]) new Object[1];
  } // construct an empty deque

  public boolean isEmpty() {
    return F == L - 1;
  } // is the deque empty?

  public int size() {

    StdOut.println(L - F - 1);
    return (L - F - 1);
  } // return the number of items on the deque

  private void validItem(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
  }

  public void addFirst(Item item) {
    validItem(item);
    // StdOut.println(F);
    // StdOut.println(L);
    if (F == -1) {
      if (L - F == 1)
        resizeFirst(L - F);
      else
        resizeFirst(L - F - 1);
    }
    itemArray[F--] = item;

  } // add the item to the front

  public void addLast(Item item) {
    validItem(item);
    if (L == itemArray.length)

      if (L - F == 1) {
        resizeLast(L - F - 1);
        L--;
        F--;
      } else {
        resizeLast(L - F - 1);
      }

    itemArray[L++] = item;
  } // add the item to the end

  public Item removeFirst() {

    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }

    Item item = itemArray[++F];
    itemArray[F] = null;
    if (F > 0 && L - F - 1 <= L / 4)
      resizeFirst(-L / 2);

    // StdOut.println("F: " + F);
    // StdOut.println("L: " + L);

    return item;
  } // remove and return the item from the front

  public Item removeLast() {

    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }

    Item item = itemArray[--L];
    itemArray[L] = null;
    if (L > 0 && L - F - 1 <= (itemArray.length - F - 1) / 4)
      resizeLast(-(itemArray.length - F - 1) / 2);
    // StdOut.println("L: " +L);
    StdOut.println("F: " + F);
    StdOut.println("L: " + L);

    return item;
  } // remove and return the item from the end

  private void resizeFirst(int change) {
    // StdOut.println();
    // StdOut.println("resizeFirst F before change: " + F);
    // StdOut.println("resizeFirst L before change: " + L);
    //
    // StdOut.println("change: " + change);
    // StdOut.println("itemArray.length: " + itemArray.length);
    Item[] copy = (Item[]) new Object[change + itemArray.length];
    F += change;
    L += change;
    // StdOut.println("resizeFirst F: " + F);
    // StdOut.println("resizeFirst L: " + L);
    for (int i = 0; i < Math.min(copy.length, itemArray.length); i++) {
      // StdOut.println("i: " + i);
      copy[copy.length - 1 - i] = itemArray[itemArray.length - 1 - i];
    }
    itemArray = copy;
  }

  private void resizeLast(int change) {
    // StdOut.println("-L/2: " + change);
    // StdOut.println("itemArray.length: " + itemArray.length);
    Item[] copy = (Item[]) new Object[change + itemArray.length];
    for (int i = 0; i < Math.min(copy.length, itemArray.length); i++) {

      copy[i] = itemArray[i];
    }
    itemArray = copy;
  }

  public Iterator<Item> iterator() {
    return new dequeIterator();
  } // return an iterator over items in order from front to end

  private class dequeIterator implements Iterator<Item> {
    private int itr = F + 1;

    public boolean hasNext() {
      return itr < L;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {

      if (L - itr == 1) {
        throw new NoSuchElementException();
      }

      return itemArray[itr++];
    }
  }

  // public void arrayPrint() {
  // for (int i = 0; i < (Integer) this.itemArray.length; i++)
  // StdOut.print((Integer) this.itemArray[i] + " ");
  // }

  public static void main(String[] args) {
  } // unit testing
}