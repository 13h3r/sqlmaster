package ru.romanchuk.sqlmaster;

/**
 * @author Alexey Romanchuk
 */
public interface Template {
    void assignValue(String name, Object value);

    void enable(String name);

    void enable(String name, boolean enable);

    String process();
}
