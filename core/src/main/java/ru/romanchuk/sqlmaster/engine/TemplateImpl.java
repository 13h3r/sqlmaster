package ru.romanchuk.sqlmaster.engine;

import ru.romanchuk.sqlmaster.SimpleEngine;
import ru.romanchuk.sqlmaster.Template;
import ru.romanchuk.sqlmaster.parser.TemplateTree;

/**
 * @author Alexey Romanchuk
 */
public class TemplateImpl implements Template {
    private TemplateTree tree;
    private TemplateState state;
    private SimpleEngine engine;

    public TemplateImpl(TemplateTree templateTree, SimpleEngine engine) {
        tree = templateTree;
        state = new TemplateState();
        this.engine = engine;
    }

    @Override
    public void assignValue(String name, Object value) {
        if (tree.getParameterNode(name).isEmpty()) {
            throw new EngineException("Unable to find parameter " + name);
        }
        state.assignValue(name, value);
    }

    @Override
    public void enable(String name) {
        enable(name, true);
    }

    @Override
    public void enable(String name, boolean enable) {
        if (tree.getEmbeddedNode(name).isEmpty()) {
            throw new EngineException("Unable to find embedded text " + name);
        }
        state.enable(name, enable);
    }

    @Override
    public String process() {
        return engine.processTemplate(this);
    }

    public TemplateTree getTree() {
        return tree;
    }

    public TemplateState getState() {
        return state;
    }
}
