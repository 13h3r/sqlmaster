package ru.romanchuk.sqlmaster;

import ru.romanchuk.sqlmaster.engine.EngineImpl;
import ru.romanchuk.sqlmaster.engine.TemplateImpl;
import ru.romanchuk.sqlmaster.parser.ParserFacade;

/**
 * Simple engine without any configuration
 * @author Alexey Romanchuk
 */
public class SimpleEngine {

    private static SimpleEngine INSTANCE;

    private static final SimpleEngine getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SimpleEngine();
        }
        return INSTANCE;
    }

    public String processTemplate(TemplateImpl t) {
        return new EngineImpl().process(t);
    }

    /**
     * Parses template from string
     */
    public static Template create(String s) {
        return new TemplateImpl(ParserFacade.createParser().parse(s), getInstance());
    }
}
