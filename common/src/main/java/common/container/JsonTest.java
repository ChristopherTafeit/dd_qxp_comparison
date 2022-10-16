package common.container;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class JsonTest {

    private List<Integer> dependIds;
    private List<Integer> failIds;
    private List<Element> elems;
    private List<Element> dependElems;
    private List<Element> failElems;

    public JsonTest(List<Integer> dependIds, List<Integer> failIds, List<Element> elems) {
        this.dependIds = dependIds;
        this.failIds = failIds;
        this.elems = elems;

        dependElems = new ArrayList<>(this.elems.stream().filter(x -> this.dependIds.contains(x.getId())).toList());
        failElems = new ArrayList<>(this.elems.stream().filter(x -> this.failIds.contains(x.getId())).toList());

    }

    public JsonTest() {
        dependIds = new ArrayList<>();
        failIds = new ArrayList<>();
        elems = new ArrayList<>();
        dependElems = new ArrayList<>();
        failElems = new ArrayList<>();
    }

    public void generateElements() {
        dependElems = new ArrayList<>(this.elems.stream().filter(x -> this.dependIds.contains(x.getId())).toList());
        failElems = new ArrayList<>(this.elems.stream().filter(x -> this.failIds.contains(x.getId())).toList());
    }
}
