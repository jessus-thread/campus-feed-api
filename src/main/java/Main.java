import io.javalin.Javalin;
import io.javalin.json.JavalinGson;
import org.jessusthread.campusfeedapi.core.database.MongoConnection;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.GetPositionsUseCase;
import org.jessusthread.campusfeedapi.modules.position.domain.PositionRepository;
import org.jessusthread.campusfeedapi.modules.position.infrastructure.controllers.PositionController;
import org.jessusthread.campusfeedapi.modules.position.infrastructure.persistence.MongoPositionRepository;

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

        MongoConnection.getDatabase();

        PositionRepository repository = new MongoPositionRepository();
        GetPositionsUseCase getPositionsUseCase = new GetPositionsUseCase(repository);
        PositionController positionController = new PositionController(getPositionsUseCase);

        positionController.registerRoutes(app);

        app.start(8080);
    }
}
