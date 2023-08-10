# File Merge Sort

## Task
Write a sorting program by merging multiple files.
Input files contain data of one of two types: integers or strings. The data is written
in a column (each line of the file is a new element). Strings can contain any non-whitespace
characters, strings with spaces are considered erroneous. It is also assumed that the files are pre
-sorted.

## Requirement
- [OpenJDK](https://openjdk.java.net)  11.0.20
- [Maven](https://maven.apache.org/) for development 3.6.3

## How start 
1. Build
```shell
mvn package
```
2. Run project 
```shell
java -jar target/file_merge_sort-1.0-SNAPSHOT.jar -a -s out.txt i1.txt i2.txt i3.txt
```
