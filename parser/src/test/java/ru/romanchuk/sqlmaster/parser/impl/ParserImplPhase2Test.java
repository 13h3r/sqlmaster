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
//        failWithParseException("select * from /**table*/ where 1 = 1");
    }

    @Test
    public void testOneParameter() {
        RootNode t = p.phase2(p.phase1("select * from client where name = /**string name(*/'Abdul Jamal'/**)*/"));
        assertNotNull(t);
        assertEquals(t.getChildes().size(), 2);
        PlainTextNode text = (PlainTextNode) t.getChildes().get(0);
        assertEquals("select * from client where name = ", text.getText());
        ParameterNode param = (ParameterNode) t.getChildes().get(1);
        assertEquals(param.getName(), "name");
        assertEquals(param.getType(), "string");
        assertEquals(param.getChildes().size(), 1);
        assertEquals(((PlainTextNode) param.getChildes().get(0)).getText(), "'Abdul Jamal'");
    }


    @Test
    public void emptyTemplate() {
        RootNode t = p.phase2(p.phase1("select * from client where 1 = 1 and 5 / 8 * 1"));
        assertNotNull(t);
        assertEquals(t.getChildes().size(), 1);
    }

    @Test
    public void testTwoMarkupInOneComment() {
//        p.phase2(p.phase1("select /**string name(*/name/**) string order(*/order/**)*/"));
    }

    private void failWithParseException(String template) {
        try {
            p.phase2(p.phase1(template));
            fail();
        } catch (ParseException p) {
        }
    }


}
