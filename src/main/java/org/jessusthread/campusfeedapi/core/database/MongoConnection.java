package org.jessusthread.campusfeedapi.core.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import org.jessusthread.campusfeedapi.core.exceptions.MissingEnvironmentVariableException;

/*
    Private constructor to prevent anyone from instantiating
    this class using "new".

    The performance issue (why you shouldn't close it all the time)

    Even if you could use try-with-resources, in a backend API we
    don't want to close the database after every operation

    The MongoDB client (MongoClient) internally manages  a
    "connection pool". Opening a database connection is costly
    (in terms of CPU time and network resources). If you close
    it at the end of a `try` block, the next time someone
    request their post feed, your serve will have to renegotiate
    the entire connection with MongoDB.

    The golden rule in backend development is: the database
    connection is established when the server starts up and
    is terminated only when the server shuts down
*/

public class MongoConnection {
    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;

    private MongoConnection() {}

    public static MongoDatabase getDatabase() {
        if (database == null) {
            try {
                Dotenv dotenv = Dotenv.load();
                String uri = dotenv.get("MONGO_URI");
                String databaseName = dotenv.get("MONGO_DB_NAME");

                if (uri == null || uri.trim().isEmpty())
                    throw new MissingEnvironmentVariableException("uri");

                if (databaseName == null || databaseName.trim().isEmpty())
                    throw  new MissingEnvironmentVariableException("databaseName");

                // Create connection to the cluster
                mongoClient = MongoClients.create(uri);
                database = mongoClient.getDatabase(databaseName);

                System.out.println("Successfully connected to the database: " + databaseName);
            } catch (MissingEnvironmentVariableException e) {
                System.out.println("Fatal error while attempting to read the .env files: " + e.getMessage());

                throw e;
            } catch (Exception e) {
                System.out.println("Fatal error connecting to MongoDB: " + e.getMessage());

                throw e;
            }
        }

        return database;
    }

    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();

            System.out.println("MongoDB connection closed gracefully.");
        }
    }
}
