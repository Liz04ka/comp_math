import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GaussSeidel {

    private int n;   //количество строк
    private int m;   //количество столбцов
    private double[][] array; //матрица
    private double[] answer; //ответы

    //выделение памяти
    private void create(int k, int l) {
        array = new double[k][];
        int i;
        for (i = 0; i < k; i++)
            array[i] = new double[l];
    }

    // чтение из файла, инициализация матрицы
    public void init(String s) throws FileNotFoundException {
        File file = new File(s);
        Scanner scan = new Scanner(file);
        Pattern pat = Pattern.compile("[\\s\\t]+");
        String str = scan.nextLine();
        String[] sn = pat.split(str);
        n = Integer.parseInt(sn[0]);
        m = Integer.parseInt(sn[1]);
        create(n, m + 1);
        int i, j;
        for (i = 0; i < n; i++) {
            str = scan.nextLine();
            sn = pat.split(str);
            for (j = 0; j <= m; j++)
                array[i][j] = Double.parseDouble(sn[j]);
        }
        scan.close();
    }

//  подбор и перестановка всех вариантов
    private boolean permutation(int n, boolean checkOnlyZero) {
        if (n == 1) {
            return checkDominance(checkOnlyZero);
        } else {
            for (int i = 0; i < n - 1; i++) {
                if (permutation(n - 1, checkOnlyZero)) return true;
                if (n % 2 == 0) {
                    swap(i, n - 1);
                } else {
                    swap(0, n - 1);
                }
            }
            return permutation(n - 1, checkOnlyZero);
        }
    }

    //меняет местами строки
    private void swap(int a, int b) {
        double[] t = array[a];
        array[a] = array[b];
        array[b] = t;
    }

    //диагональное преобладание
    private boolean checkDominance(boolean checkOnlyZero) {
        if (checkOnlyZero) print();

        boolean gt = false;
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < m; j++) {
                if (i != j) {
                    sum += Math.abs(array[i][j]);
                }
            }

            if (checkOnlyZero) {
                if (array[i][i] == 0) return false;
                continue;
            }

            if (Math.abs(array[i][i]) < sum)
                return false;

            if (Math.abs(array[i][i]) > sum) {
                gt = true;
            }
        }

        return checkOnlyZero || gt;
    }

//  диагональные 0
    public void diagonal() {
        boolean diagonalDominance = permutation(n, false);
        if (diagonalDominance) {
            System.out.println("Перестановка диагональное преобладание");
            print();
            solveTwo();
            return;
        }

        boolean noZeros = permutation(n, true);
        if (!noZeros) {
            System.out.println("невозможно решить");
//            prAnswer = -1;
            return;
        }

        System.out.println("Перестановка не диагональное преобладание, но и не нули");
        print();
        solve();
    }

    private void solveTwo() {
        double eps = 0.000000001;
        answer = new double[n];
        double max = 0.0;
        double[] previous = new double[n];
        for(int i = 0; i < n; i++) {
            previous[i] = 0.0;
        }

        do {
            for(int i = 0; i < n; i++) {
                previous[i] = array[i][n];

                for(int j = 0; j < n; j++) {
                    if (i != j)
                        previous[i] -= array[i][j] * answer[j];
                }
                previous[i] /= array[i][i];
            }
            max =  Math.abs(answer[0] - previous[0]);
            for(int i = 0 ; i < n; i++) {
                if (Math.abs(answer[i] - previous[i]) > max)
                    max = Math.abs(answer[i] - previous[i]);
                answer[i] = previous[i];
            }
        } while (max > eps);

        printAnswer();

//        System.out.println("Solution:");
//        for (int i = 0; i < n; i++) {
//            System.out.print("X_" + (i + 1) + " = ");
//            System.out.printf("%15.6E", answer[i]);
//            System.out.println();
//        }
//        System.out.println();
    }

//  итерационный метод
    public void solve() {
        int iterations = 1000;
        double eps = 1e-7;
        answer = new double[n];

        while (iterations-- != 0) {
            double diff = -1;
            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < m; j++) {
                    if (i != j) {
                        sum += array[i][j] * answer[j];
                    }
                }
                double newValue = (array[i][m] - sum) / array[i][i];
                diff = Math.max(diff, Math.abs(answer[i] - newValue));
                answer[i] = newValue;
            }
            if (diff < eps) break;
        }

        if (iterations == 0) {
            System.out.println("метод расходитстя");
            return;
        }

        printAnswer();
//        System.out.println("Solution:");
//        for (int i = 0; i < n; i++) {
//            System.out.print("X_" + (i + 1) + " = ");
//            System.out.printf("%15.6E", answer[i]);
//            System.out.println();
//        }
//        System.out.println();
    }

    private void printAnswer() {
        System.out.println("Solution:");
        for (int i = 0; i < n; i++) {
            System.out.print("X_" + (i + 1) + " = ");
            System.out.printf("%15.6E", answer[i]);
            System.out.println();
        }
        System.out.println();
    }

    //вывод матрицы на печать
    public void print() {
        int i, j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                System.out.printf("%15.6E", array[i][j]);
            }
            System.out.printf("\t|%15.6E", array[i][m]);
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
}

