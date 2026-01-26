#!/bin/bash

# Generate 100 MORE documents
for i in {101..200}
do
    cat > "document_$i.txt" << EOF
Title: Research Document $i
Author: Researcher $((i % 15))
Date: 2026-01-$((i % 28 + 1))
Category: Batch 2 - Research Papers

This is research document number $i from the second batch of documents.
It focuses on topics like data mining, cloud computing, and distributed systems.

Keywords: research, data mining, cloud computing, distributed systems, 
big data, analytics, scalability, performance optimization

Document $i explores advanced computing concepts and emerging technologies
in the field of computer science and information technology.
EOF
done

echo "Generated 100 new documents (document_101.txt to document_200.txt)!"