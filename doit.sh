#!/bin/sh

echo "compiling"
javac *.java

### to work with a fixed character set

echo "testing translation"
java LookupFilter ahab.txt ahab._out
java LookupFilter -r ahab._out ahab.ref

java LookupFilter ahab.ref ahab._out

java CutFilter ahab._out ahab._segments
java CutFilter -r ahab._segments ahab._out

java LookupFilter -r ahab._out data

diff ahab.ref data || echo "bad cut"

echo "testing FASTQ"
java FastqShuffleFilter -seq ahab._segments ahab._segments.shuffled
java FastqShuffleFilter -seq -r ahab._segments.shuffled data
java CutFilter -r data ahab._out
java LookupFilter -r ahab._out data

diff ahab.ref data || echo "bad fastq"

echo "generating FASTQ test sequences"

java CutFilter -n 100 ahab._out ahab._segments
java FastqShuffleFilter ahab._segments ahab.segments.1000.shuffled

rm -f ahab._* *.class data
