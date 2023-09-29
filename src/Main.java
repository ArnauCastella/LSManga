import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

    private static void calculatePriorities(Serie[] arr) {
        float maxPopularity = 0;
        float maxFavourites = 0;
        //Find max popularity and favourites
        for (Serie serie : arr) {
            if (serie.getPopularity() > maxPopularity) maxPopularity = serie.getPopularity();
            if (serie.getFavourites() > maxFavourites) maxFavourites = serie.getFavourites();
        }
        for (Serie serie : arr) {
            serie.setCombinedPriorities((serie.getPopularity() / maxPopularity * 10 / 3) +
                    (serie.getFavourites() / maxFavourites * 10 / 3) +
                    (float) serie.getAverageScore() / 100 * 10 / 3);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select sorting method:\n\t1. Quicksort\n\t2. Merge sort\n\t3. Bucket sort\n");
        int sortMethod = scanner.nextInt();
        System.out.println("Select file size:\n\t1. Small\n\t2. Medium\n\t3. Large\n");
        switch (scanner.nextInt()) {
            case 1 -> filename = "Datasets JSON/series_S.json";
            case 2 -> filename = "Datasets JSON/series_M.json";
            case 3 -> filename = "Datasets JSON/series_L.json";
        }
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader(filename));
        Series seriesArray = gson.fromJson(jsonReader, Series.class);
        Serie[] arr = seriesArray.getSeries();
        calculatePriorities(arr);
        switch (sortMethod) {
            case 1 -> {
                //Quicksort
                long startTime = System.nanoTime();
                Sorter.quickSort(arr, 0, arr.length - 1);
                long stopTime = System.nanoTime();
                //for (Serie serie : arr) {
                //    System.out.println(serie.getTitle().getEnglish() + ' ' + serie.getCombinedPriorities());
                //}
                System.out.println("Sorted in " + (stopTime - startTime)/1000 + " microseconds.");
            }
            case 2 -> {
                //Merge sort
                long startTime = System.nanoTime();
                Serie[] sorted = Sorter.mergeSort(arr);
                long stopTime = System.nanoTime();
                //for (Serie serie : sorted) {
                //    System.out.println(serie.getTitle().getEnglish() + ' ' + serie.getStartDateString());
                //}
                System.out.println("Sorted in " + (stopTime - startTime)/1000 + " microseconds.");
            }
            case 3 -> {
                //Bucket sort
                long startTime = System.nanoTime();
                Sorter.bucketSort(arr, (int)Math.sqrt(arr.length));
                long stopTime = System.nanoTime();
                System.out.println("Sorted in " + (stopTime - startTime)/1000 + " microseconds.");
                /*
                for (Serie serie : arr) {
                    System.out.println(serie.getTitle().getEnglish() + ' ' + serie.getAverageScore());
                }
                */
            }
        }
    }
}
