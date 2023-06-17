package com.miniautorizador.service;

import com.miniautorizador.exception.CartaoDuplicadoException;
import com.miniautorizador.exception.CartaoInexistenteSaldoException;
import com.miniautorizador.exception.CartaoInvalidoException;
import com.miniautorizador.domain.Cartao;
import com.miniautorizador.repository.CartaoRepository;
import com.miniautorizador.dto.retorno.CartaoResponse;
import com.miniautorizador.dto.entrada.CriarCartao;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@Log4j2
@Transactional
@NoArgsConstructor
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public CartaoResponse criarCartao(CriarCartao cartao) {
        log.info("Criando um novo cartão.");

        if(Objects.isNull(cartao.getNumeroCartao())) {
            cartao.setNumeroCartao(generateRandomNumber());
        }

        verificaSeCartaoTemApenasNumeros(cartao.getNumeroCartao());

        verificaSeCartaoDuplicado(cartao);

        Cartao novoCartao = new Cartao(cartao);
        cartaoRepository.save(novoCartao);

        log.info("Cartão {} criado com sucesso.", cartao.getNumeroCartao());
        return new CartaoResponse(novoCartao);
    }

//    @Cacheable("cartoes")
    public List<CartaoResponse> listarCartoes() {
        log.info("Listando todos cartões");

        List<Cartao> cartoes = cartaoRepository.findAll();

        List<CartaoResponse> cartaoResponse = new ArrayList<>();
        cartoes.forEach(cartao -> {
            cartaoResponse.add(new CartaoResponse(cartao));
        });

        return cartaoResponse;
    }

    public BigDecimal obterSaldoCartao(String numeroCartao) {
        log.info("Saldo do Cartão");

        return cartaoRepository.findByNumeroCartao(numeroCartao)
                .map(Cartao::getSaldo).orElseThrow(CartaoInexistenteSaldoException::new);
    }

    private void verificaSeCartaoDuplicado(CriarCartao cartao) {
        cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao())
                .ifPresent(c -> {
                    log.error("O cartão {} já existe.", cartao.getNumeroCartao());
                    throw new CartaoDuplicadoException(cartao.getNumeroCartao(), cartao.getSenha());
                });
    }

    private void verificaSeCartaoTemApenasNumeros(String numeroCartao) {
        if(!numeroCartao.matches("^\\d+$"))
            throw new CartaoInvalidoException();
    }

    private static String generateRandomNumber() {
        Random rand = new Random();
        long num = (long)(rand.nextDouble() * 10000000000000000L); // generate a 16-digit number
        return Long.toString(num);
    }
}
