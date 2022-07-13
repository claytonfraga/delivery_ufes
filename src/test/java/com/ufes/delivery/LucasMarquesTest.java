package com.ufes.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;
import com.ufes.delivery.model.Produto;

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
    @DisplayName("Verificar a validação de pedidos com a instância para o objeto cliente nula")
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
    @DisplayName("Verificar a validação de pedidos com a instância do objeto estabelecimento nula")
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
    @DisplayName("Verificar a validação de um ItemPedido que possui a variável quantidade negativa")
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
    @DisplayName("Verificar a validação de estabelecimento com a variável nome nula")
    void CT004(){
        //Arrange
        Estabelecimento estabelecimentoNomeNull = new Estabelecimento(null);
        Pedido pedido = new Pedido(1, cliente, estabelecimentoNomeNull, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoDAO.buscaProdutoPorCodigo(16), 0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Nome do estabelecimento inválido!");
    }

    @Test
    @DisplayName("Verificar a validação de estabelecimento com a variável nome vazia")
    void CT005(){
        //Arrange
        Estabelecimento estabelecimentoNomeVazio = new Estabelecimento("");
        Pedido pedido = new Pedido(1, cliente, estabelecimentoNomeVazio, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoDAO.buscaProdutoPorCodigo(16), 0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Nome do estabelecimento inválido!");
    }

    @Test
    @DisplayName("Verificar a validação de Cliente com a variável nome nula")
    void CT006(){
        //Arrange
        Cliente clienteNomeNull = new Cliente(null,100.0);
        Pedido pedido = new Pedido(1, clienteNomeNull, estabelecimento, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoDAO.buscaProdutoPorCodigo(16), 0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Nome de cliente inválido!");
    }

    @Test
    @DisplayName("Verificar a validação de Cliente com a variável nome vazia")
    void CT007(){
        //Arrange
        Cliente clienteNomeVazio = new Cliente("",100.0);
        Pedido pedido = new Pedido(1, clienteNomeVazio, estabelecimento, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoDAO.buscaProdutoPorCodigo(16), 0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Nome de cliente inválido!");
    }

    @Test
    @DisplayName("Verificar a validação de Cliente com a variável saldo negativa")
    void CT008(){
        //Arrange
        Cliente clienteSaldoNegativo = new Cliente("Teste saldo negativo",-10.0);
        Pedido pedido = new Pedido(1, clienteSaldoNegativo, estabelecimento, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoDAO.buscaProdutoPorCodigo(16), 0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Saldo do cliente inválido!");
    }

    @Test
    @DisplayName("Verificar a validação de produto com a variável nome nula")
    void CT009(){
        //Arrange
        Produto produtoNomeNull = new Produto(100,null, 2,10.0);
        Pedido pedido = new Pedido(1, cliente, estabelecimento, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoNomeNull, 0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Nome do produto inválido!");
    }
    @Test
    @DisplayName("Verificar a validação de produto com a variável nome vazia")
    void CT0010(){
        //Arrange
        Produto produtoNomeVazio = new Produto(100,"", 2,10.0);
        Pedido pedido = new Pedido(1, cliente, estabelecimento, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoNomeVazio, 0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Nome do produto inválido!");
    }
    
    @Test
    @DisplayName("Verificar a validação de ItemPedido com a instância de produto nula")
    void CT0011(){
        //Arrange
        Pedido pedido = new Pedido(1, cliente, estabelecimento, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, null, 0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Instância de produto inválida!");
    }
    
    @Test
    @DisplayName("Verificar a validação de produto com a variável preco unitário negativa")
    void CT0012(){
        //Arrange
    	Produto produtoPrecoNegativo = new Produto(100,"Teste", 2,-10.0);
        Pedido pedido = new Pedido(1, cliente, estabelecimento, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoPrecoNegativo, 0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Produto não pode ter preço unitário negativo!");
    }
    
    @Test
    @DisplayName("Verificar a validação de produto com a variável quantidade em estoque negativa")
    void CT0013(){
        //Arrange
    	Produto produtoQuantidadeNegativa = new Produto(100,"Teste", -2,10.0);
        Pedido pedido = new Pedido(1, cliente, estabelecimento, LocalTime.of(12, 00));
        //Act
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> {
                    pedido.incluir(new ItemPedido(pedido, produtoQuantidadeNegativa, 0.6));
                }
        );
        //Assert
        assertEquals(exception.getMessage(), "Produto não pode ter quantidade em estoque negativa!");
    }


}
