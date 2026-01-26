package com.searchengine;

import java.io.IOException;

public class LuceneApp {
    
    private static final String INDEX_DIR = "data/index";
    private static final String DATA_DIR = "data/documents";
    
    public static void main(String[] args) {
        try {
            System.out.println("Starting indexing process...");
            System.out.println("Index directory: " + INDEX_DIR);
            System.out.println("Data directory: " + DATA_DIR);
            System.out.println("-----------------------------------");
            
            Indexer indexer = new Indexer(INDEX_DIR);
            
            long startTime = System.currentTimeMillis();
            int numIndexed = indexer.indexDirectory(DATA_DIR);
            long endTime = System.currentTimeMillis();
            
            indexer.close();
            
            System.out.println("-----------------------------------");
            System.out.println("Indexing completed!");
            System.out.println("Total documents indexed: " + numIndexed);
            System.out.println("Time taken: " + (endTime - startTime) + " ms");
            
        } catch (IOException e) {
            System.err.println("Error during indexing: " + e.getMessage());
            e.printStackTrace();
        }
    }
}