#!/bin/bash

# Generate 100 simple documents
for i in {1..100}
do
    cat > "doc$i.txt" << EOF
Title: Document $i
Author: Author $((i % 10))
Date: 2024-01-$((i % 28 + 1))

This is document number $i. It contains information about topic $((i % 5)).
Some keywords: search engine lucene information retrieval indexing
Document $i discusses various aspects of text processing and search.
EOF
done

echo "Generated 100 documents!"