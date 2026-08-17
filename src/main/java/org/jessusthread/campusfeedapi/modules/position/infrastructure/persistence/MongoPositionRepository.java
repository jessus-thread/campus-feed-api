package org.jessusthread.campusfeedapi.modules.position.infrastructure.persistence;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jessusthread.campusfeedapi.core.database.MongoConnection;
import org.jessusthread.campusfeedapi.core.domain.MongoNameCollection;
import org.jessusthread.campusfeedapi.modules.position.domain.Position;
import org.jessusthread.campusfeedapi.modules.position.domain.PositionRepository;

import java.util.ArrayList;
import java.util.List;

public class MongoPositionRepository implements PositionRepository {
    private final MongoCollection<Document> collection;

    public MongoPositionRepository() {
        MongoDatabase database = MongoConnection.getDatabase();

        this.collection = database.getCollection(MongoNameCollection.POSITION.getCollectionName());
    }

    public Position create(Position position) {
        Document newPosition = new Document()
                .append("name", position.getName())
                .append("active", true);

        collection.insertOne(newPosition);

        return this.mapPosition(newPosition);
    }

    public Position findById(String id) {
        ObjectId objectId = new ObjectId(id);
        Document position = collection.find(Filters.and(
                Filters.eq("_id", objectId),
                Filters.eq("active", true)
        )).first();

        if (position == null) return null;

        return this.mapPosition(position);
    }

    public Position findByName(String name) {
        Document position = collection.find(Filters.eq("name", name)).first();

        if (position == null) return null;

        return this.mapPosition(position);
    }

    public Position update(Position position) {
        ObjectId objectId = new ObjectId(position.getId());

        UpdateResult updateResult = collection.updateOne(
                Filters.eq("_id", objectId),
                Updates.set("name", position.getName())
        );

        if (updateResult.getModifiedCount() == 0) return null;

        return this.findById(position.getId());
    }

    public Position activateById(String id) {
        ObjectId objectId = new ObjectId(id);

        UpdateResult updateResult = collection.updateOne(
                Filters.eq("_id", objectId),
                Updates.set("activate", true)
        );

        if (updateResult.getModifiedCount() == 0) return null;

        return this.findById(id);
    }

    public boolean deleteById(String id) {
        ObjectId objectId = new ObjectId(id);

        UpdateResult updateResult = collection.updateOne(
                Filters.eq("_id", objectId),
                Updates.set("active", false)
        );

        return updateResult.getModifiedCount() > 0;
    }

    public List<Position> getPositions() {
        List<Position> positions = new ArrayList<>();
        FindIterable<Document> cursor = collection.find(Filters.eq("active", true));

        for (Document document : cursor) {
            positions.add(this.mapPosition(document));
        }

        return positions;
    }

    private Position mapPosition(Document document) {
        if (document == null)
            throw new IllegalArgumentException("Position cannot be null");

        ObjectId objectId = document.getObjectId("_id");
        String id = objectId.toHexString();
        String name = document.getString("name");
        boolean active = document.getBoolean("active");

        return new Position(id, name, active);
    }
}
