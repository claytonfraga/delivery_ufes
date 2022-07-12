package com.ufes.delivery.dao;

/**
 *
 * @author clayton
 */
public class PedidoDAO {

	private static PedidoDAO instance;
	private int nroSequencialPedido = 0;

	private PedidoDAO() {

	}

	public static PedidoDAO getInstance() {
		if( instance == null ) {
			instance = new PedidoDAO();
		}
		return instance;
	}

	public int getNroSequencialPedido() {
		nroSequencialPedido++;
		return nroSequencialPedido;
	}

}
