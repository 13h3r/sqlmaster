package ru.romanchuk.sqlmaster.parser;

/**
 * @author Alexey Romanchuk
 */
public interface Parser {
    TemplateTree parse(String template);
}
