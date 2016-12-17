package edu.spbu.cs;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DenseMatrix implements IMatrix {
    private double matrix[][];

    public DenseMatrix(String inFileName) {
        Scanner in = null;
        try {
            in = new Scanner(new File(inFileName));
            int size = in.nextInt();
            matrix = new double[size][size];
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

    @Override
    public IMatrix multiply(IMatrix o) {
        return (o instanceof DenseMatrix) ? multiplyDense((DenseMatrix) o) : multiplySparse((SparseMatrix) o);
    }

    private IMatrix multiplyDense(DenseMatrix o) {
        int size = matrix.length;
        DenseMatrix resMatrix = new DenseMatrix(size);
        o.transponation();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    resMatrix.getMatrix()[i][j] += this.matrix[i][k] * o.getMatrix()[j][k];
                }
            }
        }
        return resMatrix;
    }

    private IMatrix multiplySparse(SparseMatrix o) {
        int size = matrix.length;
        DenseMatrix resMatrix = new DenseMatrix(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    Point coord2 = new Point(j, k);
                    double v2 = o.getMap().get(coord2) != null ? o.getMap().get(coord2) : 0;
                    resMatrix.getMatrix()[i][j] += this.matrix[i][k] * v2;
                }
            }
        }
        return resMatrix;
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
