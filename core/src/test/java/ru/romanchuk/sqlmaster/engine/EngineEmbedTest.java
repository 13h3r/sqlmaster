package ru.romanchuk.sqlmaster.engine;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.romanchuk.sqlmaster.SimpleEngine;
import ru.romanchuk.sqlmaster.Template;

import static org.testng.Assert.fail;

/**
 * @author Alexey Romanchuk
 */
public class EngineEmbedTest {

    @Test
    public void testNoSuchNode() {
        Template t = SimpleEngine.create("1");
        try {
            t.enable("asdf");
            fail();
        } catch(EngineException e) {}
    }

    @Test
    public void test1Embedded() {
        Template t = SimpleEngine.create("select 1 from /**table{*/dual/**}*/");
        Assert.assertEquals(t.process(), "select 1 from ");

        t.enable("table");
        Assert.assertEquals(t.process(), "select 1 from dual");

        t.enable("table", false);
        Assert.assertEquals(t.process(), "select 1 from ");
    }


    @Test
    public void test1EnableAnonymousSections() {
        Template t = SimpleEngine.create("select 1 from t/**{*/ where name = /**string name(*/'John'/**)}*/");
        Assert.assertEquals(t.process(), "select 1 from t");

        t.assignValue("name", "Kate");
        Assert.assertEquals(t.process(), "select 1 from t where name = 'Kate'");
    }

    @Test
    public void test2Embedded() {
        Template t = SimpleEngine.create("select 1 from /**table{*/dual/**}*/ and /**table{*/dual/**}*/");
        Assert.assertEquals(t.process(), "select 1 from  and ");

        t.enable("table");
        Assert.assertEquals(t.process(), "select 1 from dual and dual");
    }

    @Test
    public void testParameterAndEmbedded() {
        Template t = SimpleEngine.create("select 1 from t/**where{*/ where name = /**string name(*/'John'/**)}*/");
        Assert.assertEquals(t.process(), "select 1 from t");

        t.assignValue("name", "Kate");
        Assert.assertEquals(t.process(), "select 1 from t where name = 'Kate'");
    }

    @Test
    public void testParameterAndEmbeddedInEmbedded() {
        Template t = SimpleEngine.create("select 1 from t/**where{*/ " +
                "where 1=1" +
                "/**name{*/ and name = /**string name(*/'John'/**)}*/" +
                "/**city{*/ and city = /**string city(*/'Nsk'/**)}}*/" +
                "");
        Assert.assertEquals(t.process(), "select 1 from t");

        t.assignValue("city", "NY");
        Assert.assertEquals(t.process(), "select 1 from t where 1=1 and city = 'NY'");

        t.assignValue("name", "Mike");
        Assert.assertEquals(t.process(), "select 1 from t where 1=1 and name = 'Mike' and city = 'NY'");
    }
}
