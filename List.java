public class List<T extends Comparable> {

    public class Node {
        T value;
        Node next;

        Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    public Node head;
    public Node tail;

    public List() {
    }

//  добавление ячейки
    public void add(T value) {
        Node newNode = new Node(value, null);

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = tail.next;
        }
    }

//  ищет эл-т с меньше степенью чем заданный, для удобства сложения и умножения
    public Node getNodeBefore(T value) {

        if (head == null || head.value.compareTo(value) > 0) {
            return null;
        }

        Node current = head, last = head;
        while (current != null) {
            if (current.value.compareTo(value) > 0)
                break;
            last = current;
            current = current.next;
        }

        return last;
    }

//  вставка в середину
    public Node insert(T value) {
        if (head == null) {
            add(value);
            return null;
        }

        if (head.value.compareTo(value) > 0) {
            head = new Node(value, head);
            return head;
        }

        Node current = head.next, last = head;
        while (current != null) {
            if (current.value.compareTo(value) > 0) {
                last.next = new Node(value, current);
                return current;
            }
            last = current;
            current = current.next;
        }

        last.next = new Node(value, null);
        return null;
    }
}


