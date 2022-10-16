# Delta Debugging vs QuickXplain (dd-qxp-comparison)

## Prerequisits
* Java 17 or newer
* Gradle 7
* bash (for scripts)

## Test case generator

`java -jar testcase-generator.jar <amountOfTestcases> <generatorOutputPath> <amountOfFailIds>`

## Real world test program

`java -jar external-test-runner.jar <testProgram> <testInputFile>`

## Artificial test program

`java -jar test-runner.jar <testCaseDirectory>`

## Test execution
* scripts/
    * run_all_artificial.sh (extracts and runs all artificial tests)
    * run-artificial-test.sh \<input\>
    * run_external_tests.sh \<golem|google|orf\>