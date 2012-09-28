package ru.romanchuk.sqlmaster.engine.param;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import ru.romanchuk.sqlmaster.parser.tree.ParameterType;

import java.util.Collection;

/**
 * @author Alexey Romanchuk
 */
public class CollectionParameterTransformer implements ParameterTransformer {

    private ParameterTransformerRegistry registry;

    public CollectionParameterTransformer(ParameterTransformerRegistry registry) {
        this.registry = registry;
    }

    @Override
    public boolean canTransform(ParameterType parameterType, Class klazz) {
        if (Collection.class.isAssignableFrom(klazz)) {
            return true;
        }
        return false;
    }

    @Override
    public String transform(final ParameterType parameterType, Object value) {
        Validate.notNull(value);

        Collection values = Collections2.transform((Collection) value, new Function() {

            @Override
            public Object apply(Object item) {
                return registry.transform(parameterType, item);
            }
        });
        return StringUtils.join(values, ",");
    }
}
