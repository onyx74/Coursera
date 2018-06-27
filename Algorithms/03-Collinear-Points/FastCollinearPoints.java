import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class FastCollinearPoints {
    private static final List<LineSegment> LINE_SEGMENTS = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points)  {   // finds all line segments containing 4 or more points
        if (points == null || Arrays.asList(points).contains(null)) {
            throw new NullPointerException("argument is null");
        }

        int n = points.length;

        Point[] pointsClone = points.clone();
        Arrays.sort(pointsClone);
        Point[] points2 = new Point[n];


        for (int p = 0; p < n; p++) { // if points.length - 3, we won't catch if 2 points are the same at the end.
            for (int i = p; i < n; i++)
                points2[i] = pointsClone[i];

            Point pointP = pointsClone[p];
            Arrays.sort(points2, p+1, n, pointP.slopeOrder());
            Arrays.sort(points2, 0, p, pointP.slopeOrder());

            // go through the array:
            int ct = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;
            for (int i = p+1; i < n; i++) {
                double slope = pointP.slopeTo(points2[i]);

                // System.out.println(pointP.toString() + points2[i].toString());
                // System.out.println("slope:" + slope);
                if (slope == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("same points");
                }
                if (Double.compare(slope, previousSlope) == 0) {
                    ct++;
                }
                // System.out.println("ct:" + ct + " i:" + i);
                // System.out.println("slope != previousSlope" + Boolean.toString(slope != previousSlope));
                // System.out.println("i == points.length - 1:" + Boolean.toString(i == points.length - 1));
                if (ct >= 2 && ((Double.compare(slope, previousSlope) != 0) || i == n - 1)) {
                    // System.out.println("CHANGING:");
                    Arrays.sort(points2, i - ct, i);
                    Point minPt = points2[i - ct];
                    Point maxPt = points2[i - 1];
                    if (Double.compare(slope, previousSlope) == 0) {
                        maxPt = points2[i];
                    }

                    if (pointP.compareTo(minPt) < 0) {
                        minPt = pointP;
                    }
                    if (pointP.compareTo(maxPt) > 0) {
                        maxPt = pointP;
                    }
                    // System.out.println(pointP.toString() + points[i - ct].toString() +  points[i - 1].toString());

                    // check if there is a point in the first array with the same slope

                    boolean insert = true;
                    // System.out.println(slope);

                    for (int j = 0; j < p; j++) {
                        double pastSlope = pointP.slopeTo(points2[j]);
                        if (pastSlope >= previousSlope) {
                            if (pastSlope == previousSlope) {
                                insert = false;
                            }
                            break;
                        }
                    }


                    /*
                     boolean insert = true;
                     for (int j = i - ct; j < i; j++) {
                     // System.out.println("checking:" + points[j].toString());
                     if (!insertMap(points[j], previousSlope)) {
                     insert = false;
                     }
                     }
                     // System.out.println("checking:" + pointP.toString());
                     if (!insertMap(pointP, previousSlope)) {
                     insert = false;
                     }
                     */

                    if (insert) {
                        LineSegment lineSeg = new LineSegment(minPt, maxPt);
                        LINE_SEGMENTS.add(lineSeg);
                    }

                }
                if (slope != previousSlope) {
                    ct = 0;
                    previousSlope = slope;
                }
            }

        }

    }


    public int numberOfSegments()  {      // the number of line segments
        return LINE_SEGMENTS.size();
    }

    public LineSegment[] segments()    {            // the line segments
        LineSegment[] lineSegment = new LineSegment[numberOfSegments()];
        lineSegment =  LINE_SEGMENTS.toArray(lineSegment);
        return lineSegment;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
