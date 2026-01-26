package com.searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
    private IndexWriter writer;

    public Indexer(String indexDirectoryPath) throws IOException {
        Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        
        writer = new IndexWriter(indexDirectory, config);
    }

    public void close() throws IOException {
        writer.close();
    }

    private Document getDocument(File file) throws IOException {
        Document document = new Document();

        Field pathField = new StringField("path", file.getPath(), Field.Store.YES);
        
        Field nameField = new TextField("filename", file.getName(), Field.Store.YES);
        
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        
        Field contentField = new TextField("contents", content.toString(), Field.Store.YES);
        
        document.add(pathField);
        document.add(nameField);
        document.add(contentField);
        
        return document;
    }

    public int indexFile(File file) throws IOException {
        Document document = getDocument(file);
        writer.addDocument(document);
        return 1;
    }

    public int indexDirectory(String dataDirPath) throws IOException {
        File[] files = new File(dataDirPath).listFiles();
        int indexed = 0;
        
        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead()) {
                    indexFile(file);
                    indexed++;
                    System.out.println("Indexed: " + file.getName());
                }
            }
        }
        
        return indexed;
    }
}