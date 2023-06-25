//Roy Wu
/*
 * this class is an implementation of the median of five quicksort algorithm
 * it is highly adapted from the provided median of three quicksort algorithm
 * the only changes from the original are to the partition method and the order method where 
 * the program partitions and sorts the array based on the the index 
 * of first, quartile 1 (q1), median, quartile 3 (q3), and last values
 */

public class MedOfFive<T extends Comparable<? super T>> implements Partitionable<T> {

    public void sort(T[] array, int n) {
        quickSort(array, 0, n - 1);
    }

    public void quickSort(T[] a, int first, int last) {
        int MIN_SIZE = 5; //LINE HERE
        if (last - first + 1 < MIN_SIZE) {
            insertionSort(a, first, last);
        } else {
            int pivotIndex = partition(a, first, last);
            quickSort(a, first, pivotIndex - 1);
            quickSort(a, pivotIndex + 1, last);
        }
    }

    private void sortFirstQ1midQ3Last(T[] a, int first, int q1, int mid, int q3, int last) {
        order(a, first, q1); //a[first] <= a[q1]
        order(a, first, mid); //a[first] <= a[mid]
        order(a, first, q3); //a[first] <= a[q3]
        order(a, first, last); //a[first] <= a[last]
        order(a, q1, mid); //a[q1] <= a[mid]
        order(a, q1, q3); //a[q1] <= a[q3]
        order(a, q1, last); //a[q3] <= a[last]
        order(a, mid, q3); //a[mid] <= a[q3]
        order(a, mid, last); //a[mid] <= a[last]
        order(a, q3, last); //a[q3] <= a[last]
    }

    private void order(T[] a, int i, int j) {
        if (a[i].compareTo(a[j]) > 0) {
            swap(a, i, j);
        }
    }

    private void swap(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public void insertionSort(T[] a, int n) {
        insertionSort(a, 0, n - 1);
    }

    public void insertionSort(T[] a, int first, int last) {
        int unsorted, index;

        for (unsorted = first + 1; unsorted <= last; unsorted++) {
            T firstUnsorted = a[unsorted];
            insertInOrder(firstUnsorted, a, first, unsorted - 1);
        }
    }

    private void insertInOrder(T element, T[] a, int begin, int end) {
        int index;

        for (index = end; (index >= begin) && (element.compareTo(a[index]) < 0); index--) {
            a[index + 1] = a[index]; // make room
        }

        a[index + 1] = element;  // insert
    }


    public int partition(T[] a, int first, int last) {
        int mid = (first + last) / 2;
        int q1 = (first + mid) / 2;
        int q3 = (mid + last) / 2;

        sortFirstQ1midQ3Last(a, first, q1, mid, q3, last);

        //MOVE PIVOT OUT OF THE WAY (second to last position)
        swap(a, mid, last - 1);
        int pivotIndex = last - 1;
        T pivot = a[pivotIndex];

        int indexFromLeft = first + 1;
        int indexFromRight = last - 2;
        boolean done = false;

        while (!done) {
            while (a[indexFromLeft].compareTo(pivot) < 0)
                indexFromLeft++;

            while (a[indexFromRight].compareTo(pivot) > 0)
                indexFromRight--;

            if (indexFromLeft < indexFromRight) {
                swap(a, indexFromLeft, indexFromRight);
                indexFromLeft++;
                indexFromRight--;
            } else {
                done = true;
            }
        }
        
        //MOVE PIVOT BACK TO CORRECT SORTED LOCATION
        swap(a, pivotIndex, indexFromLeft);
        pivotIndex = indexFromLeft;

        return pivotIndex;
    }
}
