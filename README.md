# Grep Reporting Tool

# Development Stage: Building - Pre Alpha release

## what's left to do
* core logic
* angular web interface
* lots of testing
* package in easy to run docker image

## Purpose
* most reporting is in three categories
    * get counts and sums of values based on groupings
    * groupings are typically date based with one or two other "columns"
    * extract subsets of data based on matching criteria
* why do it this way
    * zip files are a really easy way to store compressed data that can be searched extremely fast
    * management of removing data is as simple as deleting files
    * grep and awk are really really fast, read up on Boyer-Moore algorithm if you are interested
* Alternatives are complicated
    * Ever tried deleting huge amounts of data from an RDBMS
    * cool you have the data in elasticsearch (insert other NOSQL name) now its another tool to use

## Use Cases
* a friendly to use reporting tool that uses .txt or .gz files as input
* general reporting on data at a decent speed. 
* provide filtering on data
* aggregation


## Licence
Licensed under the MIT Licence, repeated below:

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
