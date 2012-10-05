package ru.romanchuk.sqlmaster.engine;

import org.testng.annotations.Test;
import ru.romanchuk.sqlmaster.SimpleEngine;
import ru.romanchuk.sqlmaster.Template;

import static org.testng.Assert.fail;

/**
 * @author Alexey Romanchuk
 */
public class TemplateTest {

    @Test
    public void setNotExistedParameter() {
        try {
            Template t = SimpleEngine.create("select 1 from dual");
            t.assignValue("a", "a");
            fail();
        } catch (EngineException e) {
        }

    }
}
