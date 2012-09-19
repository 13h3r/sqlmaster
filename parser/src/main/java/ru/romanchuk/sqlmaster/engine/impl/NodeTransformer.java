package ru.romanchuk.sqlmaster.engine.impl;

import ru.romanchuk.sqlmaster.parser.Node;

/**
 * @author Alexey Romanchuk
 */
public interface NodeTransformer<T extends Node> {
    String transform(T node, TemplateState state);
}
