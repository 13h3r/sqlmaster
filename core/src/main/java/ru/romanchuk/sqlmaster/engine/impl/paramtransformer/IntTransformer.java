package ru.romanchuk.sqlmaster.engine.impl.paramtransformer;

import ru.romanchuk.sqlmaster.parser.tree.ParameterType;

/**
 * @author Alexey Romanchuk
 */
public class IntTransformer implements ParameterTransformer {
    @Override
    public boolean canTransform(ParameterType parameterType, Class klazz) {
        if (parameterType == ParameterType.INT && Number.class.isAssignableFrom(klazz)) {
            return true;
        }
        return false;
    }

    @Override
    public String transform(Object value) {
        return "" + ((Number) value).intValue();
    }
}
