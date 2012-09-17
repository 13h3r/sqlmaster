package ru.romanchuk.sqlmaster.parser.impl;

/**
 * @author Alexey Romanchuk
 */
public class MarkupNode extends AbstractNode {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarkupNode that = (MarkupNode) o;

        if (!markup.equals(that.markup)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return markup.hashCode();
    }
}
