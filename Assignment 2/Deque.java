import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

  private int F; // index referring to the first item of queue
  private int L; // index referring to the last item of queue
  public Item[] itemArray; // main object of the queue which stores the items
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

  public void addFirst(Item item) {

    StdOut.println(F);
    StdOut.println(L);
    if (F == -1) {
      if (L - F == 1)
        resizeFirst(L - F);
      else
        resizeFirst(L - F - 1);
    }
    itemArray[F--] = item;

  } // add the item to the front

  public void addLast(Item item) {
    if (L == itemArray.length)
      resizeLast(L - F - 1);
    itemArray[L++] = item;
  } // add the item to the end

  public Item removeFirst() {

    Item item = itemArray[++F];
    itemArray[F] = null;
    if (F > 0 && L - F - 1 <= L / 4)
      resizeFirst(-L / 2);

//    StdOut.println("F: " + F);
//    StdOut.println("L: " + L);
   
    return item;
  } // remove and return the item from the front

  public Item removeLast() {
    Item item = itemArray[--L];
    itemArray[L] = null;
    if (L > 0 && L - F - 1 <= (itemArray.length - F - 1) / 4)
      resizeLast(-(itemArray.length - F - 1) / 2);
    // StdOut.println("L: " +L);
    return item;
  } // remove and return the item from the end

  private void resizeFirst(int change) {
    StdOut.println();
    StdOut.println("resizeFirst F before change: " + F);
    StdOut.println("resizeFirst L before change: " + L);

    StdOut.println("change: " + change);
    StdOut.println("itemArray.length: " + itemArray.length);
    Item[] copy = (Item[]) new Object[change + itemArray.length];
    F += change;
    L += change;
    StdOut.println("resizeFirst F: " + F);
    StdOut.println("resizeFirst L: " + L);
    for (int i = 0; i < Math.min(copy.length, itemArray.length); i++){
      StdOut.println("i: " + i);
      copy[copy.length-1-i] = itemArray[itemArray.length-1-i];
    }
    itemArray = copy;
  }

  private void resizeLast(int change) {
    StdOut.println("-L/2: " + change);
    StdOut.println("itemArray.length: " + itemArray.length);
    Item[] copy = (Item[]) new Object[change + itemArray.length];
    for (int i = 0; i < Math.min(copy.length, itemArray.length); i++) {
      
      copy[i] = itemArray[i];
    }
    itemArray = copy;
  }

  public Iterator<Item> iterator() {
    return null;
  } // return an iterator over items in order from front to end

  public void arrayPrint() {
    for (int i = 0; i < (Integer) this.itemArray.length; i++)
      StdOut.print((Integer) this.itemArray[i] + " ");
  }

  public static void main(String[] args) {
    Deque<Integer> d_i = new Deque<Integer>();

    d_i.addLast(2);
    d_i.addLast(3);
    d_i.addLast(6);
    d_i.addLast(8);
    d_i.addLast(10);
    d_i.removeLast();
    d_i.removeLast();
    d_i.removeLast();
//    d_i.addFirst(4);
//    d_i.removeFirst();
//    d_i.removeFirst();

    d_i.addFirst(2);
    d_i.addFirst(3);
    d_i.addFirst(4);
//
    d_i.removeFirst();
    d_i.removeFirst();
    d_i.removeFirst();
    d_i.arrayPrint();

    // for(int i=0; i < (Integer)d_i.itemArray.length ; i++)
    // StdOut.print((Integer)d_i.itemArray[i] + " ");
  } // unit testing
}