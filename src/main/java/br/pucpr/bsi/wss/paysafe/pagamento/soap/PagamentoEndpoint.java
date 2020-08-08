package br.pucpr.bsi.wss.paysafe.pagamento.soap;

import br.pucpr.bsi.wss.paysafe.pagamento.models.EstadoPagamento;
import br.pucpr.bsi.wss.paysafe.pagamento.services.PagamentoService;
import br.pucpr.bsi.wss.paysafe.pagamento.soap.schema.TipoEstadoPagamento;
import br.pucpr.bsi.wss.paysafe.pagamento.soap.schema.VerificarPagamentoRequest;
import br.pucpr.bsi.wss.paysafe.pagamento.soap.schema.VerificarPagamentoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PagamentoEndpoint {

    public static final String NAMESPACE = "http://paysafe.wss.bsi.pucpr.br/pagamento/soap/schema";

    private final PagamentoService pagamentoService;

    @Autowired
    public PagamentoEndpoint(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "VerificarPagamentoRequest")
    @ResponsePayload
    public VerificarPagamentoResponse verificarPagamento(@RequestPayload VerificarPagamentoRequest verificarPagamentoRequest) {

        EstadoPagamento estadoPagamento =
                pagamentoService.verificarPagamento(verificarPagamentoRequest.getCodigoLoja(), verificarPagamentoRequest.getCodigoPagamento());

        VerificarPagamentoResponse response = new VerificarPagamentoResponse();
        response.setCodigoPagamento(estadoPagamento.getCodigoPagamento());
        response.setEstado(TipoEstadoPagamento.fromValue(estadoPagamento.getEstado().name()));

        return response;
    }
}
