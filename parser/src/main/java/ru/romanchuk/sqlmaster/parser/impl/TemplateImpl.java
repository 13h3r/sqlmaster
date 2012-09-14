package ru.romanchuk.sqlmaster.parser.impl;

import ru.romanchuk.sqlmaster.parser.Node;
import ru.romanchuk.sqlmaster.parser.Template;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Romanchuk
 */
public class TemplateImpl implements Template {

    private List<Node> nodes = new ArrayList<Node>();

    @Override
    public List<Node> getNodes() {
        return nodes;
    }
}
