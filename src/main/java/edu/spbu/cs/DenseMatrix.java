package edu.spbu.cs;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DenseMatrix implements IMatrix {
    private double matrix[][];
    private double resMatrix[][];
    int num = 0;

    public DenseMatrix(String inFileName) {
        Scanner in = null;
        try {
            in = new Scanner(new File(inFileName));
            int size = in.nextInt();
            matrix = new double[size][size];
            resMatrix = new double[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix[i][j] = in.nextDouble();
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

    private DenseMatrix(int size) {
        matrix = new double[size][size];
    }

    private DenseMatrix(double matrix[][]) {
        this.matrix = matrix;
    }

    @Override
    public IMatrix multiply(IMatrix o) {
        if (o instanceof DenseMatrix) {
            MultRow one = new MultRow(o, "one");
            MultRow two = new MultRow(o, "two");
            MultRow three = new MultRow(o, "three");
            MultRow four = new MultRow(o, "four");

            try {
                one.thread.join();
                two.thread.join();
                three.thread.join();
                four.thread.join();

            } catch (InterruptedException e){
                e.printStackTrace();
            }
        } else {
            multiplySparse((SparseMatrix) o);
        }

        return new DenseMatrix(resMatrix);
    }

    private synchronized int next() {
        return num++;
    }

    private void multiplyDense(DenseMatrix o) {
        int size = matrix.length;
        o.transponation();
        for (int i = next(); i < size; i = next()) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    resMatrix[i][j] += matrix[i][k] * o.getMatrix()[j][k];
                }
            }
        }
    }

    private void multiplySparse(SparseMatrix o) {
        int size = matrix.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    Point coord2 = new Point(j, k);
                    double v2 = o.getMap().get(coord2) != null ? o.getMap().get(coord2) : 0;
                    resMatrix[i][j] += matrix[i][k] * v2;
                }
            }
        }
    }

    class MultRow implements Runnable {
        IMatrix o;
        Thread thread;

        public MultRow(IMatrix o, String s) {
            this.o = o;
            thread = new Thread(this, s);
            thread.start();
        }

        public void run() {
            multiplyDense((DenseMatrix) o);
        }
    }

    @Override
    public String toString() {
        StringBuffer res = new StringBuffer();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                res.append(matrix[i][j] + " ");
            }
            res.append("\n");
        }

        return res.toString();
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void transponation(){
        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                double b = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = b;
            }
        }
    }
}
