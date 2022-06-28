package Script;

import java.util.PriorityQueue;
import java.util.Queue;
import javafx.util.Pair;

public class QuickSort {

    Queue<Integer> pivotQueue = new PriorityQueue<Integer>();
    boolean done = false;

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
    static int depth = 0;
    public double[] sort (double[] arr, int start, int end) {
        Pair<double[], Integer> pair = split(arr, start, end);
        arr = pair.getKey();
        int pivotLoc = pair.getValue();
        while (depth < 0) {
            depth++;
            if (pivotQueue.peek() != null) {
                pivotLoc = pivotQueue.poll();
            }
            if (pivotLoc+1 < end) {
                pair = split(arr, pivotLoc+1, end); 
                arr = pair.getKey();
                pivotQueue.add(pair.getValue());
            }
            if (start < pivotLoc-1) {
                pair = split(arr, start, pivotLoc-1);
                arr = pair.getKey();
                pivotQueue.add(pair.getValue());
            }
        }
        // if (pivotLoc+1 < end) {
        //     arr = sort(arr, pivotLoc+1, end);   
        // }
        // if (start < pivotLoc-1) {
        //     arr = sort(arr, start, pivotLoc-1);
        // }
        return arr;
    }

}