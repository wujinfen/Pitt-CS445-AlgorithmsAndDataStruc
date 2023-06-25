// CS 0445 Spring 2023
// Simple version of QuickSort that uses ARRAY[LAST] as the pivot and partitions based on that value

public class SimplePivot<T extends Comparable<? super T>> implements Partitionable<T>
{
    
	public void sort(T[] array, int n) {
		quickSort(array, 0, n-1);
	} 

	public void quickSort(T[] array, int first, int last) {
		if (first < last) {

			int pivotIndex = partition(array, first, last);
	      
			
			quickSort(array, first, pivotIndex-1);
			quickSort(array, pivotIndex+1, last);
		} 
	}  

	private void swap(Object [] a, int i, int j)
	{
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp; 
	} 

	public int partition(T[] a, int first, int last) {
		int pivotIndex = last;  
		T pivot = a[pivotIndex];

		int indexFromLeft = first; 
		int indexFromRight = last - 1; 

		boolean done = false;
		while (!done)
		{
			while (a[indexFromLeft].compareTo(pivot) < 0)
				indexFromLeft++;

			while (a[indexFromRight].compareTo(pivot) > 0 && indexFromRight > first)
				indexFromRight--;

			if (indexFromLeft < indexFromRight)
			{
				swap(a, indexFromLeft, indexFromRight);
				indexFromLeft++;
				indexFromRight--;
			}
			else 
				done = true;
		} 
		swap(a, pivotIndex, indexFromLeft);
		pivotIndex = indexFromLeft;

		return pivotIndex; 
	}
	}