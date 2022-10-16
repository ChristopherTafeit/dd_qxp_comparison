package common;

public enum TestResult {
    PASS(0), FAIL(-1), UNRES(1);

  TestResult(int i) {
    this.value = i;
  }

  public final int value;
}