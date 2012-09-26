package ru.romanchuk.sqlmaster.engine.impl.paramtransformer;

import ru.romanchuk.sqlmaster.parser.tree.ParameterType;

/**
 * @author Alexey Romanchuk
 */
public interface ParameterTransformer {
    boolean canTransform(ParameterType parameterType, Class klazz);
    String transform(Object value);
}
