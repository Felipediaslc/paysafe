package br.pucpr.bsi.wss.paysafe.pagamento.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProcessoPagamento {
    private final String codigoLoja;
    private final Cliente cliente;
    private final List<Produto> produtos;
    private final double valorTotal;
    private final String urlCallback;
}
