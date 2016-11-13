import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private int tail;
  private Item[] itemArray;

  public RandomizedQueue() { // construct an empty randomized queue
    tail = 0;
    itemArray = (Item[]) new Object[1];
  }

  public boolean isEmpty() { // is the queue empty?
    return tail == 0;
  }

  public int size() { // return the number of items on the queue
    return tail;
  }

  private void validItem(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
  }

  public void enqueue(Item item) { // add the item
    validItem(item);
    if (tail == itemArray.length)
      resizeLast(tail);

    itemArray[tail++] = item;
  }

  public Item dequeue() { // remove and return a random item
    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }

    int rem = StdRandom.uniform(0, tail);

    Item item = itemArray[rem];
    itemArray[rem] = null;

    for (int i = rem + 1; i < tail; i++)
      itemArray[i - 1] = itemArray[i];
    itemArray[tail - 1] = null;
    tail--;

    if (tail > 0 && tail <= (itemArray.length) / 4)
      resizeLast(-(itemArray.length) / 2);
    return item;

  }

  private void resizeLast(int change) {
    // TODO Auto-generated method stub
    Item[] copy = (Item[]) new Object[change + itemArray.length];
    for (int i = 0; i < Math.min(copy.length, itemArray.length); i++) {

      copy[i] = itemArray[i];
    }
    itemArray = copy;
  }

  public Item sample() { // return (but do not remove) a random item
    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }
    int rem = StdRandom.uniform(0, tail);

    return itemArray[rem];
  }

  public Iterator<Item> iterator() { // return an independent iterator over
                                     // items in random order
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<Item> {
    private int itr = 0;

    @Override
    public boolean hasNext() {
      // TODO Auto-generated method stub
      return itr < tail;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {

      if (tail == itr) {
        throw new NoSuchElementException();
      }

      return itemArray[itr++];
    }

  }

  // public void arrayPrint() {
  // for (int i = 0; i < (Integer) this.itemArray.length; i++)
  // StdOut.print((Integer) this.itemArray[i] + " ");
  // }

  public static void main(String[] args) { // unit testing

  }
}