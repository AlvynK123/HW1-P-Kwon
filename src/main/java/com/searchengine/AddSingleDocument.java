package com.searchengine;

import java.io.File;

public class AddSingleDocument {
    
    private static final String INDEX_DIR = "data/index";
    private static final String NEW_DOC = "data/documents/info_intel_thread.html";
    
    public static void main(String[] args) {
        try {
            System.out.println("========================================");
            System.out.println("PART 2: Adding Single Web Document");
            System.out.println("========================================\n");
            
            System.out.println("Document to add: " + NEW_DOC);
            
            Indexer indexer = new Indexer(INDEX_DIR);
            
            File docFile = new File(NEW_DOC);
            long startTime = System.currentTimeMillis();
            indexer.indexFile(docFile);
            long endTime = System.currentTimeMillis();
            
            indexer.close();
            
            System.out.println("\n✓ Document added successfully!");
            System.out.println("Time taken: " + (endTime - startTime) + " ms");
            
            System.out.println("\n========================================");
            System.out.println("Searching for keywords from new document:");
            System.out.println("========================================\n");
            
            Searcher searcher = new Searcher(INDEX_DIR);
            
            String[] keywords = {"Georgia", "Thread", "Intelligence", "Internetworks", "curriculum"};
            
            for (String keyword : keywords) {
                var hits = searcher.search(keyword, 5);
                System.out.println("Keyword: '" + keyword + "' -> Found in " + hits.totalHits + " documents");
            }
            
            searcher.close();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}