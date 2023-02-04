package br.com.attornatus_v2.infra.exception;

public class PessoaNaoEncontradaException extends RuntimeException {

        public PessoaNaoEncontradaException(String message) {
            super(message);
        }

}
