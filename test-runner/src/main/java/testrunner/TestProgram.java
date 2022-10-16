package testrunner;

import common.Program;
import common.TestResult;
import common.container.Element;
import common.container.JsonTest;
import lombok.Data;

import java.util.List;

import static common.TestResult.*;

@Data
public class TestProgram implements Program<Element> {
  private List<Element> failureElements;
  private List<Element> dependencyElements;
  private List<Element> elems;
  private boolean dd = true;


  public TestProgram(JsonTest test) {
    elems = test.getElems();
    failureElements = test.getFailElems(); //new ArrayList(elements.stream().filter(x -> test.getFailIds().contains(x.getId())).collect(Collectors.toList()));
    dependencyElements = test.getDependElems(); //new ArrayList(elements.stream().filter(x -> test.getDependIds().contains(x.getId())).collect(Collectors.toList()));
  }

  public TestProgram(List<Element> input, List<Element> failElems, List<Element> dependElems) {
    elems = input;
    failureElements = failElems; //new ArrayList(elements.stream().filter(x -> test.getFailIds().contains(x.getId())).collect(Collectors.toList()));
    dependencyElements = dependElems; //new ArrayList(elements.stream().filter(x -> test.getDependIds().contains(x.getId())).collect(Collectors.toList()));
  }

  @Override
  public TestResult test(List<Element> input) {
    var depCheck = input.stream().filter(dependencyElements::contains).toList();
    if (dd && !depCheck.isEmpty() && depCheck.size() != dependencyElements.size()) {
      return UNRES;
    }

    boolean result = input.containsAll(failureElements);

    return result ? FAIL : PASS;
  }
}
