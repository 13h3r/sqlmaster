package ru.romanchuk.sqlmaster.parser.tree;

import org.apache.commons.lang.StringUtils;

/**
 * @author Alexey Romanchuk
 */
public class ParameterNode extends AbstractNodeWithChildes {
    private String name;
    private ParameterType type;

    public ParameterNode(String name, ParameterType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ParameterType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ParameterNode that = (ParameterNode) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParameterNode(" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", childrens='" + StringUtils.join(getChildes(), ",") + "'" +
                ')';
    }
}
