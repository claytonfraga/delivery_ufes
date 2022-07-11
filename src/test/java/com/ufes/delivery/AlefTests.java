package com.ufes.delivery;

import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.imposto.Imposto;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Desconto;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.EventoPedido;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;
import com.ufes.delivery.visitor.IPedidoVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AlefTests {
    public AlefTests(){}
    private ProdutoDAO dao;
    private Cliente cliente;
    private Estabelecimento vendedor;

    @BeforeEach
    void carregaDao() {
        dao = ProdutoDAO.getInstance();
    }

    @Test
    @DisplayName("Realizar pedido com saldo insuficiente")
    void CT001(){
        
        //Arrange
        cliente = new Cliente("Alef", 0.0);
        vendedor = new Estabelecimento("BC Supermercados");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 00));
        Exception exception = null;

        //Act
        
        try {
            pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(1), 1));
            pedido.concluir();
            pedido.pagar();
        } catch (Exception e) {
            exception = e;
        }
        

        //Assert
        
        assertThat(exception.getMessage(), is("O cliente não pode pagar por esse pedido"));
        
    }
    
    @Test
    @DisplayName("Adiconar produto com quantidade igual a 0")
    void CT002(){
        
        //Arrange
        cliente = new Cliente("Alef", 200.0);
        vendedor = new Estabelecimento("BC Supermercados");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 00));
        Exception exception = null;
       

        //Act
         try {
            pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(1), 0));
            pedido.concluir();
            pedido.pagar();
            pedido.getEstado();
        } catch (Exception e) {
            exception = e;
        }      

        //Assert
        assertThat(exception.getMessage(), is("Quantidade deve ser > 0"));
        
    }

     @Test
    @DisplayName("Adicionar um visitante inválido no pedido")
    void CT003(){
                
        //Arrange
        cliente = new Cliente("Alef", 200.0);
        vendedor = new Estabelecimento("BC Supermercados");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 1));
        Exception exception = null;
        IPedidoVisitor visitor = null;
       

        //Act
         try {
            pedido.setVisitor(visitor);
            
        } catch (Exception e) {
            exception = e;
        }
           
        //Assert
        assertThat(exception.getMessage(), is("Informe um Visitante válido"));
        
    }
    
     @Test
    @DisplayName("Adicionar um evento inválido no pedido")
    void CT004(){
                
        //Arrange
        cliente = new Cliente("Alef", 200.0);
        vendedor = new Estabelecimento("BC Supermercados");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 1));
        Exception exception = null;
        EventoPedido evento = null;
       

        //Act
         try {
            //pedido.setVisitor(visitor);
            pedido.add(evento);
        } catch (Exception e) {
            exception = e;
        }
           
        //Assert
        assertThat(exception.getMessage(), is("Informe um evento válido!"));
        
    }
    
     @Test
    @DisplayName("Adicionar um desconto inválido no pedido")
    void CT005(){
                
        //Arrange
        cliente = new Cliente("Alef", 200.0);
        vendedor = new Estabelecimento("BC Supermercados");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 1));
        Exception exception = null;
        Desconto desconto = null;
       

        //Act
         try {
            //pedido.setVisitor(visitor);
            pedido.add(desconto);
        } catch (Exception e) {
            exception = e;
        }
           
        //Assert
        assertThat(exception.getMessage(), is("Informe um desconto válido!"));
        
    }
    
     @Test
    @DisplayName("Adicionar um imposto inválido no pedido")
    void CT006(){
                
        //Arrange
        cliente = new Cliente("Alef", 200.0);
        vendedor = new Estabelecimento("BC Supermercados");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 1));
        Exception exception = null;
        Imposto imposto = null;
       

        //Act
         try {
            //pedido.setVisitor(visitor);
            pedido.add(imposto);
        } catch (Exception e) {
            exception = e;
        }
           
        //Assert
        assertThat(exception.getMessage(), is("Informe um imposto válido!"));
        
    }
    
    @Test
    @DisplayName("Incluir pedido inválido")
    void CT007(){
        
        //Arrange
        cliente = new Cliente("Alef", 200.0);
        vendedor = new Estabelecimento("BC Supermercados");
        Pedido pedido = null;
        Exception exception = null;
       

        //Act
         try {
            pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(1), 0));
            
        } catch (Exception e) {
            exception = e;
        }
                   
        //Assert
        assertThat(exception.getMessage(), is("É necessário informar o pedido!"));
        
    }
    
    @Test
    @DisplayName("Incluir produto inválido no pedido")
    void CT008(){
        
        //Arrange
        cliente = new Cliente("Alef", 200.0);
        vendedor = new Estabelecimento("BC Supermercados");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 1));
        Exception exception = null;
       

        //Act
         try {
            pedido.incluir(new ItemPedido(pedido, null, 0));
            
        } catch (Exception e) {
            exception = e;
        }
                   
        //Assert
        assertThat(exception.getMessage(), is("Informe um produto valido!"));
        
    }
    
    @Test
    @DisplayName("Verificar se, ao pagar um pedido, seu estado é atualizado para Confirmado")
    void CT009(){
        
        //Arrange
        cliente = new Cliente("Alef", 200.0);
        vendedor = new Estabelecimento("BC Supermercados");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 00));
        
       

        //Act
         
        pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(1), 1));
        pedido.concluir();
        pedido.pagar();
        
        //Assert
        assertThat(pedido.getEstado(), is("Confirmado"));
        
    }
    
    @Test
    @DisplayName("Verificar se, ao pagar um pedido, seu estado é atualizado para Pedido em rota de entrega")
    void CT010(){
        
        //Arrange
        cliente = new Cliente("Alef", 200.0);
        vendedor = new Estabelecimento("BC Supermercados");
        Pedido pedido = new Pedido(1, cliente, vendedor, LocalTime.of(12, 00));
        
       

        //Act
         
        pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(1), 1));
        pedido.concluir();
        pedido.pagar();
        pedido.preparar();
        pedido.sairParaEntrega();
        
        //Assert
        assertThat(pedido.getEstado(), is("Pedido em rota de entrega"));
        
    }
}
