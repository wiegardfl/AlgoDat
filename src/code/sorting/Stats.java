package code.sorting;

public class Stats {
    public long sum, min, max;

    public Stats(){
        sum = 0;
        min = Long.MAX_VALUE;
        max = 0;
    }

    public void addData(long time){
        sum += time;
        min = Math.min(min, time);
        max = Math.max(max, time);
    }

    public String toString(int loops){
       return String.format(
               "Average time: %dµs\nMinimum time: %dµs\nMaximum time: %dµs\n",
               sum / loops / 1000,
               min / 1000,
               max / 1000
       );
    }
}
