public class Grid {
    private double[] x; //узлы сетки
    private double[] y; //значения в узлах сетки

//
    public Grid(double[] x,double[] y) {
        this.x = x;
        this.y = y;
    }


    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public double[] getY() {
        return y;
    }

    public void setY(double[] y) {
        this.y = y;
    }

//  Длина = количесво Х
    public int length(){
        return x.length;
    }
}
