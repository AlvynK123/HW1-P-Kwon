package com.searchengine;

import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class ShowIndexEntries {

    private static final String INDEX_DIR = "data/index";
    private static final String FIELD = "contents";

    // pick 3 terms for Part (e)
    private static final String[] DEMO_TERMS = { "lucene", "search", "indexing" };

    public static void main(String[] args) {
        try {
            System.out.println("========================================");
            System.out.println("EXAMPLE INDEX ENTRIES");
            System.out.println("========================================\n");

            Directory indexDirectory = FSDirectory.open(Paths.get(INDEX_DIR));
            IndexReader reader = DirectoryReader.open(indexDirectory);

            System.out.println("Total documents in index: " + reader.numDocs());
            System.out.println("\nShowing sample index terms from '" + FIELD + "' field:\n");

            // Your existing: show sample terms (docFreq)
            Terms allTerms = org.apache.lucene.index.MultiTerms.getTerms(reader, FIELD);
            if (allTerms != null) {
                TermsEnum termsEnum = allTerms.iterator();
                BytesRef term;
                int count = 0;

                System.out.println("Sample Index Terms (first 20 meaningful words):");
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
            } else {
                System.out.println("No terms found for field '" + FIELD + "'.");
            }

            // NEW PART: show postings (example docs) for 3 chosen terms
            System.out.println("\n========================================");
            System.out.println("POSTINGS LIST EXAMPLES (TERM -> DOCS)");
            System.out.println("========================================\n");

            for (String demoTerm : DEMO_TERMS) {
                printPostingsForTerm(reader, demoTerm, 8); // show up to 8 example docs
                System.out.println();
            }

            reader.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printPostingsForTerm(IndexReader reader, String termText, int maxDocs) throws Exception {
        Term term = new Term(FIELD, termText);

        int docFreq = reader.docFreq(term);
        long totalTermFreq = reader.totalTermFreq(term); // may be -1 sometimes

        System.out.println("TERM: \"" + termText + "\"");
        System.out.println("docFreq = " + docFreq + ", totalTermFreq = " + totalTermFreq);

        PostingsEnum postings = org.apache.lucene.index.MultiTerms.getTermPostingsEnum(reader, FIELD, new BytesRef(termText));
        if (postings == null) {
            System.out.println("  (no postings found — term not in index)");
            return;
        }

        System.out.println("Example docs containing \"" + termText + "\" (showing up to " + maxDocs + "):");
        System.out.println("  luceneDocId | tf  | identifier");
        System.out.println("  -----------|-----|------------------------------");

        int shown = 0;
        while (postings.nextDoc() != DocIdSetIterator.NO_MORE_DOCS && shown < maxDocs) {
            int luceneDocId = postings.docID();
            int tf = postings.freq();

            Document d = reader.document(luceneDocId);
            String id = bestDocIdentifier(d, luceneDocId);

            System.out.printf("  %-10d | %-3d | %s%n", luceneDocId, tf, id);
            shown++;
        }

        if (docFreq > maxDocs) {
            System.out.println("  ... (" + (docFreq - maxDocs) + " more docs contain this term)");
        }
    }

    private static String bestDocIdentifier(Document d, int luceneDocId) {
        // change/add fields here to match what YOU stored in Indexer.java
        String[] candidates = new String[] { "path", "filename", "title" };
        for (String f : candidates) {
            String v = d.get(f);
            if (v != null && !v.isBlank()) return f + "=" + v;
        }
        return "luceneDocId=" + luceneDocId;
    }
}
