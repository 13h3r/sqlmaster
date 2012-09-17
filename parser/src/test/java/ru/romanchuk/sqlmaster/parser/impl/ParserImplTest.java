package ru.romanchuk.sqlmaster.parser.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.romanchuk.sqlmaster.parser.Node;

import java.util.List;

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
        List<Node> t = p.phase1("select * from /**string table(*/client/**)*/ where 1 = 1");
        assertNotNull(t);
        assertEquals(t.size(), 5);
    }

    @Test
    public void testUnfinishedMarkup() {
        failTest("select * from /**table where 1 = 1");
        failTest("select * from */table where 1 = 1");
        failTest("select * from /** /**table where 1 = 1");
        failTest("select * from */table /** where 1 = 1");
    }

    @Test
    public void notMarkup() {
        List<Node> t = p.phase1("select * from client where 1 = 1 and 5 / 8 * 1");
        assertNotNull(t);
        assertEquals(t.size(), 1);
    }

    private void failTest(String template) {
        try {
            p.phase1(template);
            fail();
        } catch(ParseException p) {}
    }

}
