import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.util.Arrays;
//import java.lang.IllegalArgumentException;

public class PercolationStats {
	// Percolation[] percol_exp;
	private double[] p_array;

	public PercolationStats(int n, int trials) { // perform trials independent
													// experiments on an n-by-n
													// grid
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}
		p_array = new double[trials];
		for (int i = 0; i < trials; i++) {
			p_array[i] = 0;
			Percolation percol_exp = new Percolation(n);
			int p = 0;
			while (percol_exp.percolates() != true) {
				// StdRandom rand = new StdRandom();
				int row = StdRandom.uniform(n) + 1;
				int col = StdRandom.uniform(n) + 1;
				if (percol_exp.isFull(row, col)) {
					percol_exp.open(row, col);
					p++;
				}
			}
			p_array[i] = (double) p / (n * n);
		}
		Arrays.sort(p_array);
	}

	public double mean() {
		return StdStats.mean(p_array);
	} // sample mean of percolation threshold

	public double stddev() {
		return StdStats.stddev(p_array);
	} // sample standard deviation of percolation threshold

	public double confidenceLo() {
		return (this.mean() - 1.96 * this.stddev() / Math.sqrt(p_array.length));
	} // low endpoint of 95% confidence interval

	public double confidenceHi() {
		return (this.mean() + 1.96 * this.stddev() / Math.sqrt(p_array.length));
	} // high endpoint of 95% confidence interval

	public static void main(String[] args) {
		PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		StdOut.println("mean                    = " + percolationStats.mean());
		StdOut.println("stddev                  = " + percolationStats.stddev());
		StdOut.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
	} // test client (described below)
}