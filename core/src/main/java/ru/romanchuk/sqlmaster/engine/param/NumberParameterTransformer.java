package ru.romanchuk.sqlmaster.engine.param;

import org.apache.commons.lang.Validate;
import ru.romanchuk.sqlmaster.parser.tree.ParameterType;

/**
 * @author Alexey Romanchuk
 */
public class NumberParameterTransformer implements ParameterTransformer {
    @Override
    public boolean canTransform(ParameterType parameterType, Class klazz) {
        if (parameterType == ParameterType.NUMBER && java.lang.Number.class.isAssignableFrom(klazz)) {
            return true;
        }
        return false;
    }

    @Override
    public String transform(ParameterType parameterType, Object value) {
        Validate.notNull(value);
        return "" + value.toString();
    }
}
