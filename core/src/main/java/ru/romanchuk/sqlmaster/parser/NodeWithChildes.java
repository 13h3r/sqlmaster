package ru.romanchuk.sqlmaster.parser;

import java.util.List;

/**
 * @author Alexey Romanchuk
 */
public interface NodeWithChildes extends Node {
    void add(Node node);
    List<Node> getChildes();
}
