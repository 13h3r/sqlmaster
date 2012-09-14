package ru.romanchuk.sqlmaster.parser.impl;

import org.apache.commons.lang.StringUtils;
import ru.romanchuk.sqlmaster.parser.Parser;

/**
 * @author Alexey Romanchuk
 */
public class ParserImpl implements Parser {

    public static final String COMMENT_START = "/**";
    public static final String COMMENT_END = "*/";

    @Override
    public TemplateImpl parse(String template) {
        TemplateImpl result = new TemplateImpl();
        String current = template;
        while(!current.isEmpty()) {
            boolean startFound = false;
            String text = StringUtils.substringBefore(current, COMMENT_START);
            if(!text.isEmpty()) {
                if(text.contains(COMMENT_END)) {
                    throw new ParseException();
                }
                result.getNodes().add(new PlainTextNode(text));
                startFound = true;
                current = StringUtils.substringAfter(current, COMMENT_START);
            }
            if(current.isEmpty()) {
                break;
            }
            if(current.contains(COMMENT_END) != startFound) {
                throw new ParseException();
            }
            String markupText = StringUtils.substringBefore(current, COMMENT_END);
            result.getNodes().add(new MarkupNode(markupText));
            current = StringUtils.substringAfter(current, COMMENT_END);
        }
        return result;
    }

    /*
    if(current.isEmpty()) {
                throw new ParseException();
            }
     */
}
