package br.pucpr.bsi.wss.paysafe.pagamento.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Endereco {
    private final String cep;
    private final String logradouro;
    private final String numero;
    private final String complemento;
    private final String cidade;
    private final String estado;
}
