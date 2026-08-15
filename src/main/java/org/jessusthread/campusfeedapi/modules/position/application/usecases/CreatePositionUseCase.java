package org.jessusthread.campusfeedapi.modules.position.application.usecases;

import org.jessusthread.campusfeedapi.modules.position.application.dtos.CreatePositionDto;
import org.jessusthread.campusfeedapi.modules.position.domain.Position;
import org.jessusthread.campusfeedapi.modules.position.domain.PositionRepository;

public class CreatePositionUseCase {
    private final PositionRepository repository;

    public CreatePositionUseCase(PositionRepository repository) {
        this.repository = repository;
    }

//    public Position create(CreatePositionDto position) {
//        Position existingPosition = this.repository.findByName(position.name());
//
//
//    }

//    public Position activatePosition(Position position) {
//
//    }
}
