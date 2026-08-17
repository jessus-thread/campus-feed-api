package org.jessusthread.campusfeedapi.modules.position.infrastructure.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.GetPositionsUseCase;
import org.jessusthread.campusfeedapi.modules.position.domain.Position;

import java.util.List;

public class PositionController {
    private final GetPositionsUseCase getPositionsUseCase;

    public PositionController(GetPositionsUseCase getPositionsUseCase) {
        this.getPositionsUseCase = getPositionsUseCase;
    }

    public void registerRoutes(Javalin app) {
        app.get("/positions", this::getPositions);
    }

    private void getPositions(Context ctx) {
        List<Position> positions = this.getPositionsUseCase.execute();

        ctx.json(positions);
    }
}
