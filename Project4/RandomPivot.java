import java.util.Random;

public class RandomPivot<T extends Comparable<? super T>> implements Partitionable<T>{
    private final Random random = new Random();

    public void sort(T[] array, int n) {
        quickSort(array, 0, n - 1);
    } // end quickSort

    public void quickSort(T[] array, int first, int last) {
        if (first < last) {
            int pivotIndex = partition(array, first, last);

            quickSort(array, first, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, last);
        } // end if
    } // end quickSort


    private void swap(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    } // end swap

    
    public int partition(T[] a, int first, int last) {
        int pivotIndex = first + random.nextInt(last - first + 1); // choose a random pivot index
        T pivot = a[pivotIndex];
        swap(a, pivotIndex, last); // move the pivot to the rightmost position
        pivotIndex = last; // update the pivot index

        int indexFromLeft = first;
        int indexFromRight = last - 1;
        boolean done = false;
        while (!done) {
            while (a[indexFromLeft].compareTo(pivot) < 0)
                indexFromLeft++;

            while (a[indexFromRight].compareTo(pivot) > 0 && indexFromRight > first)
                indexFromRight--;

            if (indexFromLeft < indexFromRight) {
                swap(a, indexFromLeft, indexFromRight);
                indexFromLeft++;
                indexFromRight--;
            } else {
                done = true;
            }
        } // end while

        swap(a, pivotIndex, indexFromLeft);
        pivotIndex = indexFromLeft;

        return pivotIndex;
    }
}
