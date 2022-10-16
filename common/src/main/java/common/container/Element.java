package common.container;

import lombok.Data;

@Data
public class Element {
    private final int id;
    private final String value;

    public Element(int id, String value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        return id == element.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
