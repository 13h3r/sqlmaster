package ru.romanchuk.sqlmaster.engine.impl.nodetransformer;

import ru.romanchuk.sqlmaster.engine.impl.EngineImpl;
import ru.romanchuk.sqlmaster.engine.impl.NodeTransformer;
import ru.romanchuk.sqlmaster.engine.impl.TemplateState;
import ru.romanchuk.sqlmaster.parser.Node;
import ru.romanchuk.sqlmaster.parser.tree.RootNode;

/**
 * @author Alexey Romanchuk
 */
public class RootNodeTransformer implements NodeTransformer<RootNode> {
    @Override
    public String transform(RootNode node, TemplateState state) {
        StringBuilder result = new StringBuilder();
        for(Node walker : node.getChildes()){
            result.append(EngineImpl.processNode(walker, state));
        }
        return result.toString();
    }
}
