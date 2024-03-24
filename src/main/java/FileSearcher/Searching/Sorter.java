package filesearcher.searching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import filesearcher.display.FileData;
import javafx.util.Pair;

public class Sorter {

    private final static String alpha = "!._0123456789abcdefghijklmnopqrstuvwxyz";
    private final static char[] characters = alpha.toCharArray();

    private static double[] swap(double[] arr, int i, int j) {
        double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return arr;
    }

    private static Pair<double[], Integer> split(double[] arr, int start, int end) {
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

    public static double[] recursiveQuickSort(double[] arr, int start, int end) {
        Pair<double[], Integer> pair = split(arr, start, end);
        arr = pair.getKey();
        int pivotLoc = pair.getValue();
        if (pivotLoc+1 < end) {
            arr = recursiveQuickSort(arr, pivotLoc+1, end);   
        }
        if (start < pivotLoc-1) {
            arr = recursiveQuickSort(arr, start, pivotLoc-1);
        }
        return arr;
    }

    public static double[] interativeQuickSort(double[] arr) {
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

    public static List<FileData> sortFiles(List<FileData> list) {
        List<FileData> sortedList = new ArrayList<>();
        double[] values = new double[list.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = alphabetIndex(list.get(i).name().getValue(), 4, i*0.00000000001);     // get a list of values corresponding to each file
        }
        HashMap<Double, FileData> hashMap = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            hashMap.put(values[i], list.get(i));    // pair each value with its file
        }
        if (values.length > 0) {
            recursiveQuickSort(values, 0, values.length-1);    // sort the values
            // interativeSort(values);
        }
        for (double i : values) {
            sortedList.add(hashMap.get(i));   // get the files from the sorted values
        }
        return sortedList;
    }

    private static double alphabetIndex(String str, int letterPos, double offset) { // find a strings position along the alphabet, letter checked is one at letterPos
        double index = -1;
        char letter;
        if (str != null) {
            for (int j = 0; j <= letterPos; j++) {
                if (j < str.length()) {
                    letter = str.toLowerCase().charAt(j);
                }
                else {
                    break;
                }
                for (int i = 0; i<characters.length; i++) {
                    if (letter == characters[i]) {
                        if (j == 0) {
                            index = i/(j+1);
                        }
                        else {
                            index += (double) i/(Math.pow(10, 2+(j*2)));
                        }
                    }
                }
            }
        }
        return index+offset;
    }

}