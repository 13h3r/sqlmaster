package ru.romanchuk.sqlmaster.engine;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.romanchuk.sqlmaster.parser.Node;
import ru.romanchuk.sqlmaster.parser.NodeWithChildes;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * @author Alexey Romanchuk
 */
public class EngineParamTest {

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

        Assert.assertEquals(result, "select 1 from 't'");
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
    public void testParameterDoubleSet() {
        try {
            Template t = EngineFacade.createTemplate("select 1 from /**string table(*/dual/**)*/");
            t.assignValue("table", "1");
            t.assignValue("table", "1");
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
        assertEquals("select 1 from 'test1' join 'test1'", EngineFacade.process(t));
    }


    @Test
    public void test2Parameter() {
        Template t = EngineFacade.createTemplate("select 1 from /**string table(*/dual/**)*/ " +
                "where name = /**string name(*/'John'/**)*/");
        t.assignValue("table", "t");
        t.assignValue("name", "Jane");
        String result = EngineFacade.process(t);

        Assert.assertEquals(result, "select 1 from 't' where name = 'Jane'");
    }

    @Test
    public void testWhenNodeTranformerIsUnknown() {
        final Template t = EngineFacade.createTemplate("test");
        t.getTree().getRootNode().add(new Node() {
            @Override
            public NodeWithChildes getParent() {
                return t.getTree().getRootNode();
            }

            @Override
            public void setParent(NodeWithChildes node) {
            }
        });
        try {
            EngineFacade.process(t);
            fail();
        }catch(EngineException e) {
        }

    }
}
