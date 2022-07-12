package com.ufes.delivery.validator;

import com.ufes.delivery.model.Estabelecimento;

public interface EstabelecimentoValidator {

     static void valida(Estabelecimento estabelecimento){
        if(estabelecimento == null){
            throw new RuntimeException("Instância do estabelecimento inválida!");
        }else{
            if(estabelecimento.getNome() == null || estabelecimento.getNome().isEmpty()){
                throw new RuntimeException("Nome do estabelecimento inválido!");
            }
        }
    }
}
