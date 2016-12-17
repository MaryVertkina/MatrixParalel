import edu.spbu.cs.IMatrix;
import edu.spbu.cs.DenseMatrix;
import edu.spbu.cs.SparseMatrix;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Matrix {
    public static void main (String args[]) {
        mulDenseDense();
//        mulSparseSparse();
//        mulDenseSparce();
//        mulSparceDense();
    }

    private static void mulDenseDense() {
        IMatrix m1 = new DenseMatrix("in1.txt");
        IMatrix m2 = new DenseMatrix("in2.txt");

        IMatrix res = m1.multiply(m2);
        printMatrix(res, "outDD.txt");
    }

    private static void mulSparseSparse() {
        IMatrix m1 = new SparseMatrix("in1.txt");
        IMatrix m2 = new SparseMatrix("in2.txt");

        IMatrix res = m1.multiply(m2);
        printMatrix(res, "outSS.txt");
    }

    private static void mulDenseSparce() {
        IMatrix m1 = new DenseMatrix("in1.txt");
        IMatrix m2 = new SparseMatrix("in2.txt");

        IMatrix res = m1.multiply(m2);
        printMatrix(res, "outDS.txt");
    }

    private static void mulSparceDense() {
        IMatrix m1 = new SparseMatrix("in1.txt");
        IMatrix m2 = new DenseMatrix("in2.txt");

        IMatrix res = m1.multiply(m2);
        printMatrix(res, "outSD.txt");
    }

    private static void printMatrix(IMatrix res, String fileName) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(fileName);
            out.print(res.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
