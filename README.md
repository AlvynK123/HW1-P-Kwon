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
- **Results**: All keywords found in 101 documents (100 .txt + 1 .sh file)
- **Performance**: Indexed 101 documents in 36 ms

### Part 2: Add Single Document
- Web Document Used: https://catalog.gatech.edu/programs/intelligence-information-internetworks-computer-science-bs/
- **Results**: Successfully indexed in 31 ms
- **Verification**: Searched for unique keywords (Georgia, Thread, Intelligence, Internetworks, curriculum)
- Each keyword found in exactly 1 document, proving successful indexing

### Part 3: Add 100 More Documents
- Created 100 new research documents (document_101.txt to document_200.txt)
- **Results**: All 100 documents indexed successfully
- **Performance**: 
  - Total time: 37 ms
  - Average: 0.37 ms per document
- **Verification**: 
  - New keywords (research, data mining, cloud computing) found in 100-101 documents
  - Original documents still searchable (confirmed with 'topic' keyword)
- **Total Documents**: 202 (101 original + 1 HTML + 100 new)

### Part 4: Performance Measurement 
- Measured indexing performance for K = 100, 200, 300, 400, 500 documents
- **Results saved to**: `performance_results.csv`
- **Key Findings**:
  - K=100: 141 ms total, 1.41 ms/doc (includes JVM warmup)
  - K=200: 42 ms total, 0.21 ms/doc
  - K=300: 41 ms total, 0.14 ms/doc
  - K=400: 42 ms total, 0.11 ms/doc
  - K=500: 46 ms total, 0.09 ms/doc
- **Observation**: Average time per document decreases as corpus size increases (better amortization of indexing overhead)

### Part 5: Discussion and Analysis (To Do)
- TODO

## Performance Summary
| Operation | Documents | Time (ms) | Avg per Doc (ms) |
|-----------|-----------|-----------|------------------|
| Initial Index | 101 | 36 | 0.36 |
| Add Single Doc | 1 | 31 | 31.00 |
| Add 100 Docs | 100 | 37 | 0.37 |

### Performance Measurement (Part 4)
| K Documents | Actual Docs | Total Time (ms) | Avg Time/Doc (ms) |
|-------------|-------------|-----------------|-------------------|
| 100 | 100 | 141 | 1.41 |
| 200 | 200 | 42 | 0.21 |
| 300 | 300 | 41 | 0.14 |
| 400 | 400 | 42 | 0.11 |
| 500 | 500 | 46 | 0.09 |

## Technologies Used
- Apache Lucene 9.9.1
- Java 21
- Maven 3.9.12