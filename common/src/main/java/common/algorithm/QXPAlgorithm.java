package common.algorithm;

import common.Program;
import common.TestResult;
import common.container.Element;
import org.apache.commons.collections4.ListUtils;

import java.util.List;

import static common.TestResult.PASS;

public class QXPAlgorithm {
    Program<Element> oracle;

    public QXPAlgorithm(Program<Element> oracle) {
        this.oracle = oracle;
    }

    public List<Element> qxStarter(List<Element> aSet, List<Element> bSet) {
        //check if there is a possible set within the initial starting values
        if (oracle.test(mergeLists(aSet, bSet)) == PASS) {
            return null; // null is no conflict
        } else if (aSet.isEmpty()) {
            return List.of();
        } else {
            return qxRecursive(bSet, aSet, bSet);
        }
    }

    public List<Element> qxRecursive(List<Element> cSet, List<Element> aSet, List<Element> bSet) {
        if (!cSet.isEmpty() && oracle.test(bSet) != PASS) {
            return List.of();
        }

        if (aSet.size() == 1) {
            return aSet;
        }

        List<List<Element>> splitted = bisect(aSet);

        List<Element> aSet1 = splitted.get(0);
        List<Element> aSet2 = splitted.get(1);

        List<Element> xSet2 = qxRecursive(aSet1, aSet2, mergeLists(bSet, aSet1));
        List<Element> xSet1 = qxRecursive(xSet2, aSet1, mergeLists(bSet, xSet2));

        return mergeLists(xSet1, xSet2);
    }

    /*
    //Merge two lists
     */
    public List<Element> mergeLists(List<Element> aList, List<Element> bList) {
        return ListUtils.union(aList, bList);
    }

    /*
    Split a list in half
     */
    public List<List<Element>> bisect(List<Element> input) {

        int elem_counter = (int) Math.ceil((double) input.size() / 2);

        return ListUtils.partition(input, elem_counter);
    }
}
