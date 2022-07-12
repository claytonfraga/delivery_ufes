package com.ufes.delivery.desconto;

import com.ufes.delivery.model.Pedido;

/**
 *
 * @author clayton
 */
interface IMetodoDesconto {

	public void calcula( Pedido pedido );
}
