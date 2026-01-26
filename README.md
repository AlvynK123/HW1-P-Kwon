# HW1-P-Kwon: Lucene Toy Search Engine

## Student Information
- **Name:** Alvyn Kwon
- **Assignment:** HW1 - Programming Assignment
- **Course:** CS 4675

## Project Description
A toy search engine built with Apache Lucene 9.9.1 that indexes and searches text documents.

## Project Structure
```
HW1-P-Kwon/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── searchengine/
│                   ├── Indexer.java        # Indexes documents
│                   ├── Searcher.java       # Searches the index
│                   ├── LuceneApp.java      # Main indexing app
│                   └── SearchTest.java     # Search testing app
├── data/
│   ├── documents/          # Document collection (100+ docs)
│   └── index/              # Lucene index (generated)
├── pom.xml                 # Maven configuration
└── README.md
```

## How to Build
```bash
mvn clean compile
```

## How to Run

### Index Documents
```bash
mvn exec:java -Dexec.mainClass="com.searchengine.LuceneApp"
```

### Search Documents
```bash
mvn exec:java -Dexec.mainClass="com.searchengine.SearchTest"
```

## Assignment Progress

### Part 1: Initial Setup 
- Created toy search engine with 100 documents
- Indexed all documents successfully
- Tested search with 5 example keywords: search, lucene, information, document, indexing
- All keywords found in 101 documents (100 .txt + 1 .sh file)

### Part 2: Add Single Document (In Progress)
- Web Document Used: https://catalog.gatech.edu/programs/intelligence-information-internetworks-computer-science-bs/
- Successful Indexing
- Searched for unique keywords to prove it was indexed

### Part 3: Add 100 More Documents (To Do)
- TODO

### Part 4: Performance Measurement (To Do)
- TODO

### Part 5: Discussion and Analysis (To Do)
- TODO

## Technologies Used
- Apache Lucene 9.9.1
- Java 21
- Maven 3.9.12
