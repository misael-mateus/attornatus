package br.com.attornatus_v2.infra.exception;

public class EnderecoNaoEncontradoException extends RuntimeException {
    public EnderecoNaoEncontradoException(String message) {
        super(message);
    }
}
