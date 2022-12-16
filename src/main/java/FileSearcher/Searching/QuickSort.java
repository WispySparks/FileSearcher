package FileSearcher.Searching;

import java.util.Stack;

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

    public double[] recursiveSort(double[] arr, int start, int end) {
        Pair<double[], Integer> pair = split(arr, start, end);
        arr = pair.getKey();
        int pivotLoc = pair.getValue();
        if (pivotLoc+1 < end) {
            arr = recursiveSort(arr, pivotLoc+1, end);   
        }
        if (start < pivotLoc-1) {
            arr = recursiveSort(arr, start, pivotLoc-1);
        }
        return arr;
    }

    public double[] interativeSort(double[] arr) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(arr.length-1);
        while (!stack.isEmpty()) {
            int end = stack.pop();
            int start = stack.pop();
            if (end - start < 1) continue;
            Pair<double[], Integer> pair = split(arr, start, end);
            arr = pair.getKey();
            int pivotLoc = pair.getValue();
            stack.push(pivotLoc+1);
            stack.push(end);
            stack.push(start);
            stack.push(pivotLoc-1);
        }
        return arr;
    }

}