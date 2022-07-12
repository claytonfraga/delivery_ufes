package com.ufes.delivery.builder;

import java.time.LocalTime;

import com.ufes.delivery.dao.PedidoDAO;
import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.Pedido;

/**
 *
 * @author clayton
 */
public abstract class CestaBuilder {

	protected Pedido pedido;
	protected ProdutoDAO dao;

	public CestaBuilder( Cliente cliente, Estabelecimento estabelecimento ) {
		int nroPedido = PedidoDAO.getInstance().getNroSequencialPedido();
		pedido = new Pedido( nroPedido, cliente, estabelecimento, LocalTime.of( 14, 00 ) );
		dao = ProdutoDAO.getInstance();
	}

	public abstract void addOrigemAnimal();

	public abstract void addGraos();

	public abstract void addIndustrializados();

	public abstract void addLegumesEFrutas();

	public abstract void addOutros();

	public final Pedido getPedido() {
		return pedido;
	}

}
