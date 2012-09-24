package ru.romanchuk.sqlmaster.engine.impl;

import ru.romanchuk.sqlmaster.engine.EngineException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexey Romanchuk
 */
public class TemplateState {
    private Map<String, Object> parameters = new HashMap<String, Object>();
    private Set<String> embedded = new HashSet<String>();

    public TemplateState() {
    }

    public TemplateState(TemplateState state) {
        parameters.putAll(state.parameters);
        embedded.addAll(state.embedded);
    }

    public void assignValue(String name, Object value) {
        if(parameters.containsKey(name)) {
            throw new EngineException("Key " + name + " already set");
        }
        parameters.put(name, value);
    }

    public void embed(String name) {
        embedded.add(name);
    }

    public boolean isEmbed(String name) {
        return embedded.contains(name);
    }

    public Object getAssignedValue(String key) {
        return parameters.get(key);
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public Set<String> getEmbedded() {
        return embedded;
    }
}
