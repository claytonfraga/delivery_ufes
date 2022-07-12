package com.ufes.delivery.model.state;

import com.ufes.delivery.model.Pedido;

public class PedidoEntregueState extends PedidoState {

	public PedidoEntregueState( Pedido pedido ) {
		super( pedido, "Pedido entregue ao cliente" );
	}

	@Override
	public void avaliar( int nota ) {
		super.avaliar( nota );
	}
}
