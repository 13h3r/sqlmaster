package ru.romanchuk.sqlmaster.engine.impl.nodetransformer;

import ru.romanchuk.sqlmaster.engine.EngineException;
import ru.romanchuk.sqlmaster.engine.impl.TemplateState;
import ru.romanchuk.sqlmaster.engine.impl.paramtransformer.ParameterTransformer;
import ru.romanchuk.sqlmaster.engine.impl.paramtransformer.ParameterTransformerRegistry;
import ru.romanchuk.sqlmaster.parser.tree.ParameterNode;

/**
 * @author Alexey Romanchuk
 */
public class ParameterNodeTransformer implements NodeTransformer<ParameterNode> {
    @Override
    public String transform(ParameterNode node, TemplateState state) {
        Object value = state.getAssignedValue(node.getName());
        if (value == null) {
            throw new EngineException("Parameter " + node.getName() + " is not set");
        }
        ParameterTransformer transformer = ParameterTransformerRegistry
                .getDefault()
                .findTransformer(node.getType(), value.getClass());
        return transformer.transform(value);
    }
}
