package ru.romanchuk.sqlmaster;

/**
 * @author Alexey Romanchuk
 */
public class Example {
    public static void main(String[] args) {
        Template t = SimpleEngine.create(
                "select * from client where name = /**string name(*/'John'/**)*/");
        t.assignValue("name", "Mary");
        System.out.printf(t.process());
    }
}
