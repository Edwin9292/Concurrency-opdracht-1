package nl.saxion.concurrency.assignment1;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.Scanner;

public class Opdracht1 {


    public static void main(String[] args) {
        System.out.println("Starting the application!");

        //get user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many numbers do you wish to sort?");
        System.out.print("Amount: ");
        int amountToSort = scanner.nextInt();
        System.out.println("How many times do you wish tot run it?");
        System.out.print("Times: ");
        int timesToRun = scanner.nextInt();

        int[] durations = new int[timesToRun];

        for (int i = 0; i < timesToRun; i++) {
            //create array of random numbers
            int[] randomNumbers = new Random().ints(amountToSort, 0, Integer.MAX_VALUE).toArray();

            //start timer
            Instant start = Instant.now();

            //sort numbers
            Sort.bubbleSort(randomNumbers);

            //get the duration of the sorting, print it to console and add it to the durations array
            int durationInMilliSeconds = (int) Duration.between(start, Instant.now()).toMillis();
            System.out.println("("+(i+1)+"/"+timesToRun+") The sorting took: " + durationInMilliSeconds +
                    " ms for " + amountToSort + " numbers!");
            durations[i] = durationInMilliSeconds;
        }

        //sort the array of durations
        Sort.bubbleSort(durations);
        if(Sort.isSorted(durations)){  //extra check to make sure the durations are actually sorted so the average will be correct.
            int totalDuration = 0;
            //skip first and last (min ans max duration, since the durations are sorted)
            for (int i = 1; i < durations.length -1; i++) {
                totalDuration += durations[i];
            }
            //calculate the average duration and print it to console
            int average = totalDuration / (durations.length-2);
            System.out.println("It took " + average + " ms in average to sort " + amountToSort + " random numbers");
        }
        else{
            System.out.println("Failed to sort the average durations");
        }
    }
}
