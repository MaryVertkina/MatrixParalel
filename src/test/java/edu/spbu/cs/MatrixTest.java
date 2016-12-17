package edu.spbu.cs;

import org.junit.Test;
import static org.junit.Assert.*;

public class MatrixTest
{
    private final String matrixResStr = "-21.0 -12.0 1.0 \n"
                                      + "20.0 19.0 16.0 \n"
                                      + "31.0 37.0 42.0 \n";

    @Test
    public void testMulDenseDense() throws Exception {
        IMatrix m1 = new DenseMatrix("in1.txt");
        IMatrix m2 = new DenseMatrix("in2.txt");

        IMatrix res = m1.multiply(m2);
        assertEquals(matrixResStr, res.toString());
    }

    @Test
    public void testMulSparseSparse() throws Exception {
        IMatrix m1 = new SparseMatrix("in1.txt");
        IMatrix m2 = new SparseMatrix("in2.txt");

        IMatrix res = m1.multiply(m2);
        assertEquals(matrixResStr, res.toString());
    }
    @Test
    public void testMulDenseSparce() throws Exception {
        IMatrix m1 = new DenseMatrix("in1.txt");
        IMatrix m2 = new SparseMatrix("in2.txt");

        IMatrix res = m1.multiply(m2);
        assertEquals(matrixResStr, res.toString());
    }
    @Test
    public void testMulSparceDense() throws Exception {
        IMatrix m1 = new SparseMatrix("in1.txt");
        IMatrix m2 = new DenseMatrix("in2.txt");

        IMatrix res = m1.multiply(m2);
        assertEquals(matrixResStr, res.toString());
    }
}