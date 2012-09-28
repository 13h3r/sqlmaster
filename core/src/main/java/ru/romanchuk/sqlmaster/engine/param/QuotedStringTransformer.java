package ru.romanchuk.sqlmaster.engine.param;

import org.apache.commons.lang.Validate;
import ru.romanchuk.sqlmaster.parser.tree.ParameterType;

/**
 * @author Alexey Romanchuk
 */
public class QuotedStringTransformer implements ParameterTransformer {
    @Override
    public boolean canTransform(ParameterType parameterType, Class klazz) {
        if (parameterType == ParameterType.STRING && klazz.equals(String.class)) {
            return true;
        }
        return false;
    }

    @Override
    public String transform(ParameterType parameterType, Object value) {
        Validate.notNull(value);
        return "'" + value.toString() + "'";
    }


}
