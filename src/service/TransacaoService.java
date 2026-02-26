package service;

import model.Categoria;
import model.TipoCategoria;
import model.Transacao;
import model.Usuario;
import repository.CategoriaRepository;
import repository.TransacaoRepository;
import repository.UsuarioRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    //métodos para o total de receitas, despesas e saldototal do sistema de modo geral.

    public BigDecimal totalReceitas(){
        BigDecimal totalReceitas = BigDecimal.ZERO;
        List<Transacao> transacoes = transacaoRepository.findAll();

        for(Transacao t : transacoes){
            if(t.getCategoria().getTipo().equals(TipoCategoria.RECEITA)){
                totalReceitas = totalReceitas.add(t.getValor());
            }
        }
        return totalReceitas; // Refatorar usando Streams
    }

    public BigDecimal totalDespesas(){
        BigDecimal totalDespesas = BigDecimal.ZERO;
        List<Transacao> transacoes = transacaoRepository.findAll();

        for(Transacao t : transacoes){
            if(t.getCategoria().getTipo().equals(TipoCategoria.DESPESA)){
                totalDespesas = totalDespesas.add(t.getValor());
            }
        }
        return totalDespesas;
    }

    public BigDecimal calcularSaldoAtual(){
        BigDecimal receitas = totalReceitas();
        BigDecimal despesas = totalDespesas();
        return receitas.subtract(despesas);
    }

    private List<Transacao> buscarTransacoesDoUsuario(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId);
        return transacaoRepository.findByUsuario(usuario);
    }

    public BigDecimal totalReceitasUsuario(Long usuarioId){
        BigDecimal totalReceitasUsuario = BigDecimal.ZERO;
        List<Transacao> transacoes = buscarTransacoesDoUsuario(usuarioId);

        for(Transacao transacao : transacoes) {
            if (transacao.getCategoria().getTipo().equals(TipoCategoria.RECEITA)) {
                totalReceitasUsuario = totalReceitasUsuario.add(transacao.getValor());
            }
        }
        return totalReceitasUsuario;
    }

    public BigDecimal totalDespesasUsuario(Long usuarioId){
        BigDecimal totalDespesasUsuario = BigDecimal.ZERO;
        List<Transacao> transacoes = buscarTransacoesDoUsuario(usuarioId);

        for(Transacao transacao : transacoes){
            if(transacao.getCategoria().getTipo().equals(TipoCategoria.DESPESA)) {
                totalDespesasUsuario = totalDespesasUsuario.add(transacao.getValor());
            }
        }
        return totalDespesasUsuario;
    }

    public BigDecimal saldoAtualUsuario(Long usuarioId){
        return  totalReceitasUsuario(usuarioId).subtract(totalDespesasUsuario(usuarioId));
    }


    public List<Transacao> filtarPorMesEAno(int mes, int ano){
        List<Transacao> transacoes = transacaoRepository.findAll();
        List<Transacao> filtrados = new ArrayList<>();
        for(Transacao t : transacoes){
            if(t.getData().getMonthValue() == mes && t.getData().getYear() == ano){
                filtrados.add(t);
            }
        }
        return filtrados;
    }

    public List<Transacao> filtrarPorMes(int mes){
        List<Transacao> transacoes = transacaoRepository.findAll();
        List<Transacao> filtrados = new ArrayList<>();
        for(Transacao t : transacoes){
            if(t.getData().getMonthValue() == mes){
                filtrados.add(t);
            }
        }  return filtrados;
    }

    public List<Transacao> filtrarPorAno(int ano){
        List<Transacao> transacoes = transacaoRepository.findAll();
        List<Transacao> filtrados = new ArrayList<>();
        for(Transacao t : transacoes){
            if(t.getData().getYear() == ano){
                filtrados.add(t);
            }
        } return filtrados;
    }

    public List<Transacao> filtrarPorCategoria(Long categoriaId){
        Categoria categoria = categoriaRepository.findById(categoriaId);
        return transacaoRepository.findByCategoria(categoria);
    }

    public List<Transacao> ordenarPorValorCrescente(){
        List<Transacao> transacoes = transacaoRepository.findAll();
        List<Transacao> filtrados = new ArrayList<>(transacoes);
        filtrados.sort(new Comparator<Transacao>() {
            @Override
            public int compare(Transacao transacao, Transacao t1) {
                return transacao.getValor().compareTo(t1.getValor());
            }
        });
        return filtrados;
    }

    public List<Transacao> ordenarPorValorDecrescente(){
        List<Transacao> transacoes = transacaoRepository.findAll();
        List<Transacao> filtrados = new ArrayList<>(transacoes);
        filtrados.sort(new Comparator<Transacao>() {
            @Override
            public int compare(Transacao transacao, Transacao t1) {
                return t1.getValor().compareTo(transacao.getValor());
            }
        });
        return filtrados;
    }









}
