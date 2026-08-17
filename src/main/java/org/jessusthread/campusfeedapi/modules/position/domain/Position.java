package org.jessusthread.campusfeedapi.modules.position.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Position {
    private String id;
    private String name;
    private boolean active;

    public Position(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Position(String name) {
        this.name = name;
    }
}
