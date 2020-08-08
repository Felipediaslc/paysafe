package br.pucpr.bsi.wss.paysafe.pagamento.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Produto {
    private final String descricao;
    private final int quantidade;
    private final double valorUnitario;
    private final double valor;
}
