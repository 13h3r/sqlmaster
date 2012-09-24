package ru.romanchuk.sqlmaster.parser.tree;

import org.apache.commons.lang.StringUtils;

/**
 * @author Alexey Romanchuk
 */
public class EmbeddedNode extends AbstractNodeWithChildes{
    private final String name;

    public EmbeddedNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EmbeddedNode that = (EmbeddedNode) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EmbeddedNode{" +
                "name='" + name + '\'' +
                ", childrens='" + StringUtils.join(getChildes(), ",") + "\'" +
                '}';
    }
}
