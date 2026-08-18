package org.jessusthread.campusfeedapi.core.domain;

import lombok.Getter;

@Getter
public enum RouteNameController {
    POSITION("/position");

    private final String routeName;

    RouteNameController(String routeName) {
        this.routeName = routeName;
    }
}
