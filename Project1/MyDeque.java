
//class created by Roy Wu - row64 
//cs445 assignment 1
//prof: John Ramirez
/* ABOUT:
 * this class creates a double-ended queue that is circular and resizing by using an array and modulo logic
 * it feature methods that can access and alter data at the logical front and back of the queue
 * it can also has methods that allow the creation of deeper copies and a comparator method
 */

public class MyDeque<T> implements DequeInterface<T> {

    protected T[] data;
    protected int front;
    protected int back;
    protected int size;

    @SuppressWarnings("unchecked")
    public MyDeque(int initSize) { //CONSTRUCTOR
        data = (T[]) new Object[initSize];
        front = -1; 
        back = 0;
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public MyDeque(MyDeque<T> old) { //COPY CONSTRUCTOR
        data = (T[]) new Object[old.data.length];
        size = old.size;
        front = old.front;
        back = old.back;
        for (int i = 0; i < old.size; i++) {
            data[i] = old.data[i];
        }
    }

    public boolean equals(MyDeque<T> rhs) {
       String compareFirst = this.toString();
       String compareSecond = rhs.toString();
       if (compareFirst.equals(compareSecond)) {
           return true;
       } else {
           return false;
       }
    }

    public void addToFront(T newEntry) {
        if (!(size < capacity())) {
            resize();
        } else {
            if (front == -1) {
                front = back = 0;
            } else if (front == 0) {
                front = capacity() - 1;
            } else {
                front -= 1;
            } 
        }
        data[front] = newEntry;
        size ++;
    }

    public void addToBack(T newEntry) {
        if (size < capacity()) {
            if (size == 0) {
                front = back = 0;
                data[back] = newEntry;
                size++;
            } else if (back == capacity() - 1) {
                back = 0;
                data[back] = newEntry;
                size++;
            } else {
                back++;
                data[back] = newEntry;
                size++;
            }
        }
        if (!(size < capacity())) {
            resize();
        }
    }

    public T removeFront() {
        if (isEmpty()) {
            return null;
        } else if (front == capacity() - 1 && back < front) {
            T removed = data[front];
            data[front] = null;
            size--;
            front = 0;
            return removed;
        } else {
            T removed = data[front];
            data[front] = null;
            size--;
            front++;
            return removed;
        }
    }

    public T removeBack() {
        if (isEmpty()) {
            return null;
        } else if (back == 0 && back < front) {
            T removed = data[back];
            data[back] = null;
            size--;
            back = capacity() - 1;
            return removed;
        } else {
            T removed = data[back];
            data[back] = null;
            size--;
            back--;
            return removed;
        }
    }

    public T getFront() {
        if (isEmpty()) {
            return null;
        } else {
            return data[front];
        }
    }

    public T getBack() {
        if (isEmpty()) {
            return null;
        } else {
            return data[back];
        }
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        data = (T[]) new Object[data.length];
        front = -1;
        back = 0;
        size = 0;
    }

    public String toString() {
        StringBuilder bruh = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            bruh.append(data[(front + i) % data.length] + " ");
        }
        return "Contents: " + bruh.toString();
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return data.length;
    }

    @SuppressWarnings("unchecked")
    protected void resize() {
        int newSize = data.length * 2;
        T[] temp = (T[]) new Object[newSize];
        boolean atEnd = false;
        int newArrPos = 0;
        int oldArrPos = front;

        while (!atEnd) {
            atEnd = oldArrPos % data.length == back;
            temp[newArrPos] = data[oldArrPos % data.length];
            newArrPos++;
            oldArrPos++;
        }
        front = 0; 
        back = capacity() - 1;
        data = temp;
    }
    

}
