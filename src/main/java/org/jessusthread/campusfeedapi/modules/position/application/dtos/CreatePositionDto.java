package org.jessusthread.campusfeedapi.modules.position.application.dtos;

/*
    What exactly is a Java record?

    Think of a record as a read-only class with superpowers, designed
    specifically to serve as a "data carrier" (DTO).

    Before records existed (officially introduced in Java 16), creating
    an immutable DTO required writing a lot of boilerplate code or relying
    heavily on Lombok's `@Value` or `@Data` annotations.

    Under the hood, the Java compiler automatically generates all of this for you:

    1. Immutable attributes: It creates `private final String name;`.
    2. Constructor: It creates a constructor that accepts the `name`.
    3. Getters: It creates a method to read it. (Important note: it does
        not use the `get` prefix; the method is named exactly like the
        variable—i.e., `dto.name()` instead of `dto.getName()`).
    4. Utility methods: It automatically generates `toString()`, `equals()`,
        and `hashCode()` so you can easily print and compare your objects.

    What is it for? It is the definitive standard tool in modern Java for
    safely transporting data between layers without side effects.
*/
public record CreatePositionDto(
    String name
) {}
