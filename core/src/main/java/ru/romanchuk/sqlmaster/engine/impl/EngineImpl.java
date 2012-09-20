package ru.romanchuk.sqlmaster.engine.impl;

import ru.romanchuk.sqlmaster.engine.EngineException;
import ru.romanchuk.sqlmaster.parser.Node;
import ru.romanchuk.sqlmaster.parser.impl.ParameterNode;
import ru.romanchuk.sqlmaster.parser.impl.PlainTextNode;
import ru.romanchuk.sqlmaster.parser.impl.RootNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexey Romanchuk
 */
public class EngineImpl {

    private static Map<Class, NodeTransformer> transformers = new HashMap<Class, NodeTransformer>();
    static {
        transformers.put(PlainTextNode.class, new PlainTextNodeTransformer());
        transformers.put(RootNode.class, new RootNodeTransformer());
        transformers.put(ParameterNode.class, new ParameterNodeTransformer());
    }

    public static String processNode(Node node, TemplateState state) {
        NodeTransformer transformer = transformers.get(node.getClass());
        if(transformer == null) {
            throw new EngineException("Unable to find transformer for " + node);
        }
        return transformer.transform(node, state);
    }

    public String process(Template template) {
        validateAllParametersPresent(template);
        return processNode(template.getTree().getRootNode(), template.getState());
    }

    private void validateAllParametersPresent(Template template) {
        for(ParameterNode walker : template.getTree().getParameters()) {
            if(template.getState().getAssignedValue(walker.getName()) == null) {
                throw new EngineException("Parameter value " + walker.getName() + " is not set");
            }
        }
    }
}
