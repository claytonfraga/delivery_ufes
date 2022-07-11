package com.ufes.delivery;

import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.imposto.Imposto;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Desconto;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.EventoPedido;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;
import com.ufes.delivery.visitor.PedidoVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LeonardoTests {

    public LeonardoTests() {
    }
    private ProdutoDAO dao;
    private Cliente cliente;
    private Estabelecimento vendedor;

    @BeforeEach
    void carregaDao() {
        dao = ProdutoDAO.getInstance();
    }

    @Test
    @DisplayName("Validar se o estado é atualizado quando o produto está pronto para entrega")
    void CT001() {

        //Arrange
        cliente = new Cliente("Leonardo", 999.0);
        vendedor = new Estabelecimento("AutoSport");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 00));

        //Act
        pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(23), 1));
        pedido.concluir();
        pedido.pagar();
        pedido.preparar();

        //Assert
        assertThat(pedido.getEstado(), is("Pronto para entrega"));

    }

    @Test
    @DisplayName("Validar se o estado é atualizado quando o produto está aguardando pagamento")
    void CT002() {

        //Arrange
        cliente = new Cliente("Leonardo", 999.0);
        vendedor = new Estabelecimento("AutoSport");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 00));

        //Act
        pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(23), 1));
        pedido.concluir();
//        

        //Assert
        assertThat(pedido.getEstado(), is("Aguardando pagamento"));

    }

    @Test
    @DisplayName("Validar se o estado é atualizado quando o produto foi entregue ao cliente")
    void CT003() {

        //Arrange
        cliente = new Cliente("Leonardo", 999.0);
        vendedor = new Estabelecimento("AutoSport");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 00));

        //Act
        pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(23), 1));
        pedido.concluir();
        pedido.pagar();
        pedido.preparar();
        pedido.sairParaEntrega();
        pedido.entregar();

        //Assert
        assertThat(pedido.getEstado(), is("Pedido entregue ao cliente"));

    }

    @Test
    @DisplayName("Validar se o estado é atualizado quando o produto está aguardando pagamento")
    void CT004() {

        //Arrange
        cliente = new Cliente("Leonardo", 999.0);
        vendedor = new Estabelecimento("AutoSport");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 00));

        //Act
        pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(23), 1));
        pedido.concluir();

        //Assert
        assertThat(pedido.getEstado(), is("Aguardando pagamento"));

    }

    @Test
    @DisplayName("Validar se o estado é atualizado quando o pedido é cancelado pelo cliente")
    void CT005() {

        //Arrange
        cliente = new Cliente("Leonardo", 999.0);
        vendedor = new Estabelecimento("AutoSport");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 00));

        //Act
        pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(23), 1));
        pedido.cancelar();

        //Assert
        assertThat(pedido.getEstado(), is("Novo pedido cancelado pelo cliente"));

    }

    @Test
    @DisplayName("Incluir um item existente do pedido levanta exceção.")
    void CT006() {
        // Arrange
        cliente = new Cliente("Cliente", 2000.0);
        vendedor = new Estabelecimento("Lojas Americanas");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(9, 25));

        // Act
        pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(7), 2));
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(7), 2)),
                "Espera-se que incluir() um mesmo item ao pedido levante uma exceção");

        // Assert
        assertEquals("Produto Creme de Leite já existe no pedido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Remover um produto do pedido.")
    void CT007() {
        // Arrange
        cliente = new Cliente("Cliente", 2000.0);
        vendedor = new Estabelecimento("Lojas Americanas");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(9, 25));
        Exception exception = null;

        // Act
        pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(7), 2));
        try {
            pedido.removeItem("Creme de Leite");
        } catch (Exception e) {
            exception = e;
        }

        // Assert
        assertEquals(null, exception.getLocalizedMessage());

    }

    @Test
    @DisplayName("Verifica a remoção de um Item que não existe.")
    void CT008() {
        // Arrange
        cliente = new Cliente("Cliente", 2000.0);
        vendedor = new Estabelecimento("Lojas Americanas");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(9, 25));

        // Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> pedido.removeItem("Creme de Leite"),
                "Espera-se que removeItem().");

        // Assert
        assertEquals("Item Creme de Leite não existe", thrown.getMessage());

    }

    @Test
    @DisplayName("Verifica o tamanho de produtos na ProdutoDAO.")
    void CT009() {
        // Arrange

        // Act
        var quantidadeItens = dao.getNroProdutos();

        // Assert
        assertEquals(37, quantidadeItens);

    }

    @Test
    @DisplayName("Valida cobertura do toString() PedidoVisitor")
    void CT0010() {
        // Arrange
        cliente = new Cliente("Cliente", 2000.0);
        vendedor = new Estabelecimento("Lojas Americanas");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(9, 25));
        var desconto = new Desconto("Teste", 0.5, 20.0);
        var imposto = new Imposto("Teste", 0, 0);
        var visitor = new PedidoVisitor(pedido);

        // Act
        pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(7), 2));
        pedido.add(desconto);
        pedido.add(imposto);

        StringBuilder strEventos = new StringBuilder();
        for (EventoPedido eventoPedido : pedido.getEventos()) {
            strEventos.append("\n\t");
            strEventos.append(eventoPedido.toString());
        }

        // Assert
        assertEquals("\n"
                + "-------------------------------------------------------------------------------------------------\n"
                + "Pedido: 1, data: 11/07/2022, horario: 09:25:00, estado: Novo\n"
                + "Cliente: Cliente: Cliente, saldo de R$ 2000,00\n"
                + "Estabelecimento: Estabelecimento: Lojas Americanas\n"
                + "Itens do pedido:"
                + "\n\tItem: Creme de Leite, quantidade: 2,00, preço unitário: R$ 2,65, preço total do item: R$ 5,30"
                + "\nValor total dos itens R$: 5,30\n"
                + "\n"
                + "Descontos concedidos:\n"
                + "	Desconto: Teste, (%):50,00, valor (R$): 20,00\n"
                + "Valor total em descontos R$: 20,00\n"
                + "\n"
                + "Impostos calculados:\n"
                + "	Imposto: Teste, (%):0,00, valor (R$): 0,00\n"
                + "Valor total em impostos R$: 0,00\n"
                + "\n"
                + "Valor total do pedido R$: 0,00\n"
                + "\n"
                + "Eventos no pedido:"
                + strEventos.toString(), visitor.toString());

    }
}
