package org.jessusthread.campusfeedapi.modules.position.application.usecases;

import org.jessusthread.campusfeedapi.modules.position.application.dtos.CreatePositionDto;
import org.jessusthread.campusfeedapi.modules.position.domain.Position;
import org.jessusthread.campusfeedapi.modules.position.domain.PositionRepository;
import org.jessusthread.campusfeedapi.modules.position.domain.services.PositionValidator;

public class UpdatePositionUseCase {
    private final PositionRepository repository;
    private final PositionValidator validator;

    public UpdatePositionUseCase(PositionRepository repository, PositionValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Position execute(String id, CreatePositionDto positionDto) {
        Position positionExisting = this.validator.requireExists(id);
        boolean isSameName = positionExisting.getName().equals(positionDto.name());

        if (isSameName) return positionExisting;

        this.validator.requireUniqueName(positionDto.name(), id);

        Position positionToUpdate = new Position(positionDto.name(), id);
        Position positionUpdated = this.repository.update(positionToUpdate);

        if (positionUpdated == null)
            throw new IllegalStateException("The position could not be updated due to an internal constraint.");

        return positionUpdated;
    }
}
