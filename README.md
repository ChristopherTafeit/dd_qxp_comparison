# Delta Debugging vs QuickXplain (dd-qxp-comparison)

## Prerequisites
* Java 17 or newer
* Gradle 7
* bash (for scripts)

## Test case generator
How to run:

`java -jar testcase-generator.jar <amountOfTestcases> <generatorOutputPath> <amountOfFailIds>`

`<amountOfTestcases>`: Specify the amount of test cases that needs to be generated
`<generatorOutputPath>`: Specify the path to store the generated test cases
`<amountOfFailIds>`: Specify the amount of Fail IDs that are randomly generated and therefore randomly distributed

## Real world test program
How tu run:

`java -jar external-test-runner.jar <testProgram> <testInputFile>`

`<testProgram>`: Specify the external test program (external test oracle)
`<testInputFile>`: Specify the test input file (.json)

## Artificial test program
How to run:

`java -jar test-runner.jar <testCaseDirectory>`

`<testCaseDirectory>`: Specify the folder with the test input files (.json)

## Test execution
* scripts/
    * run_all_artificial.sh (extracts and runs all artificial tests)
    * run-artificial-test.sh \<input\>
    * run_external_tests.sh \<golem|google|orf\>