package com.eitasutilities.cs2.exceptions;

import com.eitasutilities.cs2.controller.dto.ErroCampo;
import com.eitasutilities.cs2.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UuidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleUuid(UuidException ex) {
        return ErroResposta.respostaPadrao(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();

        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .toList();

        return ErroResposta.entidadeNaoProcessada("Erro de validação!", listaErros);
    }

    @ExceptionHandler(CampoObrigatorioVazioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleCampoVazio(CampoObrigatorioVazioException ex) {
        return ErroResposta.respostaPadrao(ex.getMessage());
    }

    @ExceptionHandler(EnumInvalidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleEnumInvalida(EnumInvalidaException ex) {
        return ErroResposta.respostaPadrao(ex.getMessage());
    }

    @ExceptionHandler(NaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleNaoEncontrado(NaoEncontradoException ex) {
        return ErroResposta.naoEncontrado(ex.getMessage());
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
