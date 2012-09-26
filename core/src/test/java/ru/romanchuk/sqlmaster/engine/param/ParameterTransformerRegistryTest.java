package ru.romanchuk.sqlmaster.engine.param;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.romanchuk.sqlmaster.engine.EngineException;
import ru.romanchuk.sqlmaster.parser.tree.ParameterType;

import static org.testng.Assert.assertEquals;


/**
 * @author Alexey Romanchuk
 */
public class ParameterTransformerRegistryTest {

    private ParameterTransformerRegistry r;

    @BeforeMethod
    public void setup() {
        r = new ParameterTransformerRegistry();
        r.register(new QuotedStringTransformer());
        r.register(new IntTransformer());
    }

    @Test
    public void testNulls() {
        findShouldFails(null, null);
        findShouldFails(null, String.class);
        findShouldFails(ParameterType.STRING, null);
    }

    @Test
    public void testNotFound() {
        findShouldFails(ParameterType.INT, String.class);
        findShouldFails(ParameterType.STRING, Class.class);
        findShouldFails(ParameterType.STRING, Integer.class);
    }

    @Test
    public void testCorrectFind() {
        assertEquals(r.findTransformer(ParameterType.INT, Integer.class).getClass(), IntTransformer.class);
        assertEquals(r.findTransformer(ParameterType.STRING, String.class).getClass(), QuotedStringTransformer.class);
    }

    private void findShouldFails(ParameterType type, Class klazz) {
        try {
            r.findTransformer(type, klazz);
            AssertJUnit.fail();
        } catch (EngineException e) {
        } catch (IllegalArgumentException a) {
        }
    }

}
