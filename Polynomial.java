public class Polynomial {

    private List<Monomial> monomialList;


    public Polynomial(){
        this.monomialList = new List<>();
    }

//   для меньших обращений, делают то же, что и мономиал лист
    public void add(Monomial monomial) {
        monomialList.add(monomial);
    }

    public List<Monomial>.Node insert(Monomial monomial) {
        return monomialList.insert(monomial);
    }

    public List<Monomial>.Node getNodeBefore(Monomial monomial) {
        return monomialList.getNodeBefore(monomial);
    }

    // 1 2 4 5 6
    // 0 3 4

    // 0 1 2 3 4 5 6

//  Сложение полиномов
    public Polynomial sum(Polynomial other) {
        Polynomial res = new Polynomial();
        List<Monomial>.Node currentA = monomialList.head;
        List<Monomial>.Node currentB = other.monomialList.head;
        while (currentA != null && currentB != null) {
            if (currentA.value.power < currentB.value.power) {
                res.add(currentA.value);
                currentA = currentA.next;
            } else if (currentA.value.power > currentB.value.power){
                res.add(currentB.value);
                currentB = currentB.next;
            } else {
                Monomial check = (currentA.value.sum(currentB.value));
                if (check.koef != 0) res.add(check);
                currentA = currentA.next;
                currentB = currentB.next;
            }
        }

        List<Monomial>.Node last = currentA != null? currentA: currentB;

        while (last != null) {
            res.add(last.value);
            last = last.next;
        }
        return res;
    }

//  умножение полиномов
    public Polynomial mul(Polynomial other) {
        Polynomial res = new Polynomial();
        List<Monomial>.Node currentA = monomialList.head;
        List<Monomial>.Node currentB = other.monomialList.head;

        while (currentA != null) {

            Monomial prod = currentA.value.mul(currentB.value);
//          моном с максимальной степенью не больше искомого
            List<Monomial>.Node prev = res.getNodeBefore(prod);

//          если не нашлось, то в начало или если степени не равны
            if (prev == null || prod.power != prev.value.power)
                res.insert(prod);

//          если стпени равны и коэф в сум = 0, то удал прев
            else if ((prev.value.koef += prod.koef) == 0)
                res.monomialList.head = prev.next;

//          закончили второй множ, передвигаем
            if ((currentB = currentB.next) == null) {
                currentA = currentA.next;
                currentB = other.monomialList.head;
            }
        }
        return res;
    }

//  умножение на число
    public Polynomial scale(double a) {
        Polynomial res = new Polynomial();
        if (a == 0) return res;

        List<Monomial>.Node current = monomialList.head;
        while (current != null) {
            res.add(current.value.scale(a));
            current = current.next;
        }
        return res;
    }

//  В точке
    @Deprecated
    public double _point(double x) {
        double sum = 0;
        List<Monomial>.Node current = monomialList.head;
        while (current != null) {
            sum += current.value.point(x);
            current = current.next;
        }
        return sum;
    }

    // x^3 + x^4 + x^8
    // x^4 + x^5 + x^8

    // Член     Пред.power    Тек. power
    //  x^3         0              3        Умножаем на x (3 - 0) раз
    //  x^4         3              4        Умножаем на x (4 - 1) раз
    //  x^8         4              8        Умножаем на x (8 - 4) раз
    //

    public double point(double x) {
        double sum = 0;
        List<Monomial>.Node current = monomialList.head;
        int s = 0; //power
        double v = 1;  // in x power
        while (current != null) {
            for (int i = 0; i < (current.value.power - s); i++) {
                v *= x;
                s = current.value.power;
            }
            sum += current.value.koef * v;
            current = current.next;
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        List<Monomial>.Node current = monomialList.head;
        while (current != null) {
            builder.insert(0, current.value.toString());
            if (current.next != null) builder.insert(0, " + ");
            current = current.next;
        }
        return builder.toString();
    }
}

class Monomial implements Comparable<Monomial> {
    public double koef;
    public int power;

    public Monomial(double koef, int power) {
        this.koef = koef;
        this.power = power;
    }

//  сложение
    public Monomial sum(Monomial other) {
        if (this.power == other.power) {
            return new Monomial(this.koef + other.koef, this.power);
        } else {
            return null;
        }
    }

    public Monomial mul(Monomial other) {
        return new Monomial(this.koef * other.koef, this.power + other.power);
    }

//  умножение на число
    public Monomial scale(double x) {
        return new Monomial(this.koef * x, this.power);
    }


    public double point(double x) {
        if (power == 0) return this.koef;
        double t = x;

        for (int i = 1; i < this.power; i++) {
            t *= x;
        }
        return this.koef * t;
    }

//  сравнение по степени
    @Override
    public int compareTo(Monomial o) {
        return Integer.compare(this.power, o.power);
    }


    @Override
    public String toString() {
        if (power == 0) return String.valueOf(koef);
        if (power == 1) return String.format("%.6E * x", koef);

        return String.format("%.6E * x^%d", koef, power);
    }
}