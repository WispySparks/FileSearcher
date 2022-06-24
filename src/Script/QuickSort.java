package Script;

import javafx.util.Pair;

public class QuickSort {

    public double[] swap(double[] arr, int i, int j) {
        double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return arr;
    }

    public Pair<double[], Integer> split(double[] arr, int start, int end) {
        double pivot = arr[end];
        int j = start-1;
        for (int i = start; i<end; i++) {
            if (arr[i] < pivot) {
                j++;
                arr = swap(arr, i, j);
            }
        }
        arr = swap(arr, j+1, end);
        return new Pair<double[],Integer>(arr, j+1);
    }

    public double[] sort (double[] arr, int start, int end) {
        Pair<double[], Integer> pair = split(arr, start, end);
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