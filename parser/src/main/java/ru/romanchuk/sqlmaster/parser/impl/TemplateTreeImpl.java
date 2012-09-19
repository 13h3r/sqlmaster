package ru.romanchuk.sqlmaster.parser.impl;

import ru.romanchuk.sqlmaster.parser.TemplateTree;

/**
 * @author Alexey Romanchuk
 */
public class TemplateTreeImpl implements TemplateTree {
    private RootNode tree;

    public TemplateTreeImpl(RootNode tree) {
        this.tree = tree;
    }

    @Override
    public RootNode getRootNode() {
        return tree;
    }
}
