package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transacao {

    private Long id;
    private BigDecimal valor;
    private LocalDate data;
    private String descricao;
    private Usuario usuario;
    private Categoria categoria;


    public Transacao(BigDecimal valor, String descricao, Usuario usuario, Categoria categoria) {
        this.valor = valor;
        this.data = LocalDate.now();
        this.descricao = descricao;
        this.usuario = usuario;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id=" + id +
                ", valor=" + valor +
                ", data=" + data +
                ", descricao='" + descricao + '\'' +
                ", usuario=" + usuario.getNome() +
                ", categoria=" + categoria.getNome() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }


    public LocalDate getData() {
        return data;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
