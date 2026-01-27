package com.searchengine;

import java.nio.file.Paths;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class ShowIndexEntries {
    
    private static final String INDEX_DIR = "data/index";
    
    public static void main(String[] args) {
        try {
            System.out.println("========================================");
            System.out.println("EXAMPLE INDEX ENTRIES");
            System.out.println("========================================\n");
            
            Directory indexDirectory = FSDirectory.open(Paths.get(INDEX_DIR));
            IndexReader reader = DirectoryReader.open(indexDirectory);
            
            System.out.println("Total documents in index: " + reader.numDocs());
            System.out.println("\nShowing index terms from 'contents' field:\n");
            
            // Get terms from first document
            Terms terms = reader.getTermVector(0, "contents");
            
            if (terms != null) {
                TermsEnum termsEnum = terms.iterator();
                BytesRef term;
                int count = 0;
                
                System.out.println("Sample Index Terms (first 20):");
                System.out.println("Term                    | Frequency");
                System.out.println("------------------------|-----------");
                
                while ((term = termsEnum.next()) != null && count < 20) {
                    String termText = term.utf8ToString();
                    long freq = termsEnum.totalTermFreq();
                    System.out.printf("%-23s | %d%n", termText, freq);
                    count++;
                }
            } else {
                System.out.println("No term vectors stored. Showing terms from entire index:");
                
                Terms allTerms = org.apache.lucene.index.MultiTerms.getTerms(reader, "contents");
                if (allTerms != null) {
                    TermsEnum termsEnum = allTerms.iterator();
                    BytesRef term;
                    int count = 0;
                    
                    System.out.println("\nSample Index Terms (first 20 words):");
                    System.out.println("Term                    | Doc Frequency");
                    System.out.println("------------------------|--------------");
                    
                    while ((term = termsEnum.next()) != null && count < 20) {
                        String termText = term.utf8ToString();
                        
                        // Skip numbers and very short terms - show only meaningful words
                        if (termText.matches(".*[a-zA-Z]{3,}.*")) {
                            int docFreq = termsEnum.docFreq();
                            System.out.printf("%-23s | %d%n", termText, docFreq);
                            count++;
                        }
                    }
                }
            }
            
            reader.close();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}