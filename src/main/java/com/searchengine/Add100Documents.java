package com.searchengine;

import java.io.File;

public class Add100Documents {
    
    private static final String INDEX_DIR = "data/index";
    private static final String DATA_DIR = "data/documents";
    
    public static void main(String[] args) {
        try {
            System.out.println("========================================");
            System.out.println("PART 3: Adding 100 New Documents");
            System.out.println("========================================\n");
            
            Indexer indexer = new Indexer(INDEX_DIR);
            
            File dataDir = new File(DATA_DIR);
            File[] newFiles = dataDir.listFiles((dir, name) -> name.startsWith("document_"));
            
            if (newFiles == null || newFiles.length == 0) {
                System.out.println("No new documents found!");
                return;
            }
            
            System.out.println("Found " + newFiles.length + " new documents to index");
            System.out.println("Starting indexing...\n");
            
            long startTime = System.currentTimeMillis();
            int indexed = 0;
            
            for (File file : newFiles) {
                indexer.indexFile(file);
                indexed++;
                if (indexed % 10 == 0) {
                    System.out.println("Indexed " + indexed + " documents...");
                }
            }
            
            long endTime = System.currentTimeMillis();
            indexer.close();
            
            System.out.println("\n========================================");
            System.out.println("Indexing Complete!");
            System.out.println("========================================");
            System.out.println("Total new documents indexed: " + indexed);
            System.out.println("Time taken: " + (endTime - startTime) + " ms");
            System.out.println("Average time per document: " + 
                             String.format("%.2f", (endTime - startTime) / (double)indexed) + " ms");
            
            System.out.println("\n========================================");
            System.out.println("Verifying New Documents Are Searchable:");
            System.out.println("========================================\n");
            
            Searcher searcher = new Searcher(INDEX_DIR);
            
            String[] keywords = {"research", "data mining", "cloud computing", "distributed", "analytics"};
            
            for (String keyword : keywords) {
                var hits = searcher.search(keyword, 5);
                System.out.println("Keyword: '" + keyword + "' -> Found in " + hits.totalHits + " documents");
            }
            
            System.out.println("\nConfirming original documents still indexed:");
            var oldHits = searcher.search("topic", 5);
            System.out.println("Keyword: 'topic' (from batch 1) -> Found in " + oldHits.totalHits + " documents");
            
            searcher.close();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}