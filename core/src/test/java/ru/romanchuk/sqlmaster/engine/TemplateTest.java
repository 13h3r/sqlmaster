package ru.romanchuk.sqlmaster.engine;

import org.testng.annotations.Test;

import static org.testng.Assert.fail;

/**
 * @author Alexey Romanchuk
 */
public class TemplateTest {

    @Test
    public void setNotExistedParameter() {
        try {
            Template t = EngineFacade.createTemplate("select 1 from dual");
            t.assignValue("a", "a");
            fail();
        } catch (EngineException e) {
        }

    }
}
