package br.com.consultemed.exceptions;

public class EmailCadastradoException extends RuntimeException {

    public EmailCadastradoException() {
    }

    public EmailCadastradoException(String message) {
        super(message);
    }
}
