package nl.saxion.concurrency.assignment1;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Opdracht3 {


    public static void main(String[] args) {
        System.out.println("Starting the application!");

        Scanner scanner = new Scanner(System.in);
        System.out.println("How many numbers do you wish to sort?");
        System.out.print("Amount: ");
        int amountToSort = scanner.nextInt();
        System.out.println("How many times do you wish tot run it?");
        System.out.print("Times: ");
        int timesToRun = scanner.nextInt();
        int[] durations = new int[timesToRun];

        for (int i = 0; i < timesToRun; i++) {
            //create the array with random numbers
            int[] randomNumbers = new Random().ints(amountToSort, 0, Integer.MAX_VALUE).toArray();

            //split the array in 2 half's
            int[] firstHalf = Arrays.copyOfRange(randomNumbers, 0, randomNumbers.length/2);
            int[] secondHalf = Arrays.copyOfRange(randomNumbers, randomNumbers.length/2, randomNumbers.length);

            //start timer
            Instant start = Instant.now();

            //start threads to sort both halves of the array
            Thread threadLeft = new Thread(()->{Sort.bubbleSort(firstHalf);});
            Thread threadRight = new Thread(()->{Sort.bubbleSort(secondHalf);});
            threadLeft.start();
            threadRight.start();

            //join the threads and merge the halves
            try {
                threadLeft.join();
                threadRight.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Sort.merge(firstHalf, secondHalf);

            //print the duration for sorting + merging
            int durationInMilliSeconds = (int) Duration.between(start, Instant.now()).toMillis();
            System.out.println("("+(i+1)+"/"+timesToRun+") The sorting took: " + durationInMilliSeconds +
                    " ms for " + amountToSort + " numbers!");

            //add the duration to a list to keep track of the average sorting durations.
            durations[i] = durationInMilliSeconds;
        }

        //sort all the durations so it's easy to remove the shortest and longest
        Sort.bubbleSort(durations);

        if(Sort.isSorted(durations)){
            int totalDuration = 0;
            //skip first and last (shortest and longest time)
            for (int i = 1; i < durations.length -1; i++) {
                totalDuration += durations[i];
            }
            int average = totalDuration / (durations.length-2);
            System.out.println("It took " + average + " ms in average to sort " + amountToSort + " random numbers");
        }
        else{
            System.out.println("Failed to sort the average durations");
        }
    }
}
