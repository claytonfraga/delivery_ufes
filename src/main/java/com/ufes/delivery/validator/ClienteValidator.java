package com.ufes.delivery.validator;

import com.ufes.delivery.model.Cliente;

public interface ClienteValidator {

     static void valida(Cliente cliente){
        if (cliente == null){
            throw new RuntimeException("Instância de cliente inválida!");
        }else{
            if(cliente.getNome() == null || cliente.getNome().isEmpty()){
                throw new RuntimeException("Nome de cliente inválido!");
            }
            if(cliente.getSaldo() < 0){
                throw new RuntimeException("Saldo do cliente inválido!");
            }
        }
    }
}
