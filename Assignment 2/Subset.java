import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
  public static void main(String[] args) {
    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> randQueue = new RandomizedQueue<String>();

    for (int i = 0; i < k; i++)
      randQueue.enqueue(StdIn.readString());

    for (int i = 0; i < k; i++)
      StdOut.println(randQueue.dequeue());
  }
}