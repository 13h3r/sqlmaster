package ru.romanchuk.sqlmaster.engine;

import ru.romanchuk.sqlmaster.engine.impl.EngineImpl;
import ru.romanchuk.sqlmaster.engine.impl.Template;
import ru.romanchuk.sqlmaster.parser.ParserFacade;

/**
 * @author Alexey Romanchuk
 */
public class EngineFacade {
    public static Template createTemplate(String template) {
        return new Template(ParserFacade.createParser().parse(template));
    }

    public static String process(Template t) {
        return new EngineImpl().process(t);
    }
}
