public class MergeSort<T extends Comparable<? super T>> implements Sorter<T> {
    private int MIN_SIZE;

    public MergeSort() {
        MIN_SIZE = 5;
    }

    public void sort(T[] a, int size) {
        mergeSort(a, size);
    }

    public void setMin(int min) {
        MIN_SIZE = min;
    }

    private void mergeSort(T[] a, int n) {
        mergeSort(a, 0, n - 1);
    }

    private void mergeSort(T[] a, int first, int last) {
        T[] tempArray = (T[]) new Comparable<?>[a.length];
        mergeSort(a, tempArray, first, last);
    }

    private void mergeSort(T[] a, T[] tempArray, int first, int last) {
        if (last - first + 1 <= MIN_SIZE) {
            insertionSort(a, first, last);
            return;
        }

        if (first < last) {
            int mid = (first + last) / 2;
            mergeSort(a, tempArray, first, mid);
            mergeSort(a, tempArray, mid + 1, last);

            if (a[mid].compareTo(a[mid + 1]) > 0)
                merge(a, tempArray, first, mid, last);
        }
    }

    private void insertionSort(T[] a, int first, int last) {
        for (int i = first + 1; i <= last; i++) {
            T temp = a[i];
            int j = i;

            while (j > first && a[j - 1].compareTo(temp) > 0) {
                a[j] = a[j - 1];
                j--;
            }

            a[j] = temp;
        }
    }

    private void merge(T[] a, T[] tempArray, int first, int mid, int last) {
        int beginHalf1 = first;
        int endHalf1 = mid;
        int beginHalf2 = mid + 1;
        int endHalf2 = last;

        int index = beginHalf1;
        while (beginHalf1 <= endHalf1 && beginHalf2 <= endHalf2) {
            if (a[beginHalf1].compareTo(a[beginHalf2]) <= 0) {
                tempArray[index++] = a[beginHalf1++];
            } else {
                tempArray[index++] = a[beginHalf2++];
            }
        }

        while (beginHalf1 <= endHalf1) {
            tempArray[index++] = a[beginHalf1++];
        }

        while (beginHalf2 <= endHalf2) {
            tempArray[index++] = a[beginHalf2++];
        }

        for (index = first; index <= last; index++) {
            a[index] = tempArray[index];
        }
    }
}
