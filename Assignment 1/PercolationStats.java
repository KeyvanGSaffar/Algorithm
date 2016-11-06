import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.util.Arrays;

public class PercolationStats {
  // Percolation[] percol_exp;
  private double[] pArray;

  public PercolationStats(int n, int trials) { // perform trials independent
    // experiments on an n-by-n
    // grid
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
    pArray = new double[trials];
    for (int i = 0; i < trials; i++) {
      pArray[i] = 0;
      Percolation percolExp = new Percolation(n);
      int p = 0;
      while (!percolExp.percolates()) {
        // StdRandom rand = new StdRandom();
        int row = StdRandom.uniform(n) + 1;
        int col = StdRandom.uniform(n) + 1;
        // StdOut.println(row);
        // StdOut.println(col);
        if (!percolExp.isOpen(row, col)) {
          p++;
        }
        percolExp.open(row, col);

      }
      pArray[i] = (double) p / (n * n);
      // StdOut.println(pArray[i]);
    }
    Arrays.sort(pArray);
  }

  public double mean() {
    return StdStats.mean(pArray);
  } // sample mean of percolation threshold

  public double stddev() {
    return StdStats.stddev(pArray);
  } // sample standard deviation of percolation threshold

  public double confidenceLo() {
    return (this.mean() - 1.96 * this.stddev() / Math.sqrt(pArray.length));
  } // low endpoint of 95% confidence interval

  public double confidenceHi() {
    return (this.mean() + 1.96 * this.stddev() / Math.sqrt(pArray.length));
  } // high endpoint of 95% confidence interval

  public static void main(String[] args) {
    PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    StdOut.println("mean                    = " + percolationStats.mean());
    StdOut.println("stddev                  = " + percolationStats.stddev());
    StdOut.println(
        "95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
  } // test client (described below)
}