package ru.romanchuk.sqlmaster.parser;

import ru.romanchuk.sqlmaster.parser.impl.ParameterNode;
import ru.romanchuk.sqlmaster.parser.impl.RootNode;

/**
 * @author Alexey Romanchuk
 */
public interface TemplateTree {
    RootNode getRootNode();

    ParameterNode getParameterNode(String name);

}
