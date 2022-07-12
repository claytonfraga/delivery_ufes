package com.ufes.delivery.visitor;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import com.ufes.delivery.exception.OperacaoInvalidaException;
import com.ufes.delivery.imposto.Imposto;
import com.ufes.delivery.model.Desconto;
import com.ufes.delivery.model.EventoPedido;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;

public class PedidoVisitor implements IPedidoVisitor {

	private Pedido pedido;

	public PedidoVisitor( Pedido pedido ) {
		if( pedido == null ) {
			throw new OperacaoInvalidaException( "Instancia de pedido inválida!" );
		}
		this.pedido = pedido;
	}

	@Override
	public String toString() {
		DateTimeFormatter formatterData = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );
		DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern( "HH:mm:ss" );
		DecimalFormat df = new DecimalFormat( "0.00" );

		StringBuilder strItens = new StringBuilder();
		for( ItemPedido item : pedido.getItens() ) {
			strItens.append( "\n\t" );
			strItens.append( item.toString() );
		}

		StringBuilder strDescontos = new StringBuilder();
		for( Desconto desconto : pedido.getDescontosConcedidos() ) {
			strDescontos.append( "\n\t" );
			strDescontos.append( desconto.toString() );
		}
		if( strDescontos.length() == 0 ) {
			strDescontos.append( "Descontos não concedidos" );
		}

		StringBuilder strImpostos = new StringBuilder();
		for( Imposto imposto : pedido.getImpostos() ) {
			strImpostos.append( "\n\t" );
			strImpostos.append( imposto.toString() );
		}
		if( strImpostos.length() == 0 ) {
			strImpostos.append( "Impostos não calculados" );
		}

		StringBuilder strEventos = new StringBuilder();

		for( EventoPedido eventoPedido : pedido.getEventos() ) {
			strEventos.append( "\n\t" );
			strEventos.append( eventoPedido.toString() );
		}

		return "\n-------------------------------------------------------------------------------------------------" + "\nPedido: " + pedido.getNumero() + ", data: " + pedido.getData().format( formatterData ) + ", horario: " + pedido.getHora().format( formatterHora ) + ", estado: "
				+ pedido.getEstado() + "\nCliente: " + pedido.getCliente() + "\nEstabelecimento: " + pedido.getEstabelecimento() + "\nItens do pedido:" + strItens.toString() + "\nValor total dos itens R$: " + df.format( pedido.getValorTotal() ) + "\n\nDescontos concedidos:" + strDescontos.toString()
				+ "\nValor total em descontos R$: " + df.format( pedido.getValorTotalDescontos() ) + "\n\nImpostos calculados:" + strImpostos.toString() + "\nValor total em impostos R$: " + df.format( pedido.getValorTotalImpostos() ) + "\n\nValor total do pedido R$: "
				+ df.format( pedido.getValorFinal() ) + "\n\nEventos no pedido:" + strEventos.toString();
	}

}
