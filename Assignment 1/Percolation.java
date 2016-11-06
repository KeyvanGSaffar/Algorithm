import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private int sideNumber;
  private int[][] siteStatus;
  private WeightedQuickUnionUF grid;

  public Percolation(int n) { // create n-by-n grid, with all sites blocked
    grid = new WeightedQuickUnionUF(n * n + 2);
    siteStatus = new int[n + 2][n + 2];
    for (int i = 0; i < n + 2; i++) {
      for (int j = 0; j < n + 2; j++) {
        siteStatus[i][j] = 0; // zero means that the site is blocked
        // (full)
      }
    }
    // for (int i = 1; i <= n; i++) {
    // grid.union(0, i);
    // grid.union(n * n + 1, n * n + 1 - (i));
    // }
    this.sideNumber = n;
  }

  private void valid(int row, int col) {
    if (row < 1 || row > this.sideNumber || col < 1 || col > this.sideNumber) {
      throw new IndexOutOfBoundsException();
    }
  }

  public void open(int row, int col) { // open site (row, col) if it is not

    valid(row, col);
    // row = row - 1;
    // col = col - 1;
    if (!this.isOpen(row, col)) {
      siteStatus[row][col] = 1;

      if (row == 1) {
        grid.union(0, (row - 1) * this.sideNumber + (col - 1) + 1);
      }
      if (row == this.sideNumber) {
        grid.union(this.sideNumber * this.sideNumber + 1, (row - 1) * this.sideNumber + (col - 1) + 1);
      }

      if (row > 1) {
        if (this.isOpen(row - 1, col)) {
          grid.union((row - 1) * this.sideNumber + (col - 1) + 1, ((row - 1) - 1) * this.sideNumber + (col - 1) + 1);
        }
      }
      if (row < this.sideNumber) {
        if (this.isOpen(row + 1, col)) {
          grid.union((row - 1) * this.sideNumber + (col - 1) + 1, ((row - 1) + 1) * this.sideNumber + (col - 1) + 1);
        }
      }
      if (col > 1) {
        if (this.isOpen(row, col - 1)) {
          grid.union((row - 1) * this.sideNumber + (col - 1) + 1, (row - 1) * this.sideNumber + (col - 1));
        }
      }
      if (col < this.sideNumber) {
        if (this.isOpen(row, col + 1)) {
          grid.union((row - 1) * this.sideNumber + (col - 1) + 1, (row - 1) * this.sideNumber + (col - 1) + 2);
        }
      }
    }

    // for (int i = 1; i <= this.sideNumber; i++) {
    // if (!grid.connected(this.sideNumber * this.sideNumber + 1, (row - 1) *
    // this.sideNumber + (col - 1) + 1)
    // && this.isFull(this.sideNumber, i)) {
    // grid.union(this.sideNumber * this.sideNumber + 1, (row - 1) *
    // this.sideNumber + (col - 1) + 1);
    // }
    // }

  }

  public boolean isOpen(int row, int col) { // is site (row, col) open?

    valid(row, col);
    // row = row - 1;
    // col = col - 1;
    if (siteStatus[row][col] == 1) {
      return true;
    }
    return false;
  }

  public boolean isFull(int row, int col) { // is site (row, col) full?

    valid(row, col);
    // row = row - 1;
    // col = col - 1;
    if (this.isOpen(row, col)) {
      if (grid.connected((row - 1) * this.sideNumber + (col - 1) + 1, 0)) {
        return true;
      }
    }
    return false;
  }

  public boolean percolates() { // does the system percolate?
    return grid.connected(0, this.sideNumber * this.sideNumber + 1);
  }

  public static void main(String[] args) {
    Percolation test = new Percolation(6);
    test.open(1, 6);
    test.open(2, 6);
    test.open(3, 6);
    test.open(4, 6);
    test.open(5, 6);
    test.open(6, 6);
    test.open(6, 3);
    StdOut.println(test.isFull(6, 3));

  }

}