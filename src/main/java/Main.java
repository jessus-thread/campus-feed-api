import org.jessusthread.campusfeedapi.core.database.MongoConnection;
import org.jessusthread.campusfeedapi.modules.position.application.dtos.CreatePositionDto;
import org.jessusthread.campusfeedapi.modules.position.application.usecases.CreatePositionUseCase;
import org.jessusthread.campusfeedapi.modules.position.domain.PositionRepository;
import org.jessusthread.campusfeedapi.modules.position.infrastructure.persistence.MongoPositionRepository;

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
        PositionRepository repository = new MongoPositionRepository();
        CreatePositionUseCase createPositionUseCase = new CreatePositionUseCase(repository);
        CreatePositionDto dto = new CreatePositionDto("Jefe de carrera");

        createPositionUseCase.execute(dto);
    }
}
