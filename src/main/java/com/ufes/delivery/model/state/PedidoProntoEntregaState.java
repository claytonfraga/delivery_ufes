package com.ufes.delivery.model.state;

import com.ufes.delivery.model.Pedido;

/**
 *
 * @author clayton
 */
public class PedidoProntoEntregaState extends PedidoState {

	public PedidoProntoEntregaState( Pedido pedido ) {
		super( pedido, "Pronto para entrega" );
	}

	@Override
	public void sairParaEntrega() {
		this.pedido.setState( new PedidoEmRotaEntregaState( this.pedido ) );
	}

	@Override
	public void cancelar() {
		this.pedido.setState( new PedidoCanceladoState( this.pedido, "Pedido pronto para entrega foi cancelado pelo estabelecimento" ) );
	}

}
