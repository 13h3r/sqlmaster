package ru.romanchuk.sqlmaster.engine.impl;

import ru.romanchuk.sqlmaster.parser.Node;
import ru.romanchuk.sqlmaster.parser.impl.RootNode;

/**
 * @author Alexey Romanchuk
 */
public class RootNodeTransformer implements NodeTransformer<RootNode> {
    @Override
    public String transform(RootNode node) {
        StringBuilder result = new StringBuilder();
        for(Node walker : node.getChildes()){
            result.append(EngineImpl.processNode(walker));
        }
        return result.toString();
    }
}
