package com.miniautorizador.contract;

import com.miniautorizador.dto.retorno.CartaoResponse;
import com.miniautorizador.dto.entrada.CriarCartao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Tag(name = "1. Cartão", description = "Gerenciamento de cartões.")
@RequestMapping(value = "/cartoes")
public interface CartaoContract {

    @Operation(summary = "Cria um novo cartão")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = CartaoResponse.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = CartaoResponse.class))),
    })
    @PostMapping
    ResponseEntity<CartaoResponse> criarCartao(@RequestBody @Valid CriarCartao cartao);

    @Operation(summary = "Consultar saldo do cartão")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping(value = "/{numeroCartao}")
    ResponseEntity<BigDecimal> obterSaldoCartao(@PathVariable String numeroCartao);

    @Operation(summary = "Listar todos cartões")
    @GetMapping()
    ResponseEntity<List<CartaoResponse>> listarCartoes();

}
