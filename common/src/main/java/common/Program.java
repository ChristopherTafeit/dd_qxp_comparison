package common;

import java.util.List;

public interface Program<E> {

    TestResult test(List<E> input);
}
