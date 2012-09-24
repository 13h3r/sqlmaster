package ru.romanchuk.sqlmaster.engine.impl;

import ru.romanchuk.sqlmaster.engine.EngineException;
import ru.romanchuk.sqlmaster.parser.tree.ParameterNode;

/**
 * @author Alexey Romanchuk
 */
public class ParameterNodeTransformer implements NodeTransformer<ParameterNode> {
    @Override
    public String transform(ParameterNode node, TemplateState state) {
        Object value = state.getAssignedValue(node.getName());
        if(value == null) {
            throw new EngineException("Parameter " + node.getName() + " is not set");
        }
        return value.toString();
    }
}
