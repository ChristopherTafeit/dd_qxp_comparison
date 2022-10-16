package common.algorithm;

import common.Program;
import common.TestResult;
import common.container.Element;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static common.TestResult.FAIL;
import static java.lang.Math.max;
import static java.lang.Math.min;

@Slf4j
public class DDAlgorithm {

    private final Program<Element> testProgram;
    private final List<Element> fullInput;
    private List<Element> solution;

    public DDAlgorithm(Program<Element> testProgram) {
        this.testProgram = testProgram;
        this.fullInput = new LinkedList<>();
    }

    public List<Element> startDDAlgorithm(List<Element> input) {
        this.fullInput.addAll(input);
        ddmin(input);
        return solution;
    }

    private void ddmin(List<Element> input) {
        /*TestResult result = testProgram.test(input);*/
        TestResult result = testProgram.test(input);

        if (result == FAIL) {
            ddmin2(input, 2);
        }
    }

    public List<Element> getComplement(List<Element> input, List<Element> actualInput) {
        return actualInput.stream().filter(x -> !input.contains(x)).collect(Collectors.toList());
    }

    private void ddmin2(List<Element> list, int n) {
        if (list.size() == 1)
            return;

        List<List<Element>> split = split(list, n);

        for (List<Element> l : split) {
            //TestResult resultDelta = testProgram.test(l);
            TestResult resultDelta = testProgram.test(l);

            if (resultDelta == FAIL) {
                ddmin2(l, 2);
                return;
            }
        }

        List<List<Element>> complements = getComplementList(split, list);

        for (List<Element> l : complements) {
            var resultComplement = testProgram.test(l);

            if (resultComplement == FAIL) {
                ddmin2(l, max(n - 1, 2));
                return;
            }
        }

        if (n < list.size()) { //debug list.size() > 1
            ddmin2(list, min(list.size(), 2 * n));
            return;
        }

        solution = list;
        list.forEach(element -> log.debug(element.toString()));
    }

    private List<List<Element>> getComplementList(List<List<Element>> split, List<Element> actualInput) {
        var complementList = new ArrayList<List<Element>>();
        for (var l : split) {
            complementList.add(getComplement(l, actualInput));
        }
        return complementList;
    }

    private List<List<Element>> split(List<Element> input, int numSets) {
        int blockSize = input.size() / numSets;
        blockSize += input.size() % numSets > 0 ? 1 : 0;
        return ListUtils.partition(input, blockSize);
    }
}

