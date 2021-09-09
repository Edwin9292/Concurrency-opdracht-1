package nl.saxion.concurrency.assignment1;

import java.util.Arrays;

public class Sort {


    /**
     * Merge two sorted arrays into a new sorted array
     *
     * @param left array
     * @param right array
     * @return the sorted merge of left and right
     *
     * for an explanation of the algorithm see: https://www.baeldung.com/java-merge-sorted-arrays
     */
    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int leftPos = 0, rightPos= 0, mergedPos = 0;
        while(leftPos < left.length && rightPos < right.length) {
            if (left[leftPos] < right[rightPos]) {
                result[mergedPos++] = left[leftPos++];
            } else {
                result[mergedPos++] = right[rightPos++];
            }
        }
        while (leftPos < left.length) result[mergedPos++] = left[leftPos++];
        while (rightPos < right.length) result[mergedPos++] = right[rightPos++];
        return result;
    }

    static void splitSort(int[] arr, int threshold){

        int n = arr.length;

        //check if the array length is more than the threshold
        if(n > threshold){
            //split the array in 2 half's
            int[] firstHalf = Arrays.copyOfRange(arr, 0, n/2);
            int[] secondHalf = Arrays.copyOfRange(arr, n/2, n);

            //start threads to sort both halves of the array
            Thread threadLeft = new Thread(()-> { Sort.splitSort(firstHalf, threshold);});
            Thread threadRight = new Thread(()->{ Sort.splitSort(secondHalf, threshold);});
            threadLeft.start();
            threadRight.start();

            // join the threads and merge the halves
            try {
                threadLeft.join();
                threadRight.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int[] mergedArray = Sort.merge(firstHalf, secondHalf);
            for (int i = 0; i < mergedArray.length; i++) {
                arr[i] = mergedArray[i];
            }
        }
        else{
            bubbleSort(arr);
        }
    }


    /**
    * bubbleSort function.
    * Sorts the array in
    * @param arr unsorted array
    *
    * Copied from: https://www.javatpoint.com/bubble-sort-in-java
    */
    static void bubbleSort(int[] arr) {
        int n = arr.length;
        int temp = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (arr[j - 1] > arr[j]) {
                    //swap elements
                    temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }

            }
        }
    }


    /**
     *
     * @param arr sorted array
     * @return true if array is sorted otherwise false
     */
    static boolean isSorted(int[] arr) {
        for(int i =1; i < arr.length; i++) {
            if (arr[i-1] > arr[i]) return false;
        }
        return true;
    }

}
