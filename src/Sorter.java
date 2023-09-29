import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sorter {
    //Quicksort by average score

    //Swap two array positions
    private static void swap(Serie[] arr, int index, int index2) {
        Serie aux = arr[index];
        arr[index] = arr[index2];
        arr[index2] = aux;
    }

    //Sorts the value at the middle position and returns its sorted position (pivot)
    private static int partition(Serie[] arr, int start, int end) {
        int i = start, j = end;
        int pivot = arr[(start+end)/2].getAverageScore();
        while (i <= j) {
            while (arr[i].getAverageScore() < pivot) i++;
            while (arr[j].getAverageScore() > pivot) j--;
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        return i;
    }

    public static void quickSort(Serie[] arr, int start, int end) {
        int index = partition(arr, start, end);
        if (start < index - 1) quickSort(arr, start, index-1);
        if (index < end) quickSort(arr, index, end);
    }

    //Merge sort by date (old - new)
    private static Serie[] merge (Serie[] arrOne, Serie[] arrTwo) {
        Serie[] merged = new Serie[arrOne.length + arrTwo.length];
        List<Serie> listOne = new ArrayList<>(Arrays.asList(arrOne));
        List<Serie> listTwo = new ArrayList<>(Arrays.asList(arrTwo));
        int i = 0;
        while (listOne.size() > 0 && listTwo.size() > 0) {
            if (listOne.get(0).getStartDate().after(listTwo.get(0).getStartDate())) {
                merged[i++] = listTwo.get(0);
                listTwo.remove(0);
            } else {
                merged[i++] = listOne.get(0);
                listOne.remove(0);
            }
        }
        while (listOne.size() > 0) {
            merged[i++] = listOne.get(0);
            listOne.remove(0);
        }
        while (listTwo.size() > 0) {
            merged[i++] = listTwo.get(0);
            listTwo.remove(0);
        }
        return merged;
    }

    public static Serie[] mergeSort (Serie[] arr) {
        if (arr.length == 1) return arr;

        Serie[] arrOne = Arrays.copyOfRange(arr, 0, arr.length/2);
        Serie[] arrTwo = Arrays.copyOfRange(arr, arr.length/2, arr.length);

        arrOne = mergeSort(arrOne);
        arrTwo = mergeSort(arrTwo);

        return merge(arrOne, arrTwo);
    }

    //Bucket sort by combined priorities
    private static Serie[] putInBucket(Serie[] bucket, Serie in) {
        Serie[] newBucket = new Serie[bucket.length+1];
        newBucket[0] = in;
        System.arraycopy(bucket, 0, newBucket, 1, newBucket.length - 1);
        return newBucket;
    }

    private static int bsPartition(Serie[] arr, int start, int end) {
        int pivot = start;
        float value = arr[end].getCombinedPriorities();
        for (int i = start; i < end; i++) {
            if (arr[i].getCombinedPriorities() > value) {
                swap(arr, i, pivot);
                pivot++;
            }
        }
        swap(arr, pivot, end);
        return pivot;
    }

    private static void bsQuickSort(Serie[] arr, int start, int end) {
        if (start >= end) return;
        int pivot = bsPartition(arr, start, end);
        bsQuickSort(arr, start, pivot-1);
        bsQuickSort(arr, pivot+1, end);
    }

    public static void bucketSort (Serie[] arr, int numBuckets) {
        //Average score ranges between 0 and 100, so that is the min and max values used
        int MAX_VAL = 10, MIN_VAL = 0;

        double range = (double)(MAX_VAL - MIN_VAL) / numBuckets;
        Serie[][] buckets = new Serie[numBuckets][0];

        //Insert values into buckets
        for (Serie serie : arr) {
            int index = (int)(serie.getCombinedPriorities()/range);
            buckets[index] = putInBucket(buckets[index], serie);
        }
        //Sort each bucket
        for (Serie[] bucket : buckets) {
            bsQuickSort(bucket, 0, bucket.length-1);
        }
        //Merge every bucket into arr in descending order
        int j = 0;
        for (int i = buckets.length-1; i >= 0; i--) {
            for (Serie serie : buckets[i]) {
                arr[j++] = serie;
            }
        }
    }
}
