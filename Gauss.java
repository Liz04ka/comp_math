import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Gauss {

    private int n;   //количество строк
    private int m;   //количество столбцов
    private double[][] array; //матрица
    private double[] answer; //ответы
    private int status = -1;


    //выделение памяти
    private void create(int k, int l) {
        array = new double[k][];
        int i;
        for (i = 0; i < k; i++)
            array[i] = new double[l + 1];
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
        create(n, m);
        int i, j;
        for (i = 0; i < n; i++) {
            str = scan.nextLine();
            sn = pat.split(str);
            for (j = 0; j < m; j++)
                array[i][j] = Double.parseDouble(sn[j]);
            array[i][m] = Double.parseDouble(sn[m]);
        }
        scan.close();
    }

    //вывод матрицы на печать
    public void print() {
        int i, j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++)
                System.out.printf("%15.6E", array[i][j]);
            System.out.printf(" | %15.6E\n", array[i][m]);
        }
        System.out.println();
        System.out.println();
    }

    //меняет строки местами
    private void swap(int a, int b) {
        double[] t = array[a];
        array[a] = array[b];
        array[b] = t;
    }

    //умножает строку на коэфицент
    private void mul(int index, double k) {
        for (int i = 0; i < m + 1; i++) {
            array[index][i] *= k;
        }
    }

    //сложение двух строк
    private void sum(int index1, int index2) {
        for (int i = 0; i < m + 1; i++) {
            array[index2][i] += array[index1][i];
        }
    }

    //обнулять когда нахожу,

    //приводит к треугольному виду
    public void triangle() {
        for (int i = 0; i < m; i++) {
            int max = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(array[max][i]) < Math.abs(array[j][i])) {
                    max = j;
                }
            }
            swap(max, i);
            if (array[i][i] == 0) {
                status = 3;
//              вырожденая
            } else {
                for (int j = i + 1; j < n; j++) {
                    //занулить в пересчете, если она по абсолютной вел меньше точности
                    if (array[j][i] != 0) {
                        double k = -(array[i][i] / array[j][i]);
                        mul(j, k);
                        sum(i, j);
                    }
                }
            }
        }
        if (hasNoSolution())
            status = 1;
        else if (hasInfSolution())
            status = 2;

        if (status == -1)
            status = 0;
    }

    private boolean almostZero(double value) {
        double eps = 0.000001;
        return abs(value) < eps;
    }

    private boolean hasNoSolution() {
        return almostZero(array[n - 1][n - 1]) && !almostZero(array[n - 1][m]);
    }

    private boolean hasInfSolution() {
        return almostZero(array[n - 1][n - 1]) && almostZero(array[n - 1][m]);
    }

    public void back() {
        for (int i = n - 1; i >= 0; i--) {
            double s = 0;
            for (int j = i + 1; j < n; j++) {
                s += array[i][j] * answer[j];
            }
            answer[i] = (array[i][m] - s) / array[i][i];
        }
    }


    public void solve() {
        if (status == -1)
            triangle();

        if (status == 0) {
            answer = new double[n];
            back();
        }
    }

    private double abs(double a) {
        return a >= 0 ? a : -a;
    }

    public void printAnswer() {
        switch (status) {
            case 0:
                for (int i = 0; i < n; i++) {
                    System.out.print("X_" + (i + 1) + " = ");
                    System.out.printf("%15.6E", answer[i]);
                    System.out.println();
                }
                System.out.println();
                break;
            case 1:
                System.out.println("No solution");
                break;
            case 2:
                System.out.println("Infinitely many solutions");
                break;
            case 3:
                System.out.println("System is undetermined");
                break;
            default:
                System.out.println("System hasn't been solved");
                break;
        }

    }
}
//System.out.printf("%15.6E", array[i][j]);

// n - down, m - right
//метод для посика строки с 0вым коэфицентом
//пов для возведения в степень не подходит
//возвращенное значение сохраняем в переменную и передаем в метод где в 3 случаех выводит сообщение а в одном будет передавать что-то там
