import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private int numOpenSites;
    private WeightedQuickUnionUF uf;
    private boolean[] blocked;
    private boolean[] full;

    public Percolation(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException();
        }

        n = num;
        uf = new WeightedQuickUnionUF(n * n + 2);
        blocked = new boolean[n * n + 2];
        full = new boolean[n * n + 2];

        for (int i = 1; i <= n * n; ++i) blocked[i] = true;

        numOpenSites = 0;
    }

    private int index(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IndexOutOfBoundsException();
        }
        return (row - 1) * n + col;
    }

    public void open(int row, int col) {
        int id = index(row, col);
        if (!isOpen(row, col)) {
            numOpenSites++;
            blocked[id] = false;
            if (row - 1 > 0 && isOpen(row - 1, col)) {
                int q = index(row - 1, col);
                uf.union(id, q);
            }
            if (row + 1 <= n && isOpen(row + 1, col)) {
                int q = index(row + 1, col);
                uf.union(id, q);
            }
            if (col - 1 > 0 && isOpen(row, col - 1)) {
                int q = index(row, col - 1);
                uf.union(id, q);
            }
            if (col + 1 <= n && isOpen(row, col + 1)) {
                int q = index(row, col + 1);
                uf.union(id, q);
            }
            if (row == 1) {
                uf.union(0, id);
                full[id] = true;
            }
            if (row == n) {
                uf.union(id, n * n + 1);
                if (full[id]) full[n * n + 1] = true;
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return !blocked[index(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (!full[index(row, col)]) {
            full[index(row, col)] = uf.connected(0, index(row, col));
        }
        return full[index(row, col)];
    }

    public int numberOfOpenSites() {
        return numOpenSites;
    }

    public boolean percolates() {
        if (!full[n * n + 1]) {
            full[n * n + 1] = uf.connected(0, n * n + 1);
        }
        return full[n * n + 1];
    }
}
