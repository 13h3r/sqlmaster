package ru.romanchuk.sqlmaster.parser;

import ru.romanchuk.sqlmaster.parser.impl.ParameterNode;
import ru.romanchuk.sqlmaster.parser.impl.RootNode;

import java.util.List;

/**
 * @author Alexey Romanchuk
 */
public interface TemplateTree {
    RootNode getRootNode();

    ParameterNode getParameterNode(String name);

    List<ParameterNode> getParameters();

}
