package ru.romanchuk.sqlmaster;

/**
 * SQL template model
 *
 * Use #assignValue and #enable to setup template and #process to render
 *
 * @author Alexey Romanchuk
 */
public interface Template {
    /**
     * Assign value to template parameter
     *
     * Value will be converted and rendered based on Engine implementation
     *
     * @param name name of parameter
     * @param value value to set
     */
    void assignValue(String name, Object value);

    /**
     * Directly enables embedded text in template
     * @param name name of embedded text to enable
     */
    void enable(String name);

    /**
     * Directly enables/disables embedded text in template
     * @param name name of embedded text
     * @param enable true to enable and false to disable
     */
    void enable(String name, boolean enable);

    /**
     * Renders template
     */
    String process();
}
