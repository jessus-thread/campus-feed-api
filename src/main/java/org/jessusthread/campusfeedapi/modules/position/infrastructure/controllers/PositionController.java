package org.jessusthread.campusfeedapi.modules.position.infrastructure.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import org.jessusthread.campusfeedapi.core.domain.RouteNameController;
import org.jessusthread.campusfeedapi.modules.position.application.dtos.CreatePositionDto;
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
        final String ROUTE = RouteNameController.POSITION.getRouteName();
        final String ROUTE_WITH_ID = ROUTE + "/{id}";

        app.get(ROUTE, this::getPositions);
        app.delete(ROUTE_WITH_ID, this::deletePosition);
        app.post(ROUTE, this::createPosition);
        app.patch(ROUTE_WITH_ID, this::updatePosition);
    }

    private void updatePosition(Context ctx) {
        String id = ctx.pathParam("id");
        CreatePositionDto body = ctx.bodyAsClass(CreatePositionDto.class);
        Position positionUpdated = this.updatePositionUseCase.execute(id, body);

        ctx.status(200).json(positionUpdated);
    }

    private void createPosition(Context ctx) {
        CreatePositionDto body = ctx.bodyAsClass(CreatePositionDto.class);
        Position newPosition = this.createPositionUseCase.execute(body);

        ctx.status(201).json(newPosition);
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
