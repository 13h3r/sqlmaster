package ru.romanchuk.sqlmaster.engine.impl.nodetransformer;

import ru.romanchuk.sqlmaster.engine.impl.EngineImpl;
import ru.romanchuk.sqlmaster.engine.impl.NodeTransformer;
import ru.romanchuk.sqlmaster.engine.impl.TemplateState;
import ru.romanchuk.sqlmaster.parser.Node;
import ru.romanchuk.sqlmaster.parser.tree.EmbeddedNode;

/**
 * @author Alexey Romanchuk
 */
public class EmbeddedNodeTransformer implements NodeTransformer<EmbeddedNode> {
    @Override
    public String transform(EmbeddedNode node, TemplateState state) {
        if (state.isEmbed(node.getName())) {
            StringBuilder result = new StringBuilder();
            for (Node walker : node.getChildes()) {
                result.append(EngineImpl.processNode(walker, state));
            }
            return result.toString();
        } else {
            return "";
        }
    }
}
