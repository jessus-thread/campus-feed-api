package org.jessusthread.campusfeedapi.modules.position.domain;

import java.util.List;

public interface PositionRepository {
    Position create(Position position);

    Position findById(String id);

    Position findByName(String name);

    List<Position> getPositions();

    Position update(Position position, String id);

    boolean deleteById(String id);
}
