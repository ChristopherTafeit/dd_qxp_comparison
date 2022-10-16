#!/bin/bash

gradle clean shadowJar -p ../

java -jar ../external-test-runner/build/libs/external-test-runner-all.jar ../realworldTestcases/$1/*.jar ../realworldTestcases/$1/*.json
