package ru.romanchuk.sqlmaster.engine.impl;

import ru.romanchuk.sqlmaster.parser.impl.PlainTextNode;

/**
 * @author Alexey Romanchuk
 */
public class PlainTextNodeTransformer implements NodeTransformer<PlainTextNode> {
    @Override
    public String transform(PlainTextNode node, TemplateState state) {
        return node.getText();
    }
}
