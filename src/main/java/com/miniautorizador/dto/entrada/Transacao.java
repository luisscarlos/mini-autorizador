package com.miniautorizador.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @NotNull
    @Size(min = 16, message = "O cartão deve ter 16 números")
    @Size(max = 16, message = "O cartão deve ter 16 números")
    @Schema(description = "Número do cartão", example = "6549873025634501", required = true)
    private String numeroCartao;

    @NotNull
    @NotBlank
    @Schema(description = "Senha do cartão", example = "1234", required = true)
    private String senhaCartao;

    @NotNull
    @Schema(description = "Valor da transação", example = "113.34")
    private BigDecimal valor;
}
