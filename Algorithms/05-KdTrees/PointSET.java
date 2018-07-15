import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> set;

    public PointSET() {
        // construct an empty set of points
        set = new TreeSet<>();
    }

    public boolean isEmpty() {
        // is the set empty?
        return set.isEmpty();
    }

    public int size() {
        // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) throw new NullPointerException();
        set.add(p);
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) throw new NullPointerException();
        return set.contains(p);
    }

    public void draw() {
        // draw all points to standard draw
        for (Point2D p : set)
            p.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
        if (rect == null) throw new NullPointerException();
        ArrayList<Point2D> points = new ArrayList<>();
        for (Point2D p : set)
            if (rect.contains(p))
                points.add(p);
        return points;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new NullPointerException();
        Point2D nearest = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Point2D q: set)
            if (p.distanceTo(q) < minDistance) {
                minDistance = p.distanceTo(q);
                nearest = q;
            }
        return nearest;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
}
