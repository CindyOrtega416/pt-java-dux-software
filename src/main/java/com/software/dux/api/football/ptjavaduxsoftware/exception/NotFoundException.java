package com.software.dux.api.football.ptjavaduxsoftware.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException{
    private final int codigo;

    public NotFoundException(String mensaje, int codigo) {
        super(mensaje);
        this.codigo = codigo;
    }

}
