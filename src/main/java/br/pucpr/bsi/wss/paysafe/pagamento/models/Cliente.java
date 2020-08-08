package br.pucpr.bsi.wss.paysafe.pagamento.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cliente {
    private final String nome;
    private final String cpf;
    private final Endereco endereco;
}
