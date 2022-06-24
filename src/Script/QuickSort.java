package Script;

import javafx.util.Pair;

public class QuickSort {

    public int[] swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return arr;
    }

    public Pair<int[], Integer> split(int[] arr, int start, int end) {
        int pivot = arr[end];
        int j = start-1;
        for (int i = start; i<end; i++) {
            if (arr[i] < pivot) {
                j++;
                arr = swap(arr, i, j);
            }
        }
        arr = swap(arr, j+1, end);
        return new Pair<int[],Integer>(arr, j+1);
    }

    public int[] sort (int[] arr, int start, int end) {
        Pair<int[], Integer> pair = split(arr, start, end);
        arr = pair.getKey();
        int pivotLoc = pair.getValue();
        if (pivotLoc+1 < end) {
            arr = sort(arr, pivotLoc+1, end);   
        }
        if (start < pivotLoc-1) {
            arr = sort(arr, start, pivotLoc-1);
        }
        return arr;
    }

}