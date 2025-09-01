package com.eitasutilities.cs2.controller.utilitario;

import com.eitasutilities.cs2.controller.dto.UtilitarioDTO;
import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.services.utilitario.EditarUtilitarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/utilitarios")
@Tag(name = "Editar Utilitário", description = "Operação relacionada a edição de utilitários")
public class EditarUtilitarioController {

    private final EditarUtilitarioService service;

    @Operation(
            summary = "Edita um utilitário",
            description = "Recebe os dados do utilitário e edita um registro.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Utilitário editado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "422", description = "Há dados obrigatórios não preenchidos")
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<Object> editar(@PathVariable("id") String id, @RequestBody @Valid UtilitarioDTO utilitario) {
        Utilitario output = service.editar(id, utilitario);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
