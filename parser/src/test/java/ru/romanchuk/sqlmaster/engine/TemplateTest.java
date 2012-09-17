package ru.romanchuk.sqlmaster.engine;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.romanchuk.sqlmaster.engine.impl.Template;

/**
 * @author Alexey Romanchuk
 */
public class TemplateTest {

    @Test
    public void emptyTemplate() {
        Template t = EngineFacade.createTemplate("select 1 from dual");
        String result = EngineFacade.process(t);

        Assert.assertEquals(result, "select 1 from dual");
    }
}
