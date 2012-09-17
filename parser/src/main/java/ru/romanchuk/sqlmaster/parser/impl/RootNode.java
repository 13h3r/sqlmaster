package ru.romanchuk.sqlmaster.parser.impl;

import ru.romanchuk.sqlmaster.parser.NodeWithChildes;

/**
 * @author Alexey Romanchuk
 */
public class RootNode extends AbstractNodeWithChildes {
    @Override
    public NodeWithChildes getParent() {
        throw new ParseException("Calling getParent() of RootNode");
    }
}
