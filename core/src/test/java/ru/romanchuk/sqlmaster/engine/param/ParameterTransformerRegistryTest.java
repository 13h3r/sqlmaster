package ru.romanchuk.sqlmaster.engine.param;

import com.google.common.collect.Lists;
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
        r = ParameterTransformerRegistry.getDefault();
    }

    @Test
    public void testNulls() {
        findShouldFails(null, null);
        findShouldFails(null, String.class);
        findShouldFails(ParameterType.STRING, null);
    }

    @Test
    public void testNotFound() {
        findShouldFails(ParameterType.NUMBER, String.class);
        findShouldFails(ParameterType.STRING, Class.class);
        findShouldFails(ParameterType.STRING, Integer.class);
    }

    @Test
    public void testCorrectFind() {
        assertEquals(r.transform(ParameterType.NUMBER, 100), "100");
        assertEquals(r.transform(ParameterType.STRING, "asdfa"), "'asdfa'");
        assertEquals(r.transform(ParameterType.STRING, Lists.newArrayList("Mike", "Luke")), "'Mike','Luke'");
    }

    private void findShouldFails(ParameterType type, Class klazz) {
        try {
            r.transform(type, klazz);
            AssertJUnit.fail();
        } catch (EngineException e) {
        } catch (IllegalArgumentException a) {
        }
    }

}
