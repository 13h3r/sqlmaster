package ru.romanchuk.sqlmaster.engine;

import ru.romanchuk.sqlmaster.engine.node.*;
import ru.romanchuk.sqlmaster.parser.Node;
import ru.romanchuk.sqlmaster.parser.TemplateTree;
import ru.romanchuk.sqlmaster.parser.tree.EmbeddedNode;
import ru.romanchuk.sqlmaster.parser.tree.ParameterNode;
import ru.romanchuk.sqlmaster.parser.tree.PlainTextNode;
import ru.romanchuk.sqlmaster.parser.tree.RootNode;

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
        transformers.put(EmbeddedNode.class, new EmbeddedNodeTransformer());
    }

    public static String processNode(Node node, TemplateState state) {
        NodeTransformer transformer = transformers.get(node.getClass());
        if(transformer == null) {
            throw new EngineException("Unable to find transformer for " + node);
        }
        return transformer.transform(node, state);
    }

    public String process(Template template) {
        TemplateState processState = new TemplateState(template.getState());
        enableEmbeddedNodes(template.getTree(), processState);
        return processNode(template.getTree().getRootNode(), processState);
    }

    private void enableEmbeddedNodes(TemplateTree tree, TemplateState processState) {
        for(String param : processState.getParameters().keySet()) {
            for(ParameterNode pNode : tree.getParameterNode(param)) {
                Node up = pNode.getParent();
                while(!(up instanceof RootNode)) {
                    if(up instanceof EmbeddedNode) {
                        processState.enable(((EmbeddedNode) up).getName(), true);
                    }
                    up = up.getParent();
                }
            }
        }
    }
}
