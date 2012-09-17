package ru.romanchuk.sqlmaster.parser;

/**
 * @author Alexey Romanchuk
 */
public interface NodeWithChildes extends Node {
    void add(Node node);
}
