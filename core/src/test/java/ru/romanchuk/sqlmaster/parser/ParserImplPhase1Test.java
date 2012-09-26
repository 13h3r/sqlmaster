package ru.romanchuk.sqlmaster.parser;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.romanchuk.sqlmaster.parser.tree.MarkupNode;
import ru.romanchuk.sqlmaster.parser.tree.PlainTextNode;
import ru.romanchuk.sqlmaster.parser.tree.RootNode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

/**
 * @author Alexey Romanchuk
 */
@Test
public class ParserImplPhase1Test {

    private ParserImpl p;

    @BeforeMethod
    public void createParser() {
        this.p = new ParserImpl();
    }
    
    @Test
    public void simpleTest() {
        RootNode t = p.phase1("select * from /**string table(*/client/**)*/ where 1 = 1");
        assertNotNull(t);
        RootNode ethalon = new RootNode();
        ethalon.add(new PlainTextNode("select * from "));
        ethalon.add(new MarkupNode("string table("));
        ethalon.add(new PlainTextNode("client"));
        ethalon.add(new MarkupNode(")"));
        ethalon.add(new PlainTextNode(" where 1 = 1"));

        assertEquals(ethalon, t);
    }

    @Test
    public void testUnfinishedMarkup() {
        failWithParseException("select * from /**table where 1 = 1");
        failWithParseException("select * from */table where 1 = 1");
        failWithParseException("select * from /** /**table where 1 = 1");
        failWithParseException("select * from */table /** where 1 = 1");
    }

    @Test
    public void testOnlyMarkup() {
        RootNode t = p.phase1("/**string table()*/");
        RootNode ethalon = new RootNode();
        ethalon.add(new MarkupNode("string table()"));
        assertEquals(t, ethalon);
    }
    @Test
    public void testStartWithMarkup() {
        RootNode t = p.phase1("/**string table()*/ test");
        RootNode ethalon = new RootNode();
        ethalon.add(new MarkupNode("string table()"));
        ethalon.add(new PlainTextNode(" test"));
        assertEquals(t, ethalon);
    }

    @Test
    public void notMarkup() {
        RootNode t = p.phase1("select * from client where 1 = 1 and 5 / 8 * 1");
        assertNotNull(t);
        assertEquals(t.getChildes().size(), 1);
    }

    private void failWithParseException(String template) {
        try {
            p.phase1(template);
            fail();
        } catch(ParseException p) {}
    }



}
