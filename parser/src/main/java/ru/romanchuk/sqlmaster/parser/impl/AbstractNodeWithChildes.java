package ru.romanchuk.sqlmaster.parser.impl;

import ru.romanchuk.sqlmaster.parser.Node;
import ru.romanchuk.sqlmaster.parser.NodeWithChildes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Romanchuk
 */
public class AbstractNodeWithChildes extends AbstractNode implements NodeWithChildes {
    private List<Node> childes = new ArrayList<Node>();

    public List<Node> getChildes() {
        return childes;
    }

    public void add(Node node) {
        node.setParent(this);
        getChildes().add(node);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractNodeWithChildes that = (AbstractNodeWithChildes) o;

        if (childes != null ? !childes.equals(that.childes) : that.childes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return childes != null ? childes.hashCode() : 0;
    }
}
