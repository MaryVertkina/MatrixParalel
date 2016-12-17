package edu.spbu.cs;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SparseMatrix implements IMatrix {
    private Map<Point, Double> map;
    private int mapSize;

    public SparseMatrix(String inFileName) {
        Scanner in = null;
        try {
            in = new Scanner(new File(inFileName));
            mapSize = in.nextInt();
            map = new HashMap<>();
            double v;
            for (int i = 0; i < mapSize; i++) {
                for (int j = 0; j < mapSize; j++) {
                    v = in.nextDouble();
                    if (v != 0) {
                        map.put(new Point(j, i), v);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    private SparseMatrix(Map<Point, Double> map, int size) {
        this.map = map;
        this.mapSize = size;
    }

    @Override
    public IMatrix multiply(IMatrix o) {
        return (o instanceof DenseMatrix) ? multiplyDense((DenseMatrix) o) : multiplySparse((SparseMatrix) o);
    }

    private IMatrix multiplySparse(SparseMatrix o) {
        Map<Point, Double> resMatrix = new HashMap<>();
        int size = Math.max(mapSize, o.getSize());
        for (Map.Entry<Point,Double> e : map.entrySet()){
            Point coord1 = e.getKey();
            double v1 = e.getValue();
            for (int k = 0; k < size; k++) {
                Point coord2 = new Point(k, coord1.x);
                if (o.getMap().get(coord2) != null){
                    double v2 = o.getMap().get(coord2);
                    Point coordRes = new Point(k, coord1.y);
                    double res = resMatrix.get(coordRes) != null ? resMatrix.get(coordRes) + v1 * v2 : v1 * v2;
                    resMatrix.put(coordRes, res);
                }
            }
        }
        return new SparseMatrix(resMatrix, size);
    }

    private IMatrix multiplyDense(DenseMatrix o) {
        Map<Point, Double> resMatrix = new HashMap<>();
        int size = o.getMatrix().length;
        o.transponation();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Point coordRes = new Point(j, i);
                double res = 0;
                for (int k = 0; k < size; k++) {
                    Point coord1 = new Point(k, i);
                    double v1 = map.get(coord1) != null ? map.get(coord1) : 0;
                    res +=  v1 * o.getMatrix()[j][k];
                }
                if (res != 0) {
                    resMatrix.put(coordRes, res);
                }
            }
        }
        return new SparseMatrix(resMatrix, size);
    }

    @Override
    public String toString() {
        StringBuffer res = new StringBuffer();

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                Double v = map.get(new Point(j, i));
                if (v == null) {
                    v = (double) 0;
                }
                res.append(v + " ");
            }
            res.append("\n");
        }

        return res.toString();
    }

    public int getSize() {
        return mapSize;
    }

    public Map<Point, Double> getMap() {
        return map;
    }
}
