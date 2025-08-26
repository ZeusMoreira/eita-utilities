package com.eitasutilities.cs2.controller.utilitario;

import com.eitasutilities.cs2.controller.dto.UtilitarioDTO;
import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.services.utilitario.CriarUtilitarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utilitarios")
<<<<<<< HEAD
@Tag(name = "Criar Utilitário", description = "Operação relacionada a criação de utilitários")
=======
>>>>>>> develop
public class CriarUtilitarioController {
    private final CriarUtilitarioService service;

    public CriarUtilitarioController(CriarUtilitarioService service) {
        this.service = service;
    }

    @Operation(
            summary = "Cria um novo utilitário",
            description = "Recebe os dados do utilitário e cria um novo registro.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Utilitário criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody UtilitarioDTO utilitario) {
        Utilitario output = service.salvar(utilitario);
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

}
