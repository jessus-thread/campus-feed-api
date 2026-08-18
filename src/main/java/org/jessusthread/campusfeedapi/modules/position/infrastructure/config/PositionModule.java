package org.jessusthread.campusfeedapi.modules.position.infrastructure.config;

import io.javalin.Javalin;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.CreatePositionUseCase;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.DeletePositionUseCase;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.GetPositionsUseCase;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.UpdatePositionUseCase;
import org.jessusthread.campusfeedapi.modules.position.domain.PositionRepository;
import org.jessusthread.campusfeedapi.modules.position.domain.services.PositionValidator;
import org.jessusthread.campusfeedapi.modules.position.infrastructure.controllers.PositionController;
import org.jessusthread.campusfeedapi.modules.position.infrastructure.persistence.MongoPositionRepository;

public class PositionModule {
    public static void register(Javalin app) {
        PositionRepository repository = new MongoPositionRepository();
        PositionValidator validator = new PositionValidator(repository);

        GetPositionsUseCase getPositionsUseCase = new GetPositionsUseCase(repository);
        CreatePositionUseCase createPositionUseCase = new CreatePositionUseCase(repository);
        DeletePositionUseCase deletePositionUseCase = new DeletePositionUseCase(repository, validator);
        UpdatePositionUseCase updatePositionUseCase = new UpdatePositionUseCase(repository, validator);

        PositionController positionController = new PositionController(
                getPositionsUseCase,
                deletePositionUseCase,
                createPositionUseCase,
                updatePositionUseCase
        );

        positionController.registerRoutes(app);
    }
}
