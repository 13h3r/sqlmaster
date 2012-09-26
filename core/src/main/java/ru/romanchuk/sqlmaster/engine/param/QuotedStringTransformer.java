package ru.romanchuk.sqlmaster.engine.param;

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
    public String transform(Object value) {
        return "'" + value.toString() + "'";
    }


}
