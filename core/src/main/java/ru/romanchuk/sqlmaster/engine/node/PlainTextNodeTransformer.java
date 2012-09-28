package ru.romanchuk.sqlmaster.engine.node;

import ru.romanchuk.sqlmaster.engine.TemplateState;
import ru.romanchuk.sqlmaster.parser.tree.PlainTextNode;

/**
 * @author Alexey Romanchuk
 */
public class PlainTextNodeTransformer implements NodeTransformer<PlainTextNode> {
    @Override
    public String transform(PlainTextNode node, TemplateState state) {
        return node.getText();
    }
}
