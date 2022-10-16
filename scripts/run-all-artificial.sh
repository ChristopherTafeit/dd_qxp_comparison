#!/bin/bash

gradle clean shadowJar -p ../

for i in ../generatedTestcases/*.tbz2; do  tar xjf $i -C ../generatedTestcases/; done

for j in $(find ../generatedTestcases/ ! -path ../generatedTestcases/ -maxdepth 1 -type d); do
    for i in $(find $j ! -path $j -maxdepth 1 -type d); do
        echo "=========RUN FOLDER $i========="
        java -jar ../test-runner/build/libs/test-runner-all.jar $i;
    done
done