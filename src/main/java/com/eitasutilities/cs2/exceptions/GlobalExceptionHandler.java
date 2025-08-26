package com.eitasutilities.cs2.exceptions;

import com.eitasutilities.cs2.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UuidException.class)
    public ResponseEntity<ErroResposta> handleUuid(UuidException ex) {
        return ResponseEntity
                .badRequest()
                .body(ErroResposta.respostaPadrao(ex.getMessage()));
    }

    @ExceptionHandler(CampoObrigatorioVazioException.class)
    public ResponseEntity<ErroResposta> handleCampoVazio(CampoObrigatorioVazioException ex) {
        return ResponseEntity
                .badRequest()
                .body(ErroResposta.respostaPadrao(ex.getMessage()));
    }

    @ExceptionHandler(EnumInvalidaException.class)
    public ResponseEntity<ErroResposta> handleEnumInvalida(EnumInvalidaException ex) {
        return ResponseEntity
                .badRequest()
                .body(ErroResposta.respostaPadrao(ex.getMessage()));
    }

    @ExceptionHandler(LinkInvalidoException.class)
    public ResponseEntity<ErroResposta> handleLinkInvalido(LinkInvalidoException ex) {
        boolean ehErroConflito = Objects.equals(ex.getMessage(), "Já existe um vídeo vinculado ao link informado!");
        HttpStatus statusErro = ehErroConflito ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;
        ErroResposta erroResposta = ehErroConflito ? ErroResposta.conflito(ex.getMessage()) : ErroResposta.respostaPadrao(ex.getMessage());

        return ResponseEntity
                .status(statusErro)
                .body(erroResposta);
    }
}
