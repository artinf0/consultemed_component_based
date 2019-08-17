package br.com.consultemed.exceptions;

public class HorarioAgendamenteException extends RuntimeException {

    public HorarioAgendamenteException() {
    }

    public HorarioAgendamenteException(String message) {
        super(message);
    }
}
