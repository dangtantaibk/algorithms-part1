/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // size of grid
    private final int n;

    // weighted Quick Union UF
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final WeightedQuickUnionUF weightedQuickUnionUF2;

    // array to store the grid in one dimension DS
    private boolean[] arr;

    // counter number of open sites in the grid
    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        numberOfOpenSites = 0;
        arr = new boolean[n * n + 2];
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        weightedQuickUnionUF2 = new WeightedQuickUnionUF(n * n + 1);
    }

    private boolean isSiteValid(int row, int col) {
        return row < 1 || col < 1 || row > this.n || col > this.n;
    }

    private int getArrIndex(int row, int col) {
        return (row-1) * this.n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isSiteValid(row, col)) {
            throw new IllegalArgumentException();
        }

        int index = getArrIndex(row, col);

        if (!isOpen(row, col)) {
            numberOfOpenSites++;
            this.arr[index] = true;

            if (row == 1) {
                weightedQuickUnionUF.union(index, 0);
                weightedQuickUnionUF2.union(index, 0);
            }

            if (row == this.n) {
                weightedQuickUnionUF.union(index, this.n * this.n + 1);
            }

            // top site
            if (row > 1 && isOpen(row-1, col)) {
                weightedQuickUnionUF.union(index, getArrIndex(row-1, col));
                weightedQuickUnionUF2.union(index, getArrIndex(row-1, col));
            }

            // bottom site
            if (row < n && isOpen(row+1, col)) {
                weightedQuickUnionUF.union(index, getArrIndex(row+1, col));
                weightedQuickUnionUF2.union(index, getArrIndex(row+1, col));
            }

            // left site
            if (col > 1 && isOpen(row, col-1)) {
                weightedQuickUnionUF.union(index, getArrIndex(row, col-1));
                weightedQuickUnionUF2.union(index, getArrIndex(row, col-1));
            }

            // right site
            if (col < n && isOpen(row, col+1)) {
                weightedQuickUnionUF.union(index, getArrIndex(row, col+1));
                weightedQuickUnionUF2.union(index, getArrIndex(row, col+1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isSiteValid(row, col)) {
            throw new IllegalArgumentException();
        }
        return this.arr[getArrIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isSiteValid(row, col)) {
            throw new IllegalArgumentException();
        }

        return weightedQuickUnionUF2.connected(0, getArrIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(0, n*n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
