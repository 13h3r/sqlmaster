package ru.romanchuk.sqlmaster.engine;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.romanchuk.sqlmaster.engine.impl.Template;

/**
 * @author Alexey Romanchuk
 */
public class EngineEmbedTest {

    @Test
    public void test1Embedded() {
        Template t = EngineFacade.createTemplate("select 1 from /**table{*/dual/**}*/");
        Assert.assertEquals(EngineFacade.process(t), "select 1 from ");

        t.embed("table");
        Assert.assertEquals(EngineFacade.process(t), "select 1 from dual");
    }

    @Test
    public void test2Embedded() {
        Template t = EngineFacade.createTemplate("select 1 from /**table{*/dual/**}*/ and /**table{*/dual/**}*/");
        Assert.assertEquals(EngineFacade.process(t), "select 1 from  and ");

        t.embed("table");
        Assert.assertEquals(EngineFacade.process(t), "select 1 from dual and dual");
    }

}
