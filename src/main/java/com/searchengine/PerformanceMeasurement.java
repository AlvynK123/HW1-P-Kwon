package com.searchengine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PerformanceMeasurement {
    
    private static final String INDEX_DIR = "data/index";
    private static final String DATA_DIR = "data/documents";
    private static final String RESULTS_FILE = "performance_results.csv";
    
    public static void main(String[] args) {
        try {
            System.out.println("========================================");
            System.out.println("PART 4: Performance Measurement");
            System.out.println("========================================\n");
            
            clearIndex();
            
            int[] kValues = {100, 200, 300, 400, 500};
            List<PerformanceResult> results = new ArrayList<>();
            
            for (int k : kValues) {
                System.out.println("Testing with K = " + k + " documents...");
                
                clearIndex();
                
                File[] files = getFirstKDocuments(k);
                
                if (files.length < k) {
                    System.out.println("Warning: Only found " + files.length + " documents");
                }
                
                long startTime = System.currentTimeMillis();
                
                Indexer indexer = new Indexer(INDEX_DIR);
                for (File file : files) {
                    indexer.indexFile(file);
                }
                indexer.close();
                
                long endTime = System.currentTimeMillis();
                long timeMs = endTime - startTime;
                double avgTimePerDoc = timeMs / (double) files.length;
                
                PerformanceResult result = new PerformanceResult(k, files.length, timeMs, avgTimePerDoc);
                results.add(result);
                
                System.out.println("  Indexed: " + files.length + " documents");
                System.out.println("  Time: " + timeMs + " ms");
                System.out.println("  Avg per doc: " + String.format("%.2f", avgTimePerDoc) + " ms\n");
            }
            
            saveResultsToCSV(results);
            
            printSummaryTable(results);
            
            System.out.println("\n✓ Performance results saved to: " + RESULTS_FILE);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void clearIndex() throws IOException {
        Path indexPath = Paths.get(INDEX_DIR);
        if (Files.exists(indexPath)) {
            Files.walk(indexPath)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        }
        Files.createDirectories(indexPath);
    }
    
    private static File[] getFirstKDocuments(int k) {
        File dataDir = new File(DATA_DIR);
        File[] allFiles = dataDir.listFiles((dir, name) -> 
            name.endsWith(".txt") && !name.endsWith(".sh"));
        
        if (allFiles == null) {
            return new File[0];
        }
        
        java.util.Arrays.sort(allFiles);
        
        int count = Math.min(k, allFiles.length);
        File[] result = new File[count];
        System.arraycopy(allFiles, 0, result, 0, count);
        return result;
    }
    
    private static void saveResultsToCSV(List<PerformanceResult> results) throws IOException {
        FileWriter writer = new FileWriter(RESULTS_FILE);
        
        writer.write("K (Target Docs),Actual Docs Indexed,Total Time (ms),Avg Time per Doc (ms)\n");
        
        for (PerformanceResult result : results) {
            writer.write(String.format("%d,%d,%d,%.2f\n",
                result.k, result.actualDocs, result.timeMs, result.avgTimePerDoc));
        }
        
        writer.close();
    }
    
    private static void printSummaryTable(List<PerformanceResult> results) {
        System.out.println("\n========================================");
        System.out.println("PERFORMANCE SUMMARY TABLE");
        System.out.println("========================================");
        System.out.println(String.format("%-15s %-15s %-20s %-20s",
            "K Documents", "Actual Docs", "Total Time (ms)", "Avg Time/Doc (ms)"));
        System.out.println("------------------------------------------------------------------------");
        
        for (PerformanceResult result : results) {
            System.out.println(String.format("%-15d %-15d %-20d %-20.2f",
                result.k, result.actualDocs, result.timeMs, result.avgTimePerDoc));
        }
    }
    
    static class PerformanceResult {
        int k;
        int actualDocs;
        long timeMs;
        double avgTimePerDoc;
        
        PerformanceResult(int k, int actualDocs, long timeMs, double avgTimePerDoc) {
            this.k = k;
            this.actualDocs = actualDocs;
            this.timeMs = timeMs;
            this.avgTimePerDoc = avgTimePerDoc;
        }
    }
}