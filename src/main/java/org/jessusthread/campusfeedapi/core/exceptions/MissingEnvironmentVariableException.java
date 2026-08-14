package org.jessusthread.campusfeedapi.core.exceptions;

public class MissingEnvironmentVariableException extends RuntimeException {
    public MissingEnvironmentVariableException(String variableName) {
        super("Critical failure: Environment variable not found '" + variableName + "' in the .env file.");
    }
}
