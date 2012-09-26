package ru.romanchuk.sqlmaster.engine;

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
        if (tree.getParameterNode(name).isEmpty()) {
            throw new EngineException("Unable to find parameter " + name);
        }
        state.assignValue(name, value);
    }

    public void enable(String name) {
        enable(name, true);
    }

    public void enable(String name, boolean enable) {
        if (tree.getEmbeddedNode(name).isEmpty()) {
            throw new EngineException("Unable to find embedded text " + name);
        }
        state.enable(name, enable);
    }

    public TemplateTree getTree() {
        return tree;
    }

    public TemplateState getState() {
        return state;
    }
}
