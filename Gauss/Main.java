import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        Gauss gauss = new Gauss();
        try {
            gauss.init("matrix");
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        }

        System.out.println("Original system:");
        gauss.print();

        System.out.println("System reduced to a triangular form:");

        gauss.triangle();
        gauss.print();

        System.out.println("Solution:");
        gauss.solve();
        gauss.printAnswer();

    }
}
