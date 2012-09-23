package ru.romanchuk.sqlmaster.parser;

import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Alexey Romanchuk
 */
@Test
public class ParserImplPhaseElementTest {

    private final Pattern pattern = Pattern.compile(ParserImpl.ELEMENT_TEMPLATE_START);

    @Test
    public void simple() {
        Matcher m = pattern.matcher("string name( text");
        assertTrue(m.matches());
        assertEquals(m.group(1), "string");
        assertEquals(m.group(2), "name");
        assertEquals(m.group(3), " text");
    }
    @Test
    public void simpleWithSpaces() {
        Matcher m = pattern.matcher("string    name     ( text");
        assertTrue(m.matches());
        assertEquals(m.group(1), "string");
        assertEquals(m.group(2), "name");
        assertEquals(m.group(3), " text");
    }
}
