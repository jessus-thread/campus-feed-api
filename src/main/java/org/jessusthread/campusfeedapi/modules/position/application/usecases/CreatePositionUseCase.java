package org.jessusthread.campusfeedapi.modules.position.application.usecases;

import org.jessusthread.campusfeedapi.modules.position.application.dtos.CreatePositionDto;
import org.jessusthread.campusfeedapi.modules.position.domain.Position;
import org.jessusthread.campusfeedapi.modules.position.domain.PositionRepository;

public class CreatePositionUseCase {
    private final PositionRepository repository;

    public CreatePositionUseCase(PositionRepository repository) {
        this.repository = repository;
    }

    public Position execute(CreatePositionDto positionDto) {
        Position existingPosition = this.repository.findByName(positionDto.name());

        if (existingPosition == null) {
            Position position = new Position(positionDto.name());

            return this.repository.create(position);
        }

        if (existingPosition.isActive())
            throw new IllegalStateException("The position '" + positionDto.name() + "' already exists and is active.");

        return this.activatePosition(existingPosition);
    }

    private Position activatePosition(Position position) {
        return this.repository.activateById(position.getId());
    }
}
