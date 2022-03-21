package package6659666;

import java.util.*;
import java.io.*;

public class MemoryManagement {
    public static void main(String args[]) {
        Memory memory = Memory.getInstance();
        int[] exampleNumber = {1};
        readProcessesFromFile().forEach(process-> {
            memory.allocateMemory(process);
                if(readProcessesFromFile().stream().filter(process1 -> process1.getId() == process.getId()).count() > 1) {
                    System.out.println("***********************************START EXAMPLE NUMBER " + exampleNumber[0] + "***********************************");
                    System.out.println();
                    System.out.println("AFTER " + readProcessesFromFile().stream().filter(process1 -> process1.getId() == process.getId()).count() + " MEMORY ALLOCATIONS/DE-ALLOCATIONS FOR PROCESS " + process.getId() + ":");
                    System.out.println("*********************************************************");
                    for (int i = 0; i < process.getSegments().size(); i++) {
                        System.out.println("SEGMENT NUMBER: " + i);
                        long size = process.getSegments().get(i).getSize();
                        long base = process.getSegmentTable().getEntries().get(i).values().stream().
                                findFirst().get().values().stream().findFirst().get();
                        System.out.println("SIZE: " + size);
                        if(size == 0) {
                            System.out.println("STARTS AT ADDRESS: NULL");
                            System.out.println("ENDS AT ADDRESS: NULL");
                        }
                        else if (size == 1) {
                            System.out.println("STARTS AT ADDRESS: " + base);
                            System.out.println("ENDS AT ADDRESS: " + base);
                        }
                        else {
                            System.out.println("STARTS AT ADDRESS: " + base);
                            System.out.println("ENDS AT ADDRESS: " + (base + (int) process.getSegments().get(i).getSize()));
                        }

                        System.out.println();
                    }

                    System.out.println("MEMORY: ");
                    System.out.println("*****************************************************************************************************************");
                    System.out.println(Arrays.toString(memory.getMemory()));
                    System.out.println();

                    System.out.println("***********************************END EXAMPLE NUMBER " + exampleNumber[0] + "***********************************");
                    System.out.println();
                    exampleNumber[0]++;
                }
        });
    }

    /**
     * Reads processes from a file
     * @return the list of processes read
     */
    public static List<Process> readProcessesFromFile() {

        final File processesFile = new File("src\\package6659666\\processes.txt");
        final List<Process> processes = new ArrayList<>();

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(processesFile);
        } catch (FileNotFoundException e) {
            System.out.println("The \"processes.txt\" file could not be loaded. " +
                    "Ensure that the file exists and is named correctly");
        }
        if(processesFile.exists()) {
            while(fileScanner.hasNextLine()) {
                StringTokenizer tokenizer = new StringTokenizer(fileScanner.nextLine(), ",");
                List<Segment> segments = new ArrayList<>();
                int processId = Integer.parseInt(tokenizer.nextToken().trim());
                while(tokenizer.hasMoreTokens()) {
                    long segmentSize = Long.parseLong(tokenizer.nextToken().trim());
                    segments.add(new Segment(segmentSize));
                }
                processes.add(new Process(processId, segments, new SegmentTable()));

            }
            return processes;
        }
        return new ArrayList<>();
    }
}
