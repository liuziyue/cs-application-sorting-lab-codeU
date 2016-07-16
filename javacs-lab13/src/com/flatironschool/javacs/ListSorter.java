/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
        List<T> sorted = new ArrayList<>(list);
        mergeSortHelper(0, list.size() - 1, list, sorted, comparator);
        // System.out.println(sorted);
        return sorted;
	}

	private void mergeSortHelper(int start, int end, List<T> list, List<T> sorted, Comparator<T> comparator) {
		if (start < end) {
			int mid = (start + end) / 2;
			mergeSortHelper(start, mid, list, sorted, comparator);
			mergeSortHelper(mid + 1, end, list, sorted, comparator);
			merge(list, sorted, start, mid + 1, end, comparator);
		}
	}

	private void merge(List<T> list, List<T> sorted, int leftStart, int rightStart, int end, Comparator<T> comparator) {
		int k = leftStart;
		int leftEnd = rightStart - 1;
		while (leftStart <= leftEnd && rightStart <= end) {
			if (comparator.compare(list.get(leftStart), list.get(rightStart)) <= 0) {
				sorted.set(k, list.get(leftStart));
				leftStart++;

			} else {
				sorted.set(k, list.get(rightStart));
				rightStart++;
			}
			k++;
		}
		while (leftStart <= leftEnd) {
			sorted.set(k, list.get(leftStart));
			k++;
			leftStart++;
		}
		while (rightStart <= end) {
			sorted.set(k, list.get(rightStart));
			k++;
			rightStart++;
		}
		for (int i = 0; i < list.size(); i++) {
			list.set(i, sorted.get(i));
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
        PriorityQueue<T> queue = new PriorityQueue<T>(list.size(), comparator);
        for (int i = 0; i < list.size(); i++) {
        	queue.offer(list.get(i));
        }
        int i = 0;
        while (!queue.isEmpty()) {
        	list.set(i, queue.poll());
        	i++;
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
        PriorityQueue<T> queue = new PriorityQueue<T>(k, comparator);
       	List<T> topK = new ArrayList<T>();
        for (int i = 0; i < list.size(); i++) {
        	if (queue.size() >= k) {
        		if (comparator.compare(list.get(i), queue.peek()) >= 0) {
        			queue.poll();
        			queue.offer(list.get(i));
        		}
        	} else {
        		queue.offer(list.get(i));
        	}
        }
        while (!queue.isEmpty()) {
        	topK.add(queue.poll());
        }

        return topK;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
