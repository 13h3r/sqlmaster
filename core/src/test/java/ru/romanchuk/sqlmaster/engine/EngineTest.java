package ru.romanchuk.sqlmaster.engine;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.romanchuk.sqlmaster.engine.impl.Template;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * @author Alexey Romanchuk
 */
public class EngineTest {

    @Test
    public void emptyTemplate() {
        Template t = EngineFacade.createTemplate("select 1 from dual");
        String result = EngineFacade.process(t);

        Assert.assertEquals(result, "select 1 from dual");
    }

    @Test
    public void test1Parameter() {
        Template t = EngineFacade.createTemplate("select 1 from /**string table(*/dual/**)*/");
        t.assignValue("table", "t");
        String result = EngineFacade.process(t);

        Assert.assertEquals(result, "select 1 from t");
    }

    @Test
    public void test1ParameterNotSet() {
        try {
            Template t = EngineFacade.createTemplate("select 1 from /**string table(*/dual/**)*/");
            EngineFacade.process(t);
            fail();
        } catch (EngineException e) {
        }
    }

    @Test
    public void testTwoOccurrencesOfOneParameter() {
        Template t = EngineFacade.createTemplate(
                "select 1 from /**string table(*/dual/**)*/" +
                        " join /**string table(*/dual/**)*/");
        t.assignValue("table", "test1");
        assertEquals("select 1 from test1 join test1", EngineFacade.process(t));
    }


    @Test
    public void test2Parameter() {
        Template t = EngineFacade.createTemplate("select 1 from /**string table(*/dual/**)*/ " +
                "where name = /**string name(*/'John'/**)*/");
        t.assignValue("table", "t");
        t.assignValue("name", "Jane");
        String result = EngineFacade.process(t);

        Assert.assertEquals(result, "select 1 from t where name = Jane");
    }

    @Test
    public void test1Embedded() {
        Template t = EngineFacade.createTemplate("select 1 from /**table{*/dual/**}*/");
        Assert.assertEquals(EngineFacade.process(t), "select 1 from ");

        t.embed("table");
        Assert.assertEquals(EngineFacade.process(t), "select 1 from dual");
    }


}
