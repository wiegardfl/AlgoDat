package code.sorting;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        sortingTest(
                new Sort("QuickSort", A -> Sorting.quickSort(A)),
                //new Sort("MergeRestWithNoComparisonSort", A -> Sorting.mergeSortByHuman(A)),
                new Sort("RadixSort base 100", A -> Sorting.radixSort(A, 32)),
                new Sort("RadixSort base 10", A -> Sorting.radixSort(A, 10)),
                new Sort("RadixSort base 2", A -> Sorting.radixSort(A, 2))
        );

       /* int[] A = new int[]{121,834,343,456,142,547,455,224,34,98,45,9,6,3,232,4};
        print(A, "Unsorted: ");
        A = Sorting.radixSort(A, 10);
        print(A, "Sorted:");*/
    }

    static void sortingTest(Sort... algorithms){
        if (algorithms.length == 0) return;

        Random random = new Random();
        class Rand {
            int next(int ceiling) {
                return random.nextInt(1, ceiling);
            }
            int[] array(int length){
                int[] a = new int[length];
                for (int i = 0; i < length; i++)
                    a[i] = next(length);
                return a;
            }
        }

        int loops = 10;
        int maxExponent = 21;

        for (int i = 5; i < maxExponent; i+= 5) {
            int _length = 1 << i;
            Stats[] stats = new Stats[algorithms.length];

            for (int s = 0; s < stats.length; s++)
                stats[s] = new Stats();

            for (int j = 0; j < loops; j++) {
                int[][] AA = new int[algorithms.length][];
                AA[0] = new Rand().array(_length);
                for (int clone = 1; clone < AA.length; clone++)
                    AA[clone] = AA[0].clone();

                for (int k = 0; k < AA.length; k++){
                    long time = measureTime(algorithms[k].algorithm, AA[k]);
                    stats[k].addData(time);
                }
            }

            for (int algo = 0; algo < algorithms.length; algo++) {
                System.out.println(algorithms[algo].name + " on array with length " + (_length));
                System.out.print(stats[algo].toString(loops));
            }
            System.out.println();
        }
    }

    interface I {
        void execute();
    }

    interface IntArraySort {
        void sort(int[] A);
    }

    static class Sort {
        String name;
        IntArraySort algorithm;
        Sort(String name, IntArraySort algorithm){
            this.name = name;
            this.algorithm = algorithm;
        }
    }

    public static long measureTime(IntArraySort intArraySort, int[] A){
        var start = System.nanoTime();
        intArraySort.sort(A);
        var end = System.nanoTime();
        return end - start;
    }

    public static long measureTime(I myInterface){
        var start = System.nanoTime();
        myInterface.execute();
        var end = System.nanoTime();
        return end - start;
    }

    static void print(int[] A, String message){
        System.out.println(message);
        for (int i: A)
            System.out.print(i + "\t");
        System.out.println();
    }
}
