package org.jessusthread.campusfeedapi.modules.position.application.usecases;

import org.jessusthread.campusfeedapi.modules.position.domain.Position;
import org.jessusthread.campusfeedapi.modules.position.domain.PositionRepository;

import java.util.List;

public class GetPositionsUseCase {
    private final PositionRepository repository;

    public GetPositionsUseCase(PositionRepository repository) {
        this.repository = repository;
    }

    public List<Position> execute() {
        return this.repository.getPositions();
    }
}
