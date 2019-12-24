import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;
public class MemoryNoCache {

    Hashtable<String,Long> cache = new Hashtable<>();

    // Constants used for calculation of time statistics, you use these in the functions you are writing
    // And don't particularly need to understand them.
    // Mostly the emulate the table of device specifications in the instructions.
    // All times are translated to nanoseconds and all data lengths are in bytes.

    final int RAM = 0;
    final int SSD = 1;
    final int HDD = 2;

    final int CACHE_SIZE = 12;

    final double CACHE_READ_TIME = (1/4000000000000.0) * (1000000000.0) * 1000;
    final double CACHE_WRITE_TIME = (1/3000000000000.0) * (1000000000.0) * 1000;
    final double CACHE_LATENCY_TIME = 1;

    double cacheTotalTime = 0;
    double cacheReadTimeTotal = 0;
    double cacheWriteTimeTotal = 0;
    double cacheLatencyTimeTotal = 0;

    final double RAM_READ_TIME =  (1/20000000000.0) * (1000000000.0) * 1000;
    final double RAM_WRITE_TIME = (1/10000000000.0) * (1000000000.0) * 1000;
    final double RAM_LATENCY_TIME = 100;

    double ramTotalTime = 0;
    double ramReadTimeTotal = 0;
    double ramWriteTimeTotal = 0;
    double ramLatencyTimeTotal = 0;

    final double SSD_READ_TIME = (1/200000000.0) * (1000000000.0) * 1000;
    final double SSD_WRITE_TIME = (1/50000000.0) * (1000000000.0) * 1000;
    final double SSD_LATENCY_TIME = 15000;

    double ssdTotalTime = 0;
    double ssdReadTimeTotal = 0;
    double ssdWriteTimeTotal = 0;
    double ssdLatencyTimeTotal = 0;

    // Helper function used by readCacheTime and writeCacheTime to
    // Calculate the time cost of accessing the cache.

    public void accessCacheTime() {
        cacheTotalTime += CACHE_LATENCY_TIME;
        cacheLatencyTimeTotal += CACHE_LATENCY_TIME;
    }


    // Used by the load and copy instructions when sending a value from the cache.
    public void readCacheTime() {
        accessCacheTime();
        cacheTotalTime += CACHE_READ_TIME;
        cacheReadTimeTotal += CACHE_READ_TIME;
    }

    // Used by the load and copy instructions when writing a value to the cache.
    public void writeCacheTime() {
        accessCacheTime();
        cacheTotalTime += CACHE_WRITE_TIME;
        cacheWriteTimeTotal += CACHE_WRITE_TIME;
    }


    // Used by load instruction to calculate the
    // Time statistics used to read from a type of memory.
    public void readFromType(int sourceType) {
        switch(sourceType) {
            case RAM:
                readRAMTime();
                break;
            case SSD:
                readSSDTime();
                break;
        }
    }

    // Used by copy instruction to calculate the
    // Time statistics used to write to a type of memory.
    public void writeToType(int sourceType) {
        switch(sourceType) {
            case RAM:
                writeRAMTime();
                break;
            case SSD:
                writeSSDTime();
                break;
        }
    }


    // The following section of functions are helper functions to help calculate the final statistics for 
    // Each type of memory,
    // They are only used by readFromType, writeToType, and writeByteToType
    // Do not call any of these functions in the functions that you write.

    public void accessRAMTime() {
        ramTotalTime += RAM_LATENCY_TIME;
        ramLatencyTimeTotal += RAM_LATENCY_TIME;
    }

    public void readRAMTime() {
        accessRAMTime();
        ramTotalTime += RAM_READ_TIME;
        ramReadTimeTotal += RAM_READ_TIME;
    }

    public void writeRAMTime() {
        accessRAMTime();
        ramTotalTime += RAM_WRITE_TIME;
        ramWriteTimeTotal += RAM_WRITE_TIME;
    }

    public void accessSSDTime() {
        ssdTotalTime += SSD_LATENCY_TIME;
        ssdLatencyTimeTotal += SSD_LATENCY_TIME;
    }

    public void readSSDTime() {
        accessSSDTime();
        ssdTotalTime += SSD_READ_TIME;
        ssdReadTimeTotal += SSD_READ_TIME;
    }

    public void writeSSDTime() {
        accessSSDTime();
        ssdTotalTime += SSD_WRITE_TIME;
        ssdWriteTimeTotal += SSD_WRITE_TIME;
    }

    // End of helper functions.


    // Displays the content of the cache for debugging purposes.
    public void displayCache() {
        for (String key : cache.keySet()) {
            System.out.println(key + " " + cache.get(key).toString());
        }
    }

