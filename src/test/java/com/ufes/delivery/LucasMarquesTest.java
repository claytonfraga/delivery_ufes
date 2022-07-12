package com.ufes.delivery;

import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LucasMarquesTest {

    private Cliente cliente;
    private Estabelecimento estabelecimento;
    private ProdutoDAO produtoDAO;

    @BeforeEach
    void beforeEach(){
        this.cliente = new Cliente("Lucas Marques", 2022);
        this.estabelecimento = new Estabelecimento("Estabelecimento teste");
        this.produtoDAO = ProdutoDAO.getInstance();
    }

    @Test
    void CT001(){
        //Arrange
        Pedido pedidoClienteNull = new Pedido(1, null, estabelecimento, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedidoClienteNull.incluir(new ItemPedido(pedidoClienteNull, produtoDAO.buscaProdutoPorCodigo(16), 0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Instância de cliente inválida!");
    }

    @Test
    void CT002(){
        //Arrange
        Pedido pedido = new Pedido(1, cliente, null, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoDAO.buscaProdutoPorCodigo(16), 0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Instância do estabelecimento inválida!");
    }

    @Test
    void CT003(){
        //Arrange
        Pedido pedido = new Pedido(1, cliente, estabelecimento, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoDAO.buscaProdutoPorCodigo(16), -0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "A quantidade de um item não pode ser menor que 0!");
    }

    @Test
    void CT004(){
        //Arrange
        Estabelecimento estabelecimentoNomeNull = new Estabelecimento(null);
        Pedido pedido = new Pedido(1, cliente, estabelecimentoNomeNull, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoDAO.buscaProdutoPorCodigo(16), -0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Nome do estabelecimento inválido!");
    }

    @Test
    void CT005(){
        //Arrange
        Estabelecimento estabelecimentoNomeVazio = new Estabelecimento("");
        Pedido pedido = new Pedido(1, cliente, estabelecimentoNomeVazio, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoDAO.buscaProdutoPorCodigo(16), -0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Nome do estabelecimento inválido!");
    }

    @Test
    void CT006(){
        //Arrange
        Cliente clienteNomeNull = new Cliente(null,100.0);
        Pedido pedido = new Pedido(1, clienteNomeNull, estabelecimento, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoDAO.buscaProdutoPorCodigo(16), -0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Nome de cliente inválido!");
    }

    @Test
    void CT007(){
        //Arrange
        Cliente clienteNomeVazio = new Cliente("",100.0);
        Pedido pedido = new Pedido(1, clienteNomeVazio, estabelecimento, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoDAO.buscaProdutoPorCodigo(16), -0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Nome de cliente inválido!");
    }

    @Test
    void CT008(){
        //Arrange
        Cliente clienteSaldoNegativo = new Cliente("Teste saldo negativo",-10.0);
        Pedido pedido = new Pedido(1, clienteSaldoNegativo, estabelecimento, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoDAO.buscaProdutoPorCodigo(16), -0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Saldo do cliente inválido!");
    }
}
