package com.miniautorizador.schema;

import com.miniautorizador.model.Cartao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
public class CartaoResponse {

    @Schema(description = "Senha do cartão")
    private String senha;

    @Schema(description = "Número do cartão")
    private String numeroCartao;

    public CartaoResponse(Cartao cartao) {
        this.senha = cartao.getSenha();
        this.numeroCartao = cartao.getNumeroCartao();
    }

}
