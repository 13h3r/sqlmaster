package ru.romanchuk.sqlmaster.parser.impl;

import org.apache.commons.lang.StringUtils;
import ru.romanchuk.sqlmaster.parser.NodeWithChildes;

/**
 * @author Alexey Romanchuk
 */
public class RootNode extends AbstractNodeWithChildes {
    @Override
    public NodeWithChildes getParent() {
        throw new ParseException("Calling getParent() of RootNode");
    }

    @Override
    public String toString() {
        return "Root(" + StringUtils.join(getChildes(), ",") + ")";
    }
}
