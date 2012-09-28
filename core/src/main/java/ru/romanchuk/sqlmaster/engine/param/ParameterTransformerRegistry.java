package ru.romanchuk.sqlmaster.engine.param;

import org.apache.commons.lang.Validate;
import ru.romanchuk.sqlmaster.engine.EngineException;
import ru.romanchuk.sqlmaster.parser.tree.ParameterType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexey Romanchuk
 */
//TODO optimize synchronization. Use ReentrantLock
public class ParameterTransformerRegistry {
    private Collection<ParameterTransformer> transformers = Collections.synchronizedList(new ArrayList<ParameterTransformer>());
    private static ParameterTransformerRegistry defaultRegistry;

    static {
        defaultRegistry = new ParameterTransformerRegistry();
        defaultRegistry.register(new NumberParameterTransformer());
        defaultRegistry.register(new QuotedStringTransformer());
        defaultRegistry.register(new CollectionParameterTransformer(defaultRegistry));
    }

    public String transform(ParameterType parameterType, Object value) {
        Validate.notNull(parameterType);
        Validate.notNull(value);
        List<ParameterTransformer> result = new ArrayList<ParameterTransformer>();
        for (ParameterTransformer walker : transformers) {
            if (walker.canTransform(parameterType, value.getClass())) {
                result.add(walker);
            }
        }
        if (result.isEmpty()) {
            throw new EngineException("Unable to find transformer for type " + parameterType + " from class " + value.getClass());
        }
        if (result.size() > 1) {
            throw new EngineException("Multiple transformers for type " + parameterType + " from class " + value.getClass());
        }
        return result.get(0).transform(parameterType, value);
    }

    public void register(ParameterTransformer transformer) {
        transformers.add(transformer);
    }

    public static ParameterTransformerRegistry getDefault() {
        return defaultRegistry;
    }

}
