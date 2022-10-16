#!/bin/bash

gradle clean shadowJar -p ../

for i in ../generatedTestcases/$1.tbz2; do  tar xjf $i -C ../generatedTestcases/; done

for i in ../generatedTestcases/$1/*; do
echo "=========RUN FOLDER $i========="
java -jar ../test-runner/build/libs/test-runner-all.jar ../generatedTestcases/$i;
done