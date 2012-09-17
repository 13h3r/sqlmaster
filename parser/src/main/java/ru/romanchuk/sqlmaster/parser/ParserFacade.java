package ru.romanchuk.sqlmaster.parser;

import ru.romanchuk.sqlmaster.parser.impl.ParserImpl;

/**
 * @author Alexey Romanchuk
 */
public class ParserFacade {
    public static final Parser createParser() {
        return new ParserImpl();
    }
}