package ru.romanchuk.sqlmaster.parser;

import org.apache.commons.lang.StringUtils;
import ru.romanchuk.sqlmaster.parser.tree.EmbeddedNode;
import ru.romanchuk.sqlmaster.parser.tree.MarkupNode;
import ru.romanchuk.sqlmaster.parser.tree.ParameterNode;
import ru.romanchuk.sqlmaster.parser.tree.ParameterType;
import ru.romanchuk.sqlmaster.parser.tree.PlainTextNode;
import ru.romanchuk.sqlmaster.parser.tree.RootNode;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexey Romanchuk
 */
class ParserImpl implements Parser {

    public static final String COMMENT_START = "/**";
    public static final String COMMENT_END = "*/";
    public static final String PARAMETER_START = "^([a-z]+)[ ]+([a-zA-Z0-9_-]+)[ ]*\\((.*)";
    public static final String PARAMETER_END = ")";
    public static final String EMBEDDED_START = "^([a-zA-Z0-9_-]*)[ ]*\\{(.*)";
    public static final String EMBEDDED_END = "}";

    @Override
    public TemplateTree parse(String template) {
        return phase2(phase1(template));
    }

    public RootNode phase1(String template) {
        RootNode result = new RootNode();
        String current = template;
        while (!current.isEmpty()) {
            boolean startFound;
            String text = StringUtils.substringBefore(current, COMMENT_START);
            if (!text.isEmpty()) {
                result.add(new PlainTextNode(text));
                if (text.contains(COMMENT_END)) {
                    throw new ParseException();
                }

            }
            if (current.contains(COMMENT_START)) {
                startFound = true;
                current = StringUtils.substringAfter(current, COMMENT_START);
            } else {
                break;
            }
            if (current.isEmpty()) {
                break;
            }
            if (current.contains(COMMENT_END) != startFound) {
                throw new ParseException();
            }
            String markupText = StringUtils.substringBefore(current, COMMENT_END);
            result.add(new MarkupNode(markupText));
            current = StringUtils.substringAfter(current, COMMENT_END);
        }
        return result;
    }

    public TemplateTreeImpl phase2(RootNode input) {
        TemplateTreeImpl result = new TemplateTreeImpl(new RootNode());
        NodeWithChildes currentRoot = result.getRootNode();
        for (Node walker : input.getChildes()) {
            if (walker instanceof PlainTextNode) {
                currentRoot.add(walker);
            } else if (walker instanceof MarkupNode) {
                String markup = ((MarkupNode) walker).getMarkup().trim();
                while (!markup.isEmpty()) {
                    Matcher parameterStart = Pattern.compile(PARAMETER_START).matcher(markup);
                    Matcher embeddedStart = Pattern.compile(EMBEDDED_START).matcher(markup);
                    if (parameterStart.matches()) {
                        String name = parameterStart.group(2);
                        if (currentRoot instanceof ParameterNode) {
                            throw new ParseException("Element " + name + " placed inside other element");
                        }
                        ParameterType type = ParameterType.getByTemplateName(parameterStart.group(1));
                        if (type == null) {
                            throw new ParseException("Unable to determine type " + parameterStart.group(1));
                        }
                        List<ParameterNode> existedNode = result.getParameterNode(name);
                        if (!existedNode.isEmpty() && existedNode.get(0).getType() != type) {
                            throw new ParseException("Different types for " + name + " parameter");
                        }
                        ParameterNode parameterNode = new ParameterNode(
                                name,
                                type);
                        currentRoot.add(parameterNode);
                        currentRoot = parameterNode;
                        markup = parameterStart.group(3).trim();
                    } else if (markup.startsWith(PARAMETER_END)) {
                        if (!(currentRoot instanceof ParameterNode)) {
                            throw new ParseException("Incorrect ')' after " + currentRoot);
                        }
                        currentRoot = currentRoot.getParent();
                        markup = StringUtils.substringAfter(markup, PARAMETER_END).trim();
                    } else if (embeddedStart.matches()) {
                        EmbeddedNode node = new EmbeddedNode(embeddedStart.group(1));
                        currentRoot.add(node);
                        currentRoot = node;
                        markup = embeddedStart.group(2).trim();
                    } else if (markup.startsWith(EMBEDDED_END)) {
                        if (!(currentRoot instanceof EmbeddedNode)) {
                            throw new ParseException("Incorrect '}' after " + currentRoot);
                        }
                        currentRoot = currentRoot.getParent();
                        markup = StringUtils.substringAfter(markup, EMBEDDED_END).trim();
                    } else {
                        throw new ParseException("Unable to parse: " + markup);
                    }
                }
            }
        }
        if (!(currentRoot instanceof RootNode)) {
            throw new ParseException("Parsing ends on nonroot node");
        }
        return result;
    }
}