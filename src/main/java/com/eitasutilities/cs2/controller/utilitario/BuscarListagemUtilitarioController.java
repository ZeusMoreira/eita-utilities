package com.eitasutilities.cs2.controller.utilitario;

import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.services.utilitario.BuscarUtilitarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/utilitarios")
@Tag(name = "Buscar Utilitários", description = "Operação relacionada a listagem de utilitários")
public class BuscarListagemUtilitarioController {

    private final BuscarUtilitarioService service;

    @Operation(
            summary = "Buscar uma lista de utilitários",
            description = "Busca uma lista de utilitários.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Utilitários buscados com sucesso"),
            }
    )
    @GetMapping
    public ResponseEntity<Object> buscar(
            @RequestParam(value = "lado", required = false) String lado,
            @RequestParam(value = "mapa", required = false) String mapa,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "tipo", required = false) String tipo,
            @RequestParam(value = "dificuldade", required = false) String dificuldade
    ) {
        List<Utilitario> output = service.buscar(lado, mapa, titulo, tipo, dificuldade);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
