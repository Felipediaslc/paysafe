package br.pucpr.bsi.wss.paysafe.pagamento.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LiberacaoPagamento {
    private final boolean autorizado;
    private final String codigoPagamento;
    private final String linkRedirecionamento;
}
