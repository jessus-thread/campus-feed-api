package org.jessusthread.campusfeedapi.modules.position.application.usecases;

import org.jessusthread.campusfeedapi.modules.position.domain.PositionRepository;
import org.jessusthread.campusfeedapi.modules.position.domain.services.PositionValidator;

public class DeletePositionUseCase {
    private final PositionRepository repository;
    private final PositionValidator validator;

    public DeletePositionUseCase(PositionRepository repository, PositionValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public void execute(String id) {
        this.validator.requireExists(id);

        boolean wasEliminated = this.repository.deleteById(id);

        if (!wasEliminated)
            throw new IllegalStateException("The position could not be deleted due to an internal constraint.");
    }
}
