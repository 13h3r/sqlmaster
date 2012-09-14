package ru.romanchuk.sqlmaster.parser.impl;

import ru.romanchuk.sqlmaster.parser.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Romanchuk
 */
public class MarkupNode implements Node {
    private List<Node> childNodes = new ArrayList<Node>();
    private String markup;

    public MarkupNode(String markup) {
        this.markup = markup;
    }

    public List<Node> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<Node> childNodes) {
        this.childNodes = childNodes;
    }

    public String getMarkup() {
        return markup;
    }

    public void setMarkup(String markup) {
        this.markup = markup;
    }
}
