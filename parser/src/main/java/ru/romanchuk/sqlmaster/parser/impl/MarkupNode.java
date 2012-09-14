package ru.romanchuk.sqlmaster.parser.impl;

import ru.romanchuk.sqlmaster.parser.Node;

/**
 * @author Alexey Romanchuk
 */
public class MarkupNode implements Node {
    private String markup;

    public MarkupNode(String markup) {
        this.markup = markup;
    }

    public String getMarkup() {
        return markup;
    }

    public void setMarkup(String markup) {
        this.markup = markup;
    }
}
