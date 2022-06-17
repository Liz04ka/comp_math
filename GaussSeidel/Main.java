import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        GaussSeidel gs = new GaussSeidel();
        try {
            gs.init("matrix");
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        }

        System.out.println("Original system:");
        gs.print();

        System.out.println("");
        gs.diagonal();
    }
}
