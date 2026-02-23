package service;

import model.Categoria;
import model.Transacao;
import model.Usuario;
import repository.CategoriaRepository;
import repository.TransacaoRepository;
import repository.UsuarioRepository;

import java.math.BigDecimal;
import java.util.List;

public class TransacaoService {

    private TransacaoRepository transacaoRepository;
    private CategoriaRepository categoriaRepository;
    private UsuarioRepository usuarioRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.transacaoRepository = transacaoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Transacao criarTransacao(BigDecimal valor, String descricao, Long usuarioId, Long categoriaId){
        if(valor == null || valor.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Valor deve ser maior que zero e diferente de nulo");
        }
        if(descricao == null || descricao.isBlank()){
            throw new IllegalArgumentException("Descrição não pode ser nula nem estar em branco");
        }
        Usuario usuario = usuarioRepository.findById(usuarioId);
        Categoria categoria = categoriaRepository.findById(categoriaId);
        if(usuario == null){
            throw new IllegalArgumentException("Usuário não encontrado!");
        }
        if(categoria == null){
            throw new IllegalArgumentException("Categoria não encontrada");
        }

        Transacao transacao = new Transacao(valor, descricao, usuario, categoria);
        return transacaoRepository.save(transacao);
    }

    public Transacao buscarPorId(Long id){
        if(id == null){
            throw new IllegalArgumentException("Id nulo");
        }
        Transacao transacao = transacaoRepository.findById(id);
        if(transacao == null){
            throw new IllegalArgumentException("Transação não encontrada");
        }
        return transacao;
    }

    public List<Transacao> listarTodos(){
        return transacaoRepository.findAll();
    }

    public void removerTransacao(Long id){
        if(id == null){
            throw new IllegalArgumentException("Id inválido");
        }
        Transacao transacao = transacaoRepository.findById(id);

        if (transacao == null){
            throw new IllegalArgumentException("Transação não encontrada");
        }

        transacaoRepository.deleteById(id);
    }

    public List<Transacao> buscarPorUsuario(Long usuarioId){
        if (usuarioId == null) {
            throw new IllegalArgumentException("Id nulo");
        }
        Usuario usuario = usuarioRepository.findById(usuarioId);
        if(usuario == null){
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        return transacaoRepository.findByUsuario(usuario);
    }

    public List<Transacao> buscarPorCategoria(Long categoriaId){
        if(categoriaId == null){
            throw new IllegalArgumentException("Id nulo");
        }
        Categoria categoria = categoriaRepository.findById(categoriaId);
        if(categoria == null){
            throw new IllegalArgumentException("Categoria não encontrada");
        }
        return transacaoRepository.findByCategoria(categoria);
    }

}
