package repository;

import model.Categoria;
import model.Transacao;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class TransacaoRepository {
    private List<Transacao> transacoes = new ArrayList<>();
    private Long idSequence = 1L;

    public Transacao save(Transacao transacao){
        if (transacao.getId() == null){
            transacao.setId(idSequence++);
            transacoes.add(transacao);
        }
        return transacao;
    }

    public Transacao findById(Long id){
        for (Transacao transacao : transacoes){
            if(transacao.getId().equals(id)){
                return transacao;
            }
        } return null;
    }

    public List<Transacao> findAll(){
        return new ArrayList<>(transacoes);
    }

    public void deleteById(Long id){
        for(int i = 0; i<transacoes.size(); i++){
            if(transacoes.get(i).getId().equals(id)){
                transacoes.remove(i);
                break;
            }
        }
    }

    public Transacao updateById(Transacao transacao){
        if (transacao.getId() == null){
            throw new IllegalArgumentException("Transação não pode ser atualizada sem um ID");
        }

        for (int i = 0; i < transacoes.size(); i++){
            if(transacoes.get(i).getId().equals(transacao.getId())){
                transacoes.set(i, transacao);
                return transacao;
            }
        }

        throw new IllegalArgumentException("Transação não encontrada para atualização");
    }

    public List<Transacao> findByUsuario(Usuario usuario){
        List<Transacao> resultado = new ArrayList<>();
        for(Transacao transacao : transacoes){
            if (transacao.getUsuario().equals(usuario)){
                resultado.add(transacao);
            }
        }
        return resultado;
    }

    public List<Transacao> findByCategoria(Categoria categoria){
        List<Transacao> resultado = new ArrayList<>();
        for(Transacao transacao : transacoes){
            if(transacao.getCategoria().equals(categoria)){
                resultado.add(transacao);
            }
        } return resultado;
    }


}
