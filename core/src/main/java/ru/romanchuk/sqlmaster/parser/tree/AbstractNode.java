package ru.romanchuk.sqlmaster.parser.tree;

import ru.romanchuk.sqlmaster.parser.Node;
import ru.romanchuk.sqlmaster.parser.NodeWithChildes;

/**
 * @author Alexey Romanchuk
 */
public class AbstractNode implements Node {
    private NodeWithChildes parent;

    public NodeWithChildes getParent() {
        return parent;
    }

    public void setParent(NodeWithChildes parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractNode that = (AbstractNode) o;

        return !(parent != null ? !parent.equals(that.parent) : that.parent != null);

    }

    @Override
    public int hashCode() {
        return parent != null ? parent.hashCode() : 0;
    }
}
