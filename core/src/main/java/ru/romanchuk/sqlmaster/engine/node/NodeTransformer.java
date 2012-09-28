package ru.romanchuk.sqlmaster.engine.node;

import ru.romanchuk.sqlmaster.engine.TemplateState;
import ru.romanchuk.sqlmaster.parser.Node;

/**
 * @author Alexey Romanchuk
 */
public interface NodeTransformer<T extends Node> {
    String transform(T node, TemplateState state);
}
