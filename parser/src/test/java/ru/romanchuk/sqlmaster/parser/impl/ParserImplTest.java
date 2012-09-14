package ru.romanchuk.sqlmaster.parser.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Alexey Romanchuk
 */
@Test
public class ParserImplTest {

    private ParserImpl p;

    @BeforeMethod
    public void createParser() {
        this.p = new ParserImpl();
    }
    
    @Test
    public void simpleTest() {
        TemplateImpl t = p.parse("select * from /**string table(*/client/**)*/ where 1 = 1");
        assertNotNull(t.getNodes());
        assertEquals(t.getNodes().size(), 5);
    }

    @Test
    public void testUnfinishedMarkup() {
        try {
            p.parse("select * from /**table where 1 = 1");
            fail();
        } catch(ParseException p) {}
    }

    @Test
    public void notMarkup() {
        TemplateImpl t = p.parse("select * from client where 1 = 1 and 5 / 8 * 1");
        assertNotNull(t.getNodes());
        assertEquals(t.getNodes().size(), 1);
    }
}
