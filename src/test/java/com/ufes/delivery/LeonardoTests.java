package com.ufes.delivery;

import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LeonardoTests {
    public LeonardoTests(){}
    private ProdutoDAO dao;
    private Cliente cliente;
    private Estabelecimento vendedor;

    @BeforeEach
    void carregaDao() {
        dao = ProdutoDAO.getInstance();
    }

        
    @Test
    @DisplayName("Validar se o estado é atualizado quando o produto está pronto para entrega")
    void CT001(){
        
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
    void CT002(){
        
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
    void CT003(){
        
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
    void CT004(){
        
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
    void CT005(){
        
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
}