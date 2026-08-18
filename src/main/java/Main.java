import io.javalin.Javalin;
import io.javalin.json.JavalinGson;
import org.jessusthread.campusfeedapi.core.database.MongoConnection;
import org.jessusthread.campusfeedapi.core.exceptions.ConflictException;
import org.jessusthread.campusfeedapi.core.exceptions.NotFoundException;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.CreatePositionUseCase;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.DeletePositionUseCase;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.GetPositionsUseCase;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.UpdatePositionUseCase;
import org.jessusthread.campusfeedapi.modules.position.domain.PositionRepository;
import org.jessusthread.campusfeedapi.modules.position.domain.services.PositionValidator;
import org.jessusthread.campusfeedapi.modules.position.infrastructure.controllers.PositionController;
import org.jessusthread.campusfeedapi.modules.position.infrastructure.persistence.MongoPositionRepository;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Campus Feed API...");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n Shutting down application...");

            MongoConnection.closeConnection();
        }));

        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new JavalinGson());
        });

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

        MongoConnection.getDatabase();

        PositionRepository repository = new MongoPositionRepository();
        GetPositionsUseCase getPositionsUseCase = new GetPositionsUseCase(repository);
        CreatePositionUseCase createPositionUseCase = new CreatePositionUseCase(repository);
        PositionValidator positionValidator = new PositionValidator(repository);
        DeletePositionUseCase deletePositionUseCase = new DeletePositionUseCase(repository, positionValidator);
        UpdatePositionUseCase updatePositionUseCase = new UpdatePositionUseCase(repository, positionValidator);
        PositionController positionController = new PositionController(getPositionsUseCase, deletePositionUseCase, createPositionUseCase, updatePositionUseCase);

        positionController.registerRoutes(app);

        app.start(8080);
    }
}
