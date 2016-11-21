import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private int first; // index referring to the first item of queue
  private int last; // index referring to the last item of queue
  private Item[] itemArray; // main object of the queue which stores the items
  // private int extra = 0;

  public Deque() {
    first = -1;
    last = 0;
    itemArray = (Item[]) new Object[1];
  } // construct an empty deque

  public boolean isEmpty() {
    return first == last - 1;
  } // is the deque empty?

  public int size() {

    // StdOut.println(last - first - 1);
    return (last - first - 1);
  } // return the number of items on the deque

  private void validItem(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
  }

  public void addFirst(Item item) {
    validItem(item);
    if (first == -1) {
      if (last - first == 1)
        resizeFirst(last - first);
      else
        resizeFirst(last - first - 1);
    }
    itemArray[first--] = item;

  } // add the item to the front

  public void addLast(Item item) {
    validItem(item);
    if (last == itemArray.length)

      if (last - first == 1) {
        resizeLast(last - first - 1);
        last--;
        first--;
      } else {
        resizeLast(last - first - 1);
      }

    itemArray[last++] = item;
  } // add the item to the end

  public Item removeFirst() {

    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }

    Item item = itemArray[++first];
    itemArray[first] = null;
    if (first > 0 && last - first - 1 <= last / 4)
      resizeFirst(-last / 2);
    return item;
  } // remove and return the item from the front

  public Item removeLast() {

    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }

    Item item = itemArray[--last];
    itemArray[last] = null;
    if (last > 0 && last - first - 1 <= (itemArray.length - first - 1) / 4)
      resizeLast(-(itemArray.length - first - 1) / 2);

    return item;
  } // remove and return the item from the end

  private void resizeFirst(int change) {

    Item[] copy = (Item[]) new Object[change + itemArray.length];
    first += change;
    last += change;
    for (int i = 0; i < Math.min(copy.length, itemArray.length); i++) {
      copy[copy.length - 1 - i] = itemArray[itemArray.length - 1 - i];
    }
    itemArray = copy;
  }

  private void resizeLast(int change) {
    Item[] copy = (Item[]) new Object[change + itemArray.length];
    for (int i = 0; i < Math.min(copy.length, itemArray.length); i++) {

      copy[i] = itemArray[i];
    }
    itemArray = copy;
  }

  public Iterator<Item> iterator() {
    return new DequeIterator();
  } // return an iterator over items in order from front to end

  private class DequeIterator implements Iterator<Item> {
    private int itr = first + 1;

    public boolean hasNext() {
      return itr < last;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {

      if (last - itr == 0) {
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