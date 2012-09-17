package ru.romanchuk.sqlmaster.parser;

import java.util.List;

/**
 * @author Alexey Romanchuk
 */
public interface Parser {
    List<Node> parse(String template);
}
