package org.jessusthread.campusfeedapi.core.config;

import io.javalin.Javalin;
import org.jessusthread.campusfeedapi.core.exceptions.ConflictException;
import org.jessusthread.campusfeedapi.core.exceptions.NotFoundException;

import java.util.Map;

public class GlobalExceptionHandler {
    public static void register(Javalin app) {
        app.exception(NotFoundException.class, (e, ctx) -> {
            ctx.status(404);
            ctx.json(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        });

        app.exception(ConflictException.class, (e, ctx) -> {
            ctx.status(409);
            ctx.json(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        });

        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500);
            ctx.json(Map.of(
                    "success", false,
                    "message", "An unexpected error occurred."
            ));
        });
    }
}
