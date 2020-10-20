import java.util.ArrayList;
import java.util.Collections;

class Point implements Comparable<Point> {
    double x;
    double y;

    Point (double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point o) {
        return (int) (this.x - o.x);
    }
}

public class ClosestPair2D {

    private static double getDistance (Point A, Point B) {
        return Math.sqrt(Math.pow(A.x - B.x,2) + Math.pow(A.y - B.y,2));
    }

    private static double bruteForceClosestPair(ArrayList<Point> points) {
        double d = Double.MAX_VALUE;
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                d = Math.min(d, getDistance(points.get(i), points.get(j)));
            }
        }
        return d;
    }

    private static double findClosestPair(ArrayList<Point> points) {
        ArrayList<Point> pointsL = new ArrayList<>();
        ArrayList<Point> pointsR = new ArrayList<>();
        ArrayList<Point> P = new ArrayList<>();
        ArrayList<Point> Q = new ArrayList<>();
        int n = points.size();
        if (n <= 3) return bruteForceClosestPair(points);
        for (int i = 0; i <= Math.ceil(n / 2); i++) pointsL.add(new Point(points.get(i).x, points.get(i).y));
        double dL = findClosestPair(pointsL);
        for (int i = (int)(Math.ceil(n / 2) + 1); i < n; i++) pointsR.add(new Point(points.get(i).x, points.get(i).y));
        double dR = findClosestPair(pointsR);
        double d = Math.min(dL, dR);
        double m = points.get((int)(Math.ceil(n / 2) - 1)).x;
        for (Point p : points) {
            if (m - d >= p.x && p.x >= m) P.add(new Point(p.x, p.y));
            if (m >= p.x && p.x >= m + d) Q.add(new Point(p.x, p.y));
        }
        for (Point p : P) {
            for (Point q : Q) {
                if (Math.abs(p.y - q.y) < d) d = Math.min(d, getDistance(p, q));
            }
        }
        return d;
    }

    public static void main (String[] args) {
        double[] x = {2,12,40,5,12,3};
        double[] y = {3,30,50,1,10,4};
        ArrayList<Point> p = new ArrayList<>();
        for (int i = 0; i < x.length; i++) p.add(new Point(x[i], y[i]));
        Collections.sort(p);
        System.out.println(findClosestPair(p));
        System.out.println(bruteForceClosestPair(p));
    }
}
