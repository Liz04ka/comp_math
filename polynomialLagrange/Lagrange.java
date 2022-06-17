public class Lagrange {

    private Grid grid;
    private Polynomial polynomial = new Polynomial();

    public double point(double x) {
        return polynomial.point(x);
    }

    public Lagrange(Grid grid) {
        this.grid = grid;
        build();
    }

    private void build() {
        for (int i = 0; i < grid.length(); i++) {
            polynomial = polynomial.sum(l(i));
        }
    }

    private Polynomial l(int i) {
        double zn = 1;
        for (int j = 0; j < grid.length(); j++) {
            if(i != j)
                zn *= grid.getX()[i] - grid.getX()[j];
        }

        Polynomial ch = null;

        for (int j = 0; j < grid.length(); j++) {
            if (i == j) continue;

            Polynomial poly = new Polynomial();
            if (grid.getX()[j] != 0)
                poly.add(new Monomial(-grid.getX()[j], 0));

            poly.add(new Monomial(1, 1));
            if (ch == null) ch = poly;
            else ch = ch.mul(poly);
        }

//        System.out.printf("%d Before scale: %s\n", i, ch.toString());
          ch = ch.scale(grid.getY()[i] / zn);
//        System.out.printf("%d After scale: %s\n", i, ch.toString());

        return ch;
    }

    public void print() {
        System.out.println("Ln(x) = " + polynomial.toString());
    }
}