    // Produces the final output table, only shows groups that have values.
    // Pretty weakly formatted, feel free to change if you want to make it look nicer.
    public void displayTotals() {
        double totalTime = cacheTotalTime + ramTotalTime + ssdTotalTime;
        if (cacheTotalTime > 0) {

            double cacheReadPercent = cacheReadTimeTotal / cacheTotalTime;
            double cacheReadTotalPercent = cacheReadTimeTotal / totalTime;
            double cacheWritePercent = cacheWriteTimeTotal / cacheTotalTime;
            double cacheWriteTotalPercent = cacheWriteTimeTotal / totalTime;
            double cacheLatencyPercent = cacheLatencyTimeTotal / cacheTotalTime;
            double cacheLatencyTotalPercent = cacheLatencyTimeTotal / totalTime;
            double cacheTotalPercent = cacheTotalTime / totalTime;
            System.out.println("CACHE READ: " + cacheReadTimeTotal + " " + cacheReadPercent + " " + cacheReadTotalPercent );
            System.out.println("CACHE WRITE: " + cacheWriteTimeTotal + " " + cacheWritePercent + " " + cacheWriteTotalPercent);
            System.out.println("CACHE LATENCY: " + cacheLatencyTimeTotal + " " + cacheLatencyPercent + " " + cacheLatencyTotalPercent);
            System.out.println("CACHE TOTAL: " + cacheTotalTime + " " + cacheTotalPercent);
        }
        if (ramTotalTime > 0) {
            double ramReadPercent = ramReadTimeTotal / ramTotalTime;
            double ramReadTotalPercent = ramReadTimeTotal / totalTime;
            double ramWritePercent = ramWriteTimeTotal / ramTotalTime;
            double ramWriteTotalPercent = ramWriteTimeTotal / totalTime;
            double ramLatencyPercent = ramLatencyTimeTotal / ramTotalTime;
            double ramLatencyTotalPercent = ramLatencyTimeTotal / totalTime;
            double ramTotalPercent = ramTotalTime / totalTime;
            System.out.println("RAM READ: " + ramReadTimeTotal + " " + ramReadPercent + " " + ramReadTotalPercent );
            System.out.println("RAM WRITE: " + ramWriteTimeTotal + " " + ramWritePercent + " " + ramWriteTotalPercent);
            System.out.println("RAM LATENCY: " + ramLatencyTimeTotal + " " + ramLatencyPercent + " " + ramLatencyTotalPercent);
            System.out.println("RAM TOTAL: " + ramTotalTime + " " + ramTotalPercent);
        }
        if (ssdTotalTime > 0) {
            double ssdReadPercent = ssdReadTimeTotal / ssdTotalTime;
            double ssdReadTotalPercent = ssdReadTimeTotal / totalTime;
            double ssdWritePercent = ssdWriteTimeTotal / ssdTotalTime;
            double ssdWriteTotalPercent = ssdWriteTimeTotal / totalTime;
            double ssdLatencyPercent = ssdLatencyTimeTotal / ssdTotalTime;
            double ssdLatencyTotalPercent = ssdLatencyTimeTotal / totalTime;
            double ssdTotalPercent = ssdTotalTime / totalTime;
            System.out.println("SSD READ: " + ssdReadTimeTotal + " " + ssdReadPercent + " " + ssdReadTotalPercent );
            System.out.println("SSD WRITE: " + ssdWriteTimeTotal + " " + ssdWritePercent + " " + ssdWriteTotalPercent);
            System.out.println("SSD LATENCY: " + ssdLatencyTimeTotal + " " + ssdLatencyPercent + " " + ssdLatencyTotalPercent);
            System.out.println("SSD TOTAL: " + ssdTotalTime + " " + ssdTotalPercent);
        }

        double totalRead = cacheReadTimeTotal + ramReadTimeTotal + ssdReadTimeTotal;
        double totalReadPercent = totalRead / totalTime;
        double totalWrite = cacheWriteTimeTotal + ramWriteTimeTotal + ssdWriteTimeTotal;
        double totalWritePercent = totalWrite / totalTime;
        double totalLatency = cacheLatencyTimeTotal + ramLatencyTimeTotal + ssdLatencyTimeTotal;
        double totalLatencyPercent = totalLatency / totalTime;
        System.out.println("TOTAL READ: " + totalRead + " " + totalReadPercent);
        System.out.println("TOTAL WRITE: " + totalWrite + " " + totalWritePercent);
        System.out.println("TOTAL LATENCY: " + totalLatency + " " + totalLatencyPercent);
        System.out.println("TOTAL TIME: " + totalTime);
    }

    // THE FUNCTIONS YOU HAVE TO MAKE

    // Implements the load instruction,
    // Makes any necessary changes to the contents of the cache and the time statistics
    public void load(int sourceType) {
        readFromType(sourceType);
    }

    // Implements the copy instruction
    // Makes any necessary changes to the contents of the cache and the time statistics
    public void copy(int sourceType, int destType) {
        load(sourceType);
        writeToType(destType);
    }

    // Reads in the instructions until the stop command is recieved after which it prints the statistics.
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.err.println("Filename argument missing.");
            System.err.println("Usage: <command> <filename>");
            System.exit(1);
        }

        MemoryNoCache myMemory = new MemoryNoCache();
        File file = new File(args[0]);
        myMemory.fileParser(file);
    }

    public void fileParser(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);

        while(scan.hasNextLine()){
            String input = scan.nextLine();
            if(input.charAt(0) == '/'){
                continue;
            }
            String[] line = input.split(" ");
            if(line[0].equals("LOAD")){
                load(Integer.parseInt(line[1]));
            }else if(line[0].equals("COPY")){
                copy(Integer.parseInt(line[1]), Integer.parseInt(line[3]));
            }else if(line[0].equals("STOP")){
                displayTotals();
            }
        }
    }
}