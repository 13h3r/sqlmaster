package ru.romanchuk.sqlmaster.parser.tree;

/**
 * @author Alexey Romanchuk
 */
public enum ParameterType {
    STRING("string"),
    INT("int"),
    ;
    private String templateName;

    private ParameterType(String templateName) {
        this.templateName = templateName;
    }

    public static ParameterType getByTemplateName(String name) {
        for(ParameterType type : values()) {
            if(type.templateName.equals(name)) {
                return type;
            }
        }
        return null;
    }
}
