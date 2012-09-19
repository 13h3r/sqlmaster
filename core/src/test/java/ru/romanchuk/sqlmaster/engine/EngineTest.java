package ru.romanchuk.sqlmaster.engine;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.romanchuk.sqlmaster.engine.impl.Template;

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
        //todo
//        Template t = EngineFacade.createTemplate("select 1 from /**string table(*/dual/**)*/");
//        t.assignValue("table", "t");
//        String result = EngineFacade.process(t);
//
//        Assert.assertEquals(result, "select 1 from t");
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


}
