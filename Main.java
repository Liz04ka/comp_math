import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double a = sc.nextDouble();
        double b = sc.nextDouble();
        int n = sc.nextInt();

        Grid gr = getGrid(a, b, n);

//        for (int i = 0; i < gr.length(); i++) {
//            System.out.printf("%f %f\n", gr.getX()[i], gr.getY()[i]);
//        }

        int nExt = 2 * n - 1;
        Grid ext = getGrid(a, b, nExt);

        Lagrange Ln = new Lagrange(gr);
        double[] fx = new double[nExt];
        double[] ln = new double[nExt];
        for (int i = 0; i < nExt; i++) {
            fx[i] = f(ext.getX()[i]);
            ln[i] = Ln.point(ext.getX()[i]);

            if (i % 2 == 1) ext.getY()[i] = Double.MAX_VALUE;
        }
        Ln.print();
        printExel(ext.getX(), ext.getY(), fx, ln);
    }

    private static Grid getGrid(double a, double b, int n) {
        double h = (b - a) / (n - 1);
        double[] y = new double[n];
        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = a + i*h;
            y[i] = f(x[i]);
        }
        return new Grid(x, y);
    }

    public static double f(double x) {
//        return 4.378 * (x*x*x*x*x) - 3.271 * (x*x*x);
        return Math.sin(Math.pow(x,2)/2);
//        return Math.pow(x, 5) * 3.724 + 4.273 * Math.pow(x, 3) + 5.444 * Math.pow(x, 2);
//          return 5.444 * Math.pow(x, 2) - Math.pow(x, 5) * 3.724 - 4.273 * Math.pow(x, 3);
//        return Math.pow(x, 5) - Math.pow(x, 4)  * 4.378 - Math.pow(x, 2) * 2.177 + 0.3310;
    }

    private static void printExel(double[] x, double[] y, double[] f, double[] ln) {
        System.out.printf("\t\t%-15s %-15s %-15s %-15s\n", "x", "y", "f", "Ln");

        for (int i = 0; i < x.length; i++) {
            System.out.printf("%15.6E %15.6E %15.6E %15.6E\n",
                    x[i],
                    y[i] == Double.MAX_VALUE? null: y[i],
                    f[i],
                    ln[i]
            );
        }
    }
}
