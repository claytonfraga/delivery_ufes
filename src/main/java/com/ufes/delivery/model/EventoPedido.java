package com.ufes.delivery.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author clayton
 */
public class EventoPedido {

	private LocalDateTime dataHora;
	private String mensagem;

	public EventoPedido( LocalDateTime dataHora, String mensagem ) {
		this.dataHora = dataHora;
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return this.mensagem;
	}

	public LocalDateTime getDataHora() {
		return this.dataHora;
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd/MM/yyyy HH:mm:ss" );
		return "Evento no pedido: " + dataHora.format( formatter ) + "; " + mensagem;
	}

}
