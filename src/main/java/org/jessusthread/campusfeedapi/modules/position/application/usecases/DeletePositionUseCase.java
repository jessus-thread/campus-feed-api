package org.jessusthread.campusfeedapi.modules.position.application.usecases;

import org.jessusthread.campusfeedapi.core.exceptions.NotFoundException;
import org.jessusthread.campusfeedapi.modules.position.domain.Position;
import org.jessusthread.campusfeedapi.modules.position.domain.PositionRepository;

public class DeletePositionUseCase {
    private final PositionRepository repository;

    public DeletePositionUseCase(PositionRepository repository) {
        this.repository = repository;
    }

    public void execute(String id) {
        Position existingPosition = this.repository.findById(id);

        if (existingPosition == null)
            throw new NotFoundException("The position with the ID (" + id + ") does not exist.");

        boolean wasEliminated = this.repository.deleteById(id);

        if (!wasEliminated)
            throw new IllegalStateException("The position could not be deleted due to an internal constraint.");
    }
}
