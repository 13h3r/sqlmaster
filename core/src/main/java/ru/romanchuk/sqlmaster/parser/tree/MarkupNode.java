package ru.romanchuk.sqlmaster.parser.tree;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarkupNode that = (MarkupNode) o;

        return markup.equals(that.markup);

    }

    @Override
    public int hashCode() {
        return markup.hashCode();
    }

    @Override
    public String toString() {
        return "MarkupNode{" +
                "markup='" + markup + '\'' +
                '}';
    }
}
