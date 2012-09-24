package ru.romanchuk.sqlmaster.engine.impl;

import ru.romanchuk.sqlmaster.engine.EngineException;
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
        if(tree.getParameterNode(name).isEmpty()) {
            throw new EngineException("Unable to find parameter " + name);
        }
        state.assignValue(name, value);
    }

    public void embed(String name) {
        if(tree.getEmbeddedNode(name).isEmpty()) {
            throw new EngineException("Unable to find embedded text " + name);
        }
        state.embed(name);
    }

    public TemplateTree getTree() {
        return tree;
    }

    public TemplateState getState() {
        return state;
    }
}
