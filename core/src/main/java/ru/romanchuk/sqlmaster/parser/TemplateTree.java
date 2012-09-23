package ru.romanchuk.sqlmaster.parser;

import ru.romanchuk.sqlmaster.parser.tree.ParameterNode;
import ru.romanchuk.sqlmaster.parser.tree.RootNode;

import java.util.List;

/**
 * @author Alexey Romanchuk
 */
public interface TemplateTree {
    RootNode getRootNode();

    ParameterNode getParameterNode(String name);

    List<ParameterNode> getParameters();

}
