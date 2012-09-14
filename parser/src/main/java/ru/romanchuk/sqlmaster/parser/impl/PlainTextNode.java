package ru.romanchuk.sqlmaster.parser.impl;

import ru.romanchuk.sqlmaster.parser.Node;

/**
 * @author Alexey Romanchuk
 */
public class PlainTextNode implements Node {
    private String text;

    public PlainTextNode(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
