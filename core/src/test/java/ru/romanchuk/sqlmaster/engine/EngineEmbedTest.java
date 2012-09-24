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

    @Test
    public void testParameterAndEmbedded() {
        Template t = EngineFacade.createTemplate(
                "select 1 from t/**where{*/ where name = /**string name(*/'John'/**)}*/");
        Assert.assertEquals(EngineFacade.process(t), "select 1 from t");

        t.assignValue("name", "Kate");
        Assert.assertEquals(EngineFacade.process(t), "select 1 from t where name = Kate");

    }

    @Test
    public void testParameterAndEmbeddedInEmbedded() {
        Template t = EngineFacade.createTemplate(
                "select 1 from t/**where{*/ " +
                        "where 1=1" +
                        "/**name{*/ and name = /**string name(*/'John'/**)}*/" +
                        "/**city{*/ and city = /**string city(*/'Nsk'/**)}}*/" +
                        "");
        Assert.assertEquals(EngineFacade.process(t), "select 1 from t");

        t.assignValue("city", "NY");
        Assert.assertEquals(EngineFacade.process(t), "select 1 from t where 1=1 and city = NY");

        t.assignValue("name", "Mike");
        Assert.assertEquals(EngineFacade.process(t), "select 1 from t where 1=1 and name = Mike and city = NY");


    }


}
