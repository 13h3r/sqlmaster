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
        defaultRegistry.register(new IntTransformer());
        defaultRegistry.register(new QuotedStringTransformer());
    }

    public ParameterTransformer findTransformer(ParameterType parameterType, Class klazz) {
        Validate.notNull(parameterType);
        Validate.notNull(klazz);
        List<ParameterTransformer> result = new ArrayList<ParameterTransformer>();
        for (ParameterTransformer walker : transformers) {
            if (walker.canTransform(parameterType, klazz)) {
                result.add(walker);
            }
        }
        if (result.isEmpty()) {
            throw new EngineException("Unable to find transformer for type " + parameterType + " from class " + klazz);
        }
        if (result.size() > 1) {
            throw new EngineException("Multiple transformers for type " + parameterType + " from class " + klazz);
        }
        return result.get(0);
    }

    public void register(ParameterTransformer transformer) {
        transformers.add(transformer);
    }

    public static ParameterTransformerRegistry getDefault() {
        return defaultRegistry;
    }

}
