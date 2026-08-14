import com.mongodb.client.MongoDatabase;
import org.jessusthread.campusfeedapi.core.database.MongoConnection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Campus Feed API...");

        // TODO: Falta añadir comentarios en los apuntes del curso

        MongoDatabase mongoDatabase = MongoConnection.getDatabase();

        // We register the "Shutdown Hook"
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n Shutting down application...");

            MongoConnection.closeConnection();
        }));
    }
}
