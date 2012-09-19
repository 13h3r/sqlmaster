package ru.romanchuk.sqlmaster.parser.impl;

import org.apache.commons.lang.StringUtils;
import ru.romanchuk.sqlmaster.parser.Node;
import ru.romanchuk.sqlmaster.parser.NodeWithChildes;
import ru.romanchuk.sqlmaster.parser.Parser;
import ru.romanchuk.sqlmaster.parser.TemplateTree;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexey Romanchuk
 */
public class ParserImpl implements Parser {

    public static final String COMMENT_START = "/**";
    public static final String COMMENT_END = "*/";
    public static final String ELEMENT_TEMPLATE_START = "^([a-z]+)[ ]+([a-zA-Z0-9_-]+)[ ]*\\((.*)";
    public static final String ELEMENT_TEMPLATE_END = ")";

    @Override
    public TemplateTree parse(String template) {
        return new TemplateTreeImpl(phase2(phase1(template)));
    }

    public RootNode phase1(String template) {
        RootNode result = new RootNode();
        String current = template;
        while(!current.isEmpty()) {
            boolean startFound = false;
            String text = StringUtils.substringBefore(current, COMMENT_START);
            if(!text.isEmpty()) {
                result.add(new PlainTextNode(text));
                if(text.contains(COMMENT_END)) {
                    throw new ParseException();
                }

            }
            if(current.contains(COMMENT_START)){
                startFound = true;
                current = StringUtils.substringAfter(current, COMMENT_START);
            } else {
                break;
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

    public RootNode phase2(RootNode input) {
        RootNode result = new RootNode();
        NodeWithChildes currentRoot = result;
        for(Node walker : input.getChildes()) {
            if(walker instanceof PlainTextNode) {
                currentRoot.add(walker);
            }
            else if(walker instanceof MarkupNode) {
                String markup = ((MarkupNode) walker).getMarkup().trim();
                while(!markup.isEmpty()) {
                    Matcher startPattern = Pattern.compile(ELEMENT_TEMPLATE_START).matcher(markup);
                    if(startPattern.matches()) {
                        if(currentRoot instanceof ParameterNode) {
                            throw new ParseException("Element " + startPattern.group(2) + " placed inside other element");
                        }
                        ParameterNode parameterNode = new ParameterNode(startPattern.group(2), startPattern.group(1));
                        currentRoot.add(parameterNode);
                        currentRoot = parameterNode;
                        markup = startPattern.group(3).trim();
                    }else if(markup.startsWith(")")) {
                        currentRoot = currentRoot.getParent();
                        markup = StringUtils.substringAfter(markup, ELEMENT_TEMPLATE_END).trim();
                    }else {
                        throw new ParseException("Unable to parse: " + markup);
                    }
                }
            }
        }
        if(!(currentRoot instanceof RootNode)) {
            throw new ParseException("Parsing ends on nonroot node");
        }
        return result;
    }
}