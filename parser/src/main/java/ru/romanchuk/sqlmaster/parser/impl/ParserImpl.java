package ru.romanchuk.sqlmaster.parser.impl;

import org.apache.commons.lang.StringUtils;
import ru.romanchuk.sqlmaster.parser.Parser;

/**
 * @author Alexey Romanchuk
 */
public class ParserImpl implements Parser {

    @Override
    public TemplateImpl parse(String template) {
        TemplateImpl result = new TemplateImpl();
        String current = template;
        while(!current.isEmpty()) {
            String text = StringUtils.substringBefore(current, "/**");
            if(!text.isEmpty()) {
                result.getNodes().add(new PlainTextNode(text));
            }
            current = StringUtils.substringAfter(current,  "/**");
            if(current.isEmpty()) {
                break;
            }
            if(!current.contains("*/")) {
                throw new ParseException();
            }
            String markupText = StringUtils.substringBefore(current,  "*/");
            result.getNodes().add(new MarkupNode(markupText));
            current = StringUtils.substringAfter(current, "*/");
        }
        return result;
    }

    /*
    if(current.isEmpty()) {
                throw new ParseException();
            }
     */
}
