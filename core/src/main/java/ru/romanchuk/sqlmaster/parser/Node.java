package ru.romanchuk.sqlmaster.parser;

/**
 * @author Alexey Romanchuk
 */
public interface Node {
    NodeWithChildes getParent();
    void setParent(NodeWithChildes node);
}
