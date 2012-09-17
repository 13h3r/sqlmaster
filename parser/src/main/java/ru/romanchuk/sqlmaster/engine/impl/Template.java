package ru.romanchuk.sqlmaster.engine.impl;

import ru.romanchuk.sqlmaster.parser.TemplateTree;

/**
 * @author Alexey Romanchuk
 */
public class Template {
    private TemplateTree tree;
    private TemplateState state;

    public Template(TemplateTree templateTree) {
        tree = templateTree;
        state = new TemplateState();
    }

    public void assignValue(String name, Object value) {
        state.assignValue(name, value);
    }
}
