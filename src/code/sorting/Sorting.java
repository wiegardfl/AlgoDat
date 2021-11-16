package code.sorting;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Sorting {
    final static Random random = new Random();
    final static Scanner scanner = new Scanner(System.in);

    static int linearSearch(int[] A, int element){
        for (int i = 0; i < A.length; i++)
            if (A[i] == element)
                return i;
        return -1;
    }

    static int binarySearch(int[] A, int element){
        return binarySearch(A, element, 0, A.length - 1);
    }

    static int binarySearch(int[] A, int element, int p, int q){
        int middle = (p + q)/2;
        if (A[middle] == element)
            return middle;
        else if (p >= q)
            return -1;
        else if (A[middle] > element)
            return binarySearch(A, element, p, middle - 1);
        else return binarySearch(A, element, middle + 1, q);
    }

    static int binarySearchLeftmostGreater(int[] A, int element, int p, int q){
        int mean = (p + q)/2;
        if (p >= q && A[mean] > element)
            return mean;
        else if ((mean < p || A[mean] <= element) && (mean >= q || A[mean+1] > element))
            return mean+1;
        else if (A[mean] > element)
            return binarySearchLeftmostGreater(A, element, p, mean);
        else
            return binarySearchLeftmostGreater(A, element, mean + 1, q);
    }

    static int binarySearchLeftmostGreater(int[] A, int element){
        return binarySearchLeftmostGreater(A, element, 0, A.length - 1);
    }

    static boolean isSortedReverse(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            if (a[i] < a[i - 1])
                return false;
        }
        return true;
    }

    static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1])
                return false;
        }
        return true;
    }

    static void insertionSort(int[] A){
        for (int i = 1; i < A.length; i++){
            int j = i;
            int element = A[i];
            while (j > 0 && element < A[j-1]){
                A[j] = A[j-1];
                j -= 1;
            }
            A[j] = element;
        }
    }

    static void insertionSort_binSearch(int[] A) {
        for (int i = 1; i < A.length; i++) {
            int element = A[i];
            if (element < A[i - 1]) {
                int leftmostGreater = binarySearchLeftmostGreater(A, element, 0, i - 1);
                for (int k = i; k > leftmostGreater; k--)
                    A[k] = A[k - 1];
                A[leftmostGreater] = element;
            }
        }
    }

    static void merge(int[] A, int p, int q, int r){
        int     n_1 = q - p + 1,
                n_2 = r - q;
        int[]   B = new int[n_1 + 1],
                C = new int[n_2 + 1];
        for (int i = 0; i < n_1; i++)
            B[i] = A[p + i];
        B[n_1] = Integer.MAX_VALUE;
        for (int j = 0; j < n_2; j++)
            C[j] = A[q + j + 1];
        C[n_2] = Integer.MAX_VALUE;

        int b = 0, c = 0;
        for (int k = p; k <= r; k++){
            if (B[b] <= C[c]){
                A[k] = B[b];
                b++;
            } else {
                A[k] = C[c];
                c++;
            }
        }
    }

    static void mergeByHuman(int[] A, int p, int q, int r){
        int     n_1 = q - p + 1,
                n_2 = r - q;
        int[]   B = new int[n_1],
                C = new int[n_2];
        for (int i = 0; i < n_1; i++)
            B[i] = A[p + i];
        for (int j = 0; j < n_2; j++)
            C[j] = A[q + j + 1];

        int b = 0, c = 0;
        for (int k = p; k <= r; k++){
            if (b == B.length){
                A[k] = C[c];
                c++;
            }
            else if (c == C.length || B[b] <= C[c] /*AskHuman(B[b], C[c])*/){
                A[k] = B[b];
                b++;
            } else {
                A[k] = C[c];
                c++;
            }
        }
    }

    static boolean AskHuman(int x, int y){
        System.out.printf("Is %d <= %d? [y/n]\n", x, y);
        String read = scanner.next();
        switch (read){
            case "y":
                return true;
            case "n":
                return false;
            default:
                System.out.println("Invalid character. Try again!");
                return AskHuman(x, y);
        }
    }

    static void mergeSort(int[] A, int p, int r){
        if (p < r){
            int q = (p + r) / 2;
            mergeSort(A, p, q);
            mergeSort(A, q + 1, r);
            merge(A, p, q, r);
        }
    }

    static void mergeSortByHuman(int[] A, int p, int r){
        if (p < r){
            int q = (p + r) / 2;
            mergeSortByHuman(A, p , q);
            mergeSortByHuman(A, q +1, r);
            mergeByHuman(A, p, q, r);
        }
    }

    static void mergeSort(int[] A){
        mergeSort(A, 0, A.length - 1);
    }

    static void mergeSortByHuman(int[] A){
        mergeSortByHuman(A, 0, A.length - 1);
    }

    static void swap(int[] A, int i, int j){
        if (i != j) {
            A[i] += A[j];
            A[j] = A[i] - A[j];
            A[i] -= A[j];
        }
    }

    static int partition(int[] A, int p, int r){
        int pivot = A[r];
        int i = p;
        for (int j = p; j < r; j++)
            if (A[j] <= pivot){
                swap(A, i, j);
                i++;
            }
        swap(A, i, r);
        return i;
    }

    static int randomizedPartition(int[] A, int p, int r){
        int i = random.nextInt(p, r);
        swap(A, i, r);
        return partition(A,p,r);
    }

    static int randomizedMedianPartition(int[] A, int p, int r){
        /*int[] randoms = new int[2];
        randoms[0] = random.nextInt(p,r+1);
        randoms[1] = random.nextInt(p,r+1);
        if (A[randoms[1]] < A[randoms[0]])
            swap(randoms, 0, 1);
        int i = random.nextInt(p, r+1);
        i = A[i] > A[randoms[1]] ? randoms[1] : (A[i] < A[randoms[0]]) ? randoms[0] : i;*/

        int j = random.nextInt(p, r+1);
        int k = random.nextInt(p, r+1);

        swap(A, A[j] > A[k] ? k : j, r);
        return partition(A,p,r);
    }

    static void quickSort(int[] A, int p, int r){
        if (p < r){
            int q = partition(A, p, r);
            quickSort(A, p, q-1);
            quickSort(A, q + 1, r);
        }
    }

    static void randomizedQuickSort(int[] A, int p, int r){
        if (p < r){
            int q = randomizedPartition(A, p, r);
            randomizedQuickSort(A, p, q-1);
            randomizedQuickSort(A, q+1, r);
        }
    }

    static void randomizedMedianQuickSort(int[] A, int p , int r){
        if (p < r){
            int q = randomizedMedianPartition(A,p,r);
            randomizedMedianQuickSort(A,p,q-1);
            randomizedMedianQuickSort(A, q+1, r);
        }
    }

    static void quickSort(int[] A){
        quickSort(A, 0, A.length - 1);
    }

    static void randomizedQuickSort(int[] A){
        randomizedQuickSort(A, 0, A.length - 1);
    }

    static void randomizedMedianQuickSort(int[] A){
        randomizedMedianQuickSort(A, 0, A.length - 1);
    }


    static int[] countingSort(int[] key, int[] value, int k){
        int[] C = new int[k+1];
        int[] result = new int[value.length];

        for (int i = 0; i < key.length; i++)
            C[key[i]]++;

        for (int i = 1; i < C.length; i++)
            C[i] += C[i-1];

        for (int i = key.length - 1; i >= 0; i--) {
            result[C[key[i]]-1] = value[i];
            C[key[i]]--;
        }

        return result;
    }

    static int[] countingSort(int[] A, int k){
        return countingSort(A, A, k);
    }

    static int[] radixSort(int[] A, int base, int d){
        int significance = 1;
        for (int i = 0; i < d; i++) {
            int denominator = significance;
            int[] keys = Arrays.stream(A).map(e -> (e / denominator) % base).toArray();
            A = countingSort(keys, A, base-1);
            significance *= base;
        }
        return A;
    }

    static int[] radixSort(int[] A, int base){
        int d = Arrays.stream(A).reduce(0, (accu, x) -> Math.max(accu, (int)(Math.log10((double)x) / Math.log10(base))));
        return radixSort(A, base, d + 1);
    }
}