package com.searchengine;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class SearchTest {
    
    private static final String INDEX_DIR = "data/index";
    
    public static void main(String[] args) {
        try {
            Searcher searcher = new Searcher(INDEX_DIR);
            
            // Test with 5 different keywords
            String[] keywords = {"search", "lucene", "information", "document", "indexing"};
            
            System.out.println("========================================");
            System.out.println("SEARCH ENGINE TEST - 5 EXAMPLE KEYWORDS");
            System.out.println("========================================\n");
            
            for (String keyword : keywords) {
                System.out.println("--------------------------------------------------");
                System.out.println("Searching for: \"" + keyword + "\"");
                System.out.println("--------------------------------------------------");
                
                long startTime = System.currentTimeMillis();
                TopDocs hits = searcher.search(keyword, 10);
                long endTime = System.currentTimeMillis();
                
                System.out.println("Found " + hits.totalHits + " documents");
                System.out.println("Search time: " + (endTime - startTime) + " ms\n");
                
                System.out.println("Top results:");
                for (int i = 0; i < Math.min(5, hits.scoreDocs.length); i++) {
                    ScoreDoc scoreDoc = hits.scoreDocs[i];
                    Document doc = searcher.getDocument(scoreDoc);
                    System.out.println("  " + (i+1) + ". " + doc.get("filename") + 
                                     " (Score: " + scoreDoc.score + ")");
                }
                System.out.println();
            }
            
            searcher.close();
            
        } catch (Exception e) {
            System.err.println("Error during search: " + e.getMessage());
            e.printStackTrace();
        }
    }
}