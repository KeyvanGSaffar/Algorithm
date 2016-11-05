import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//import java.lang.IndexOutOfBoundsException;

public class Percolation {
	private int sideNumber;
	private int[] siteStatus;
	private WeightedQuickUnionUF grid;

	public Percolation(int n) { // create n-by-n grid, with all sites blocked
		grid = new WeightedQuickUnionUF(n * n + 2);
		siteStatus = new int[n * n];
		for (int i = 0; i < n * n; i++) {
			siteStatus[i] = 0; // zero means that the site is blocked (full)
		}
		for (int i = 1; i <= n; i++) {
			grid.union(0, i);
			grid.union(n * n + 1, n * n + 1 - (i));
		}
		this.sideNumber = n;
	}

	private void valid(int row, int col) {
		if (row < 1 || row > this.sideNumber || col < 1 || col > this.sideNumber) {
			throw new IndexOutOfBoundsException();
		}
	}

	public void open(int row, int col) { // open site (row, col) if it is not
											// open already
		valid(row, col);
		row = row - 1;
		col = col - 1;
		if (siteStatus[row * this.sideNumber + col] != 1) {
			siteStatus[row * this.sideNumber + col] = 1;
		}
		if (row > 0 && siteStatus[(row - 1) * this.sideNumber + col] == 1) {
			grid.union((row) * this.sideNumber + col, (row - 1) * this.sideNumber + col);
		}
		if (row < this.sideNumber - 1 && siteStatus[(row + 1) * this.sideNumber + col] == 1) {
			grid.union((row) * this.sideNumber + col, (row + 1) * this.sideNumber + col);
		}
		if (col > 0 && siteStatus[(row) * this.sideNumber + col - 1] == 1) {
			grid.union((row) * this.sideNumber + col, row * this.sideNumber + col - 1);
		}

		if (col < this.sideNumber - 1 && siteStatus[(row) * this.sideNumber + col + 1] == 1) {
			grid.union((row) * this.sideNumber + col, row * this.sideNumber + col + 1);
		}
	}

	public boolean isOpen(int row, int col) { // is site (row, col) open?

		valid(row, col);
		row = row - 1;
		col = col - 1;
		if (siteStatus[row * sideNumber + col] == 1) {
			return true;
		}
		return false;
	}

	public boolean isFull(int row, int col) { // is site (row, col) full?
		valid(row, col);
		row = row - 1;
		col = col - 1;
		if (siteStatus[row * sideNumber + col] == 0) {
			return true;
		}
		return false;
	}

	public boolean percolates() { // does the system percolate?
		return grid.connected(0, this.sideNumber * this.sideNumber + 1);
	}

}