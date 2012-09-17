package ru.romanchuk.sqlmaster.parser.impl;

import org.apache.commons.lang.StringUtils;
import ru.romanchuk.sqlmaster.parser.Node;
import ru.romanchuk.sqlmaster.parser.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Romanchuk
 */
public class ParserImpl implements Parser {

    public static final String COMMENT_START = "/**";
    public static final String COMMENT_END = "*/";

    @Override
    public List<Node> parse(String template) {
        return null;
    }

    public List<Node> phase1(String template) {
        List<Node> result = new ArrayList<Node>();
        String current = template;
        while(!current.isEmpty()) {
            boolean startFound = false;
            String text = StringUtils.substringBefore(current, COMMENT_START);
            if(!text.isEmpty()) {
                if(text.contains(COMMENT_END)) {
                    throw new ParseException();
                }
                result.add(new PlainTextNode(text));
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
            result.add(new MarkupNode(markupText));
            current = StringUtils.substringAfter(current, COMMENT_END);
        }
        return result;
    }
}