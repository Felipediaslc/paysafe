package br.pucpr.bsi.wss.paysafe.pagamento.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstadoPagamento {
    public static enum TipoEstadoPagamento {
        AGUARDANDO_CHECKOUT, PAGAMENTO_CANCELADO, BOLETO_EMITIDO, BOLETO_PAGO, BOLETO_VENCIDO
    }

    private String codigoPagamento;
    private TipoEstadoPagamento estado;
}
