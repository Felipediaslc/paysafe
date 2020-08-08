package br.pucpr.bsi.wss.paysafe.pagamento.soap;

import br.pucpr.bsi.wss.paysafe.pagamento.models.Cliente;
import br.pucpr.bsi.wss.paysafe.pagamento.models.Endereco;
import br.pucpr.bsi.wss.paysafe.pagamento.models.Produto;
import br.pucpr.bsi.wss.paysafe.pagamento.models.*;
import br.pucpr.bsi.wss.paysafe.pagamento.services.PagamentoService;
import br.pucpr.bsi.wss.paysafe.pagamento.soap.schema.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static java.util.stream.Collectors.toList;

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

    @PayloadRoot(namespace = NAMESPACE, localPart = "IniciarPagamentoRequest")
    @ResponsePayload
    public IniciarPagamentoResponse iniciarPagamento(@RequestPayload IniciarPagamentoRequest verificarPagamentoRequest) {

        LiberacaoPagamento estadoPagamento =
                pagamentoService.iniciarProcessoPagamento(ProcessoPagamento.builder()
                        .cliente(Cliente.builder()
                                .cpf(verificarPagamentoRequest.getCliente().getCpf())
                                .nome(verificarPagamentoRequest.getCliente().getNome())
                                .endereco(Endereco.builder()
                                        .cep(verificarPagamentoRequest.getCliente().getEndereco().getCep())
                                        .cidade(verificarPagamentoRequest.getCliente().getEndereco().getCidade())
                                        .complemento(verificarPagamentoRequest.getCliente().getEndereco().getComplemento())
                                        .logradouro(verificarPagamentoRequest.getCliente().getEndereco().getLogradouro())
                                        .estado(verificarPagamentoRequest.getCliente().getEndereco().getEstado())
                                        .numero(verificarPagamentoRequest.getCliente().getEndereco().getNumero())
                                        .build())
                                .build())
                        .produtos(verificarPagamentoRequest.getProdutos().getProduto().stream().map(produto ->
                                Produto.builder()
                                        .descricao(produto.getDescricao())
                                        .quantidade(produto.getQuantidade())
                                        .valor(produto.getValor())
                                        .valorUnitario(produto.getValorUnitario())
                                        .build()
                        ).collect(toList()))
                        .codigoLoja(verificarPagamentoRequest.getCodigoLoja())
                        .urlCallback(verificarPagamentoRequest.getUrlCallback())
                        .valorTotal(verificarPagamentoRequest.getValorTotal())
                        .build());

        IniciarPagamentoResponse response = new IniciarPagamentoResponse();
        response.setAutorizado(estadoPagamento.isAutorizado());
        response.setCodigoPagamento(estadoPagamento.getCodigoPagamento());
        response.setLinkRedirecionamento(estadoPagamento.getLinkRedirecionamento());

        return response;
    }
}
