package nl.saxion.concurrency.assignment1;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class SortTask extends RecursiveTask<int[]> {
    private final int[] data;
    private final int threshold;

    public SortTask(int[] data, int threshold){
        this.data = data;
        this.threshold = threshold;
    }


    @Override
    protected int[] compute() {
        int n = data.length;
        //check if the array length is more than the threshold
        if(n > threshold){
            //split the array in 2 half's
            int[] firstHalf = Arrays.copyOfRange(data, 0, n/2);
            int[] secondHalf = Arrays.copyOfRange(data, n/2, n);

            var leftTask = new SortTask(firstHalf, threshold);
            var rightTask = new SortTask(secondHalf, threshold);

            leftTask.fork();
            rightTask.fork();

            int[] leftSorted = leftTask.join();
            int[] rightSorted = rightTask.join();

            return Sort.merge(leftSorted, rightSorted);
        }
        else{
            Sort.bubbleSort(data);
            return data;
        }
    }
}
