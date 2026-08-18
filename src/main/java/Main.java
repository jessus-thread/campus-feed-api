import io.javalin.Javalin;
import io.javalin.json.JavalinGson;
import org.jessusthread.campusfeedapi.core.config.GlobalExceptionHandler;
import org.jessusthread.campusfeedapi.core.database.MongoConnection;
import org.jessusthread.campusfeedapi.modules.position.infrastructure.config.PositionModule;

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
        GlobalExceptionHandler.register(app);
        PositionModule.register(app);

        app.start(8080);
    }
}
