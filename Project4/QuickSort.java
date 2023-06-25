public class QuickSort<T extends Comparable<? super T>> implements Sorter<T> {
    private Partitionable<T> partAlgo;
    private int MIN_SIZE;

    public QuickSort(Partitionable<T> part) {
        partAlgo = part;
        MIN_SIZE = 5;
    }

    
    public void sort(T[] a, int size) {
        quickSort(a, 0, size - 1);
    }

    private void quickSort(T[]a, int first, int last) {
        if (last - first+1 < MIN_SIZE) {
            insertionSort(a, first, last);
        } else {
            int pivotIndex = partAlgo.partition(a, first, last);
            quickSort(a, first, pivotIndex - 1);
            quickSort(a, pivotIndex + 1, last);
        }
    }

    private void insertionSort(T[] a, int first, int last) {
        for (int unsorted = first + 1; unsorted <= last; unsorted++) {
            T firstUnsorted = a[unsorted];
            int index;

            for (index = unsorted - 1; index >= first && firstUnsorted.compareTo(a[index]) < 0; index--) {
                a[index + 1] = a[index];
            }
            a[index + 1] = firstUnsorted;
        }
    }
    
    public void setMin(int min) {
        MIN_SIZE = min;
    }
}
