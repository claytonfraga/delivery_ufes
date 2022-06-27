package com.ufes.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ufes.delivery.builder.CestaTopBuilder;
import com.ufes.delivery.builder.DiretorCesta;
import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;

import java.time.LocalDate;
import java.time.LocalTime;

public class PedidoTest {

    public PedidoTest() {
    }

    private ProdutoDAO dao;
    private Cliente cliente;
    private Estabelecimento vendedor;
    private DiretorCesta diretor;

    @BeforeEach
    public void carregaDao() {
        dao = ProdutoDAO.getInstance();

    }

    @Test
    @DisplayName("Teste de criação de um pedido com um item")
    public void CT001() {

        //Arrange
        cliente = new Cliente("Fulano", 1000.0);
        vendedor = new Estabelecimento("Casa XPTO");
        diretor = new DiretorCesta();

        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 00));

        //Act
        pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(16), 0.6));

        pedido.concluir();
        pedido.pagar();
        pedido.preparar();
        pedido.sairParaEntrega();
        pedido.entregar();
        pedido.avaliar(5);

        //Assert
        assertEquals(1, pedido.getItens().size());
        assertEquals(LocalDate.now(), pedido.getData());
        assertEquals(LocalTime.of(12, 00), pedido.getHora());
        assertEquals("Fulano", pedido.getCliente().getNome());
        assertEquals(26.87, pedido.getValorFinal(), 0.01);
        assertEquals("Pedido entregue ao cliente", pedido.getEstado());
    }

    @Test
    @DisplayName("Não permite que um cliente pague por um pedido "
            + "cujo valor seja superior ao dinheiro que ele tem")
    public void CT002() {

        cliente = new Cliente("Fulano", 10.0);
        vendedor = new Estabelecimento("Casa XPTO");

        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 00));

        pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(16), 0.6));

        pedido.concluir();

        //Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> pedido.pagar(),
                "O cliente não pode pagar por esse pedido"
        );

        //Assert
        assertEquals("O cliente não pode pagar por esse pedido", thrown.getMessage());

    }

}
