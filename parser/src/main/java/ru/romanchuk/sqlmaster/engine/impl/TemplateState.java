package ru.romanchuk.sqlmaster.engine.impl;

import ru.romanchuk.sqlmaster.engine.EngineException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexey Romanchuk
 */
public class TemplateState {
    private Map<String, Object> elementsValue = new HashMap<String, Object>();

    public void assignValue(String name, Object value) {
        if(elementsValue.containsKey(name)) {
            throw new EngineException("Key " + name + " already set");
        }
        elementsValue.put(name, value);
    }

}
