package ru.romanchuk.sqlmaster.engine.param;

import ru.romanchuk.sqlmaster.parser.tree.ParameterType;

/**
 * @author Alexey Romanchuk
 */
public interface ParameterTransformer {
    boolean canTransform(ParameterType parameterType, Class klazz);
    String transform(ParameterType parameterType, Object value);
}
