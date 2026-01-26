#!/bin/bash

# Generate documents 201-500 (300 more documents)
for i in {201..500}
do
    cat > "perf_test_$i.txt" << EOF
Title: Performance Test Document $i
Author: TestBot $((i % 20))
Date: 2026-01-$((i % 28 + 1))
Category: Performance Testing Batch

This is performance test document number $i for measuring indexing speed.
Content includes various topics: optimization, benchmarking, scalability testing.

Keywords: performance, testing, benchmark, optimization, scalability, 
throughput, latency, efficiency, measurement, metrics

Document $i is used to evaluate the search engine's indexing performance
as the corpus size grows from 100 to 500 documents.
EOF
done

echo "Generated 300 performance test documents (perf_test_201.txt to perf_test_500.txt)!"