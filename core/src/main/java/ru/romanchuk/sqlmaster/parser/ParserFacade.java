package ru.romanchuk.sqlmaster.parser;

/**
 * @author Alexey Romanchuk
 */
public class ParserFacade {
    public static Parser createParser() {
        return new ParserImpl();
    }
}
