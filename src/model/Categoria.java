package model;

public class Categoria {
    private Long id;
    private String nome;
    private TipoCategoria tipo;

    public Categoria(String nome, TipoCategoria tipo){
        this.nome = nome;
        this.tipo = tipo;
    }

    public Long getId(){
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoCategoria getTipo() {
        return tipo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(TipoCategoria tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
