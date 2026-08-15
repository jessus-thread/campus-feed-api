import org.jessusthread.campusfeedapi.core.database.MongoConnection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Campus Feed API...");

        // We register the "Shutdown Hook"
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n Shutting down application...");

            MongoConnection.closeConnection();
        }));

        // TODO: Falta añadir comentarios en los apuntes del curso

        MongoConnection.getDatabase();
    }
}
