package nl.saxion.concurrency.assignment1;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Opdracht4 {


    public static void main(String[] args) {
        System.out.println("Starting the application!");

        Scanner scanner = new Scanner(System.in);
        System.out.println("How many numbers do you wish to sort?");
        System.out.print("Amount: ");
        int amountToSort = scanner.nextInt();
        System.out.println("How many times do you wish tot run it?");
        System.out.print("Times: ");
        int timesToRun = scanner.nextInt();
        System.out.println("What threshold to use for the splitting?");
        System.out.print("Threshold: ");
        int threshold = scanner.nextInt();
        int[] durations = new int[timesToRun];

        for (int i = 0; i < timesToRun; i++) {
            //create the array with random numbers
            int[] randomNumbers = new Random().ints(amountToSort, 0, 100).toArray();

            //start timer
            Instant start = Instant.now();

            Sort.splitSort(randomNumbers, threshold);

            System.out.println("is array sorted? : " + Sort.isSorted(randomNumbers));

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
