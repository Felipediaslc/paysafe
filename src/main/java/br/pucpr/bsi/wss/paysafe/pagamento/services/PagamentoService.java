package br.pucpr.bsi.wss.paysafe.pagamento.services;

import br.pucpr.bsi.wss.paysafe.pagamento.models.EstadoPagamento;
import br.pucpr.bsi.wss.paysafe.pagamento.models.LiberacaoPagamento;
import br.pucpr.bsi.wss.paysafe.pagamento.models.ProcessoPagamento;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PagamentoService {
    private final String urlPagamentoBase;

    public PagamentoService(@Value("${paysafe.pagamento.urlBase}") String urlPagamentoBase) {
        this.urlPagamentoBase = urlPagamentoBase;
    }

    public LiberacaoPagamento iniciarProcessoPagamento(ProcessoPagamento processoPagamento) {
        final String codigoPagamento = UUID.randomUUID().toString();

        return LiberacaoPagamento.builder()
                .autorizado(true)
                .codigoPagamento(codigoPagamento)
                .linkRedirecionamento(urlPagamentoBase + codigoPagamento)
                .build();
    }

    public EstadoPagamento verificarPagamento(String codigoLoja, String codigoPagamento) {
        return EstadoPagamento.builder()
                .estado(EstadoPagamento.TipoEstadoPagamento.BOLETO_EMITIDO)
                .codigoPagamento(codigoPagamento)
                .build();
    }
}
