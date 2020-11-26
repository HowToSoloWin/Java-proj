package com.company;

public class List<E> {
    private ListElement head;
    private ListElement tail;

    public void addBack(E data) {
        ListElement a = new ListElement();
        a.data = data;
        if (tail == null) {
            head = a;
            tail = a;
        } else {
            tail.next = a;
            tail = a;
        }
    }
    public void printList() {
        ListElement t = head;
        while (t != null) {
            System.out.println(t.data + " ");
            t = t.next;
        }
        System.out.println("\n");
    }
    public void removeData(E data) {
        if (head == null) { // если список пуст ничего не делаем
            return;
        }
        if (head == tail) { // если список состоит из одного элемента очищаем указатель начала и конца
            head = null;
            tail = null;
            return;
        }
        if (head.data == data) { // если первый элемент - тот что нужен переключаем указатель начала на второй элемент
            head = head.next;
            return;
        }
        ListElement t = head; // поиск элемента
        while (t.next != null) {
            if (t.next.data == data) {
                if (tail == t.next) {
                    tail = t;
                }
                t.next = t.next.next;
                return;
            }
            t = t.next;
        }
    }
    public void getData(int index) {
        ListElement w = this.head;
        while (index-- > 0) {
            w = w.next;
        }
        System.out.println(w.data + " ");
        return;
    }
  /*  public int getSize() {
        int count = 0;
        if (head != null) {
            count++;
            while (head.next != null) {
                count++;
                head = head.next;
            }
        }else {
            return 0;
        }
        return count;
    } */ // тестовая
}

