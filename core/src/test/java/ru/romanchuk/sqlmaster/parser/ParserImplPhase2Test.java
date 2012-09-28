package ru.romanchuk.sqlmaster.parser;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.romanchuk.sqlmaster.parser.tree.EmbeddedNode;
import ru.romanchuk.sqlmaster.parser.tree.ParameterNode;
import ru.romanchuk.sqlmaster.parser.tree.ParameterType;
import ru.romanchuk.sqlmaster.parser.tree.PlainTextNode;
import ru.romanchuk.sqlmaster.parser.tree.RootNode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

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
    public void testUnfinishedParameterMarkup() {
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
    public void testUnfinishedEmbeddedMarkup() {
        // wrong number of words
        failWithParseException("select * from /**test table {}*/ where 1 = 1");
        // missed )
        failWithParseException("select * from /**test table {*/ where 1 = 1");
        // starts with )
        failWithParseException("select * from /** } test table {}*/ where 1 = 1");
    }

    @Test
    public void testUnknownType() {
        failWithParseException("select * from /**strange type()*/ where 1 = 1");
    }

    @Test
    public void testUnfinishedMixedMarkup() {
        failWithParseException("select * from /**table {(}*/ where 1 = 1");
        failWithParseException("select * from /**table {)}*/ where 1 = 1");
        failWithParseException("select * from /**table {})*/ where 1 = 1");
        failWithParseException("select * from /**table ()*/ where 1 = 1");
        failWithParseException("select * from /**string table(}*/ where 1 = 1");
        failWithParseException("select * from /**string table{)*/ where 1 = 1");
    }

    @Test
    public void testOneParameter() {
        RootNode t = p.phase2(p.phase1("select * from client where name = /**string name(*/'Abdul Jamal'/**)*/"));

        RootNode ethalon = new RootNode();
        ethalon.add(new PlainTextNode("select * from client where name = "));
        ParameterNode p1 = new ParameterNode("name", ParameterType.STRING);
        p1.add(new PlainTextNode("'Abdul Jamal'"));
        ethalon.add(p1);
        assertEquals(t, ethalon);
    }

    @Test
    public void testParameterInParameter() {
        failWithParseException("/**string p1(string p2())*/");
    }

    @Test
    public void testOneParameterAndNoValueInside() {
        RootNode t = p.phase2(p.phase1("select * from client where name = /**string name()*/"));

        RootNode ethalon = new RootNode();
        ethalon.add(new PlainTextNode("select * from client where name = "));
        ethalon.add(new ParameterNode("name", ParameterType.STRING));
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
        RootNode result = p.phase2(p.phase1("select /**string name(*/name/**) number order(*/order/**)*/"));

        RootNode ethalon = new RootNode();
        ethalon.add(new PlainTextNode("select "));
        ParameterNode p1 = new ParameterNode("name", ParameterType.STRING);
        ethalon.add(p1);
        p1.add(new PlainTextNode("name"));
        ParameterNode p2 = new ParameterNode("order", ParameterType.NUMBER);
        ethalon.add(p2);
        p2.add(new PlainTextNode("order"));

        assertEquals(result, ethalon);
    }

    @Test
    public void testSimpleEmbedded() {
        RootNode t = p.phase2(p.phase1("select * from client where name = /**join{}*/"));

        RootNode ethalon = new RootNode();
        ethalon.add(new PlainTextNode("select * from client where name = "));
        ethalon.add(new EmbeddedNode("join"));
        assertEquals(t, ethalon);
    }

    @Test
    public void testSimpleAnonEmbedded() {
        RootNode t = p.phase2(p.phase1("select * from client where name = /**{}*/"));

        RootNode ethalon = new RootNode();
        ethalon.add(new PlainTextNode("select * from client where name = "));
        ethalon.add(new EmbeddedNode(""));
        assertEquals(t, ethalon);
    }

    @Test
    public void testSimpleEmbeddedSpaces() {
        RootNode t = p.phase2(p.phase1("select * from client where name = /** " +
                "  join   {  \t  }  */"));

        RootNode ethalon = new RootNode();
        ethalon.add(new PlainTextNode("select * from client where name = "));
        ethalon.add(new EmbeddedNode("join"));
        assertEquals(t, ethalon);
    }

    @Test
    public void testEmbedded3xTimes() {
        RootNode t = p.phase2(p.phase1("select * from t0\n" +
                "/**j1{*/join t1\n" +
                "/**j2{*/join t2\n" +
                "/**j3{*/join t3\n" +
                "/**}}}*/" +
                ""
        ));
        RootNode ethalon = new RootNode();
        ethalon.add(new PlainTextNode("select * from t0\n"));
        EmbeddedNode j1 = new EmbeddedNode("j1");
        ethalon.add(j1);
        j1.add(new PlainTextNode("join t1\n"));
        EmbeddedNode j2 = new EmbeddedNode("j2");
        j2.add(new PlainTextNode("join t2\n"));
        EmbeddedNode j3 = new EmbeddedNode("j3");
        j3.add(new PlainTextNode("join t3\n"));
        j2.add(j3);
        j1.add(j2);
        assertEquals(t, ethalon);
    }


    @Test
    public void testEmbedded3xAnonTimes() {
        RootNode t = p.phase2(p.phase1("select * from t0\n" +
                "/**{*/join t1\n" +
                "/**{*/join t2\n" +
                "/**j3{*/join t3\n" +
                "/**}}}*/" +
                ""
        ));
        RootNode ethalon = new RootNode();
        ethalon.add(new PlainTextNode("select * from t0\n"));
        EmbeddedNode j1 = new EmbeddedNode("");
        ethalon.add(j1);
        j1.add(new PlainTextNode("join t1\n"));
        EmbeddedNode j2 = new EmbeddedNode("");
        j2.add(new PlainTextNode("join t2\n"));
        EmbeddedNode j3 = new EmbeddedNode("j3");
        j3.add(new PlainTextNode("join t3\n"));
        j2.add(j3);
        j1.add(j2);
        assertEquals(t, ethalon);
    }

    private void failWithParseException(String template) {
        try {
            p.phase2(p.phase1(template));
            fail();
        } catch (ParseException p) {
        }
    }


}
