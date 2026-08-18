package org.jessusthread.campusfeedapi.modules.position.infrastructure.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.CreatePositionUseCase;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.DeletePositionUseCase;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.GetPositionsUseCase;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.UpdatePositionUseCase;
import org.jessusthread.campusfeedapi.modules.position.domain.Position;

import java.util.List;

@AllArgsConstructor
public class PositionController {
    private final GetPositionsUseCase getPositionsUseCase;
    private final DeletePositionUseCase deletePositionUseCase;
    private final CreatePositionUseCase createPositionUseCase;
    private final UpdatePositionUseCase updatePositionUseCase;

    public void registerRoutes(Javalin app) {
        app.get("/positions", this::getPositions);
        app.delete("/positions/{id}", this::deletePosition);
    }

    private void getPositions(Context ctx) {
        List<Position> positions = this.getPositionsUseCase.execute();

        ctx.json(positions);
    }

    private void deletePosition(Context ctx) {
        String id = ctx.pathParam("id");

        this.deletePositionUseCase.execute(id);

        ctx.status(204);
    }
}
