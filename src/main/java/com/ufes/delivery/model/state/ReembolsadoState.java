package com.ufes.delivery.model.state;

import com.ufes.delivery.model.Pedido;

/**
 *
 * @author clayton
 */
public class ReembolsadoState extends PedidoState {

	public ReembolsadoState( Pedido pedido ) {
		super( pedido, "Pedido reembolsado" );
	}

	@Override
	public void avaliar( int nota ) {
		super.avaliar( nota );
	}

}
