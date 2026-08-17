package org.jessusthread.campusfeedapi.modules.position.domain.services;

import org.jessusthread.campusfeedapi.core.exceptions.ConflictException;
import org.jessusthread.campusfeedapi.core.exceptions.NotFoundException;
import org.jessusthread.campusfeedapi.modules.position.domain.Position;
import org.jessusthread.campusfeedapi.modules.position.domain.PositionRepository;

public class PositionValidator {
    private final PositionRepository repository;

    public PositionValidator(PositionRepository repository) {
        this.repository = repository;
    }

    public Position requireExists(String id) {
        Position position = this.repository.findById(id);

        if (position == null)
            throw new NotFoundException("The position with the ID (" + id + ") does not exist.");

        return position;
    }

    public void requireUniqueName(String name, String excludeId) {
        Position position = this.repository.findByName(name);

        if (position != null && !position.getId().equals(excludeId)) {
            throw new ConflictException(
                    "The position '" + name + "' already exists and is " + (position.isActive() ? "active." : "inactive.")
            );
        }
    }
}
