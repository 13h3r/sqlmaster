package ru.romanchuk.sqlmaster.parser.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

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
        assertEquals(t.getChildes().size(), 5);
    }

    @Test
    public void testUnfinishedMarkup() {
        failWithParseException("select * from /**table where 1 = 1");
        failWithParseException("select * from */table where 1 = 1");
        failWithParseException("select * from /** /**table where 1 = 1");
        failWithParseException("select * from */table /** where 1 = 1");
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
