package ru.romanchuk.sqlmaster.parser.impl;

/**
 * @author Alexey Romanchuk
 */
public class PlainTextNode extends AbstractNode {
    private String text;

    public PlainTextNode(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlainTextNode that = (PlainTextNode) o;

        return text.equals(that.text);

    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return "PlainTextNode(" + text + ')';
    }
}