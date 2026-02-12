package io.github.pedro.msavaliadorcredito.application.exception;

import lombok.Getter;

public class ErroComunicacaoMicroserviceException extends Exception{

    @Getter
    private final Integer status;

    //recebe a mensagem de erro e o status http
    public ErroComunicacaoMicroserviceException(String msg, Integer status) {
        super(msg);
        this.status = status;
    }
}
