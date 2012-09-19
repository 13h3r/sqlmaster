package ru.romanchuk.sqlmaster.parser.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Alexey Romanchuk
 */
@Test
public class ParserImplPhase2Test {

    private ParserImpl p;

    @BeforeMethod
    public void createParser() {
        this.p = new ParserImpl();
    }

    @Test
    public void testUnfinishedMarkup() {
        // no (
        failWithParseException("select * from /**table*/ where 1 = 1");
        // wrong number of words
        failWithParseException("select * from /**test test table ()*/ where 1 = 1");
        // missed )
        failWithParseException("select * from /**test table (*/ where 1 = 1");
        // starts with )
        failWithParseException("select * from /**)test table ()*/ where 1 = 1");
    }

    @Test
    public void testOneParameter() {
        RootNode t = p.phase2(p.phase1("select * from client where name = /**string name(*/'Abdul Jamal'/**)*/"));

        RootNode ethalon = new RootNode();
        ethalon.add(new PlainTextNode("select * from client where name = "));
        ParameterNode p1 = new ParameterNode("name", "string");
        p1.add(new PlainTextNode("'Abdul Jamal'"));
        ethalon.add(p1);
        assertEquals(t, ethalon);
    }

    @Test
    public void testOneParameterAndNoValueInside() {
        RootNode t = p.phase2(p.phase1("select * from client where name = /**string name()*/"));

        RootNode ethalon = new RootNode();
        ethalon.add(new PlainTextNode("select * from client where name = "));
        ethalon.add(new ParameterNode("name", "string"));
        assertEquals(t, ethalon);
    }

    @Test
    public void emptyTemplate() {
        RootNode t = p.phase2(p.phase1("select * from client where 1 = 1 and 5 / 8 * 1"));
        assertNotNull(t);
        assertEquals(t.getChildes().size(), 1);
    }

    @Test
    public void testTwoMarkupInOneComment() {
        RootNode result = p.phase2(p.phase1("select /**string name(*/name/**) int order(*/order/**)*/"));

        RootNode ethalon = new RootNode();
        ethalon.add(new PlainTextNode("select "));
        ParameterNode p1 = new ParameterNode("name", "string");
        ethalon.add(p1);
        p1.add(new PlainTextNode("name"));
        ParameterNode p2 = new ParameterNode("order", "int");
        ethalon.add(p2);
        p2.add(new PlainTextNode("order"));

        assertEquals(result, ethalon);


    }

    private void failWithParseException(String template) {
        try {
            p.phase2(p.phase1(template));
            fail();
        } catch (ParseException p) {
        }
    }


}
