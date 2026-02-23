package service;

import model.Categoria;
import model.TipoCategoria;
import repository.CategoriaRepository;

import java.util.List;

public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }


    public Categoria criarCategoria(String nome, TipoCategoria tipoCategoria){
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome da categoria não pode ser vazio nem nulo");
        }
        if(tipoCategoria == null) {
            throw new IllegalArgumentException("Tipo inválido de categoria!");
        }
        for (Categoria categoria : categoriaRepository.findAll()){
            if(categoria.getNome().equalsIgnoreCase(nome)){
                throw new IllegalArgumentException("Já existe uma categoria com esse nome!");
            }
        }
        Categoria categoria = new Categoria(nome, tipoCategoria);
        return categoriaRepository.save(categoria);

    }

    public Categoria buscarPorId(Long id){
        if(id == null){
            throw new IllegalArgumentException("Id nulo!");
        }
        Categoria categoria = categoriaRepository.findById(id);
        if(categoria == null){
            throw new IllegalArgumentException("Categoria não encontrada");
        }
        return categoria;
    }

    public List<Categoria> listarTodasCategorias(){
        return categoriaRepository.findAll();
    }

    public void removerCategoria(Long id){
        if (id == null){
            throw new IllegalArgumentException("Id inválido");
        }
        Categoria categoria = categoriaRepository.findById(id);
        if(categoria == null){
            throw new IllegalArgumentException("Categoria não encontrada");
        }

        categoriaRepository.deleteById(id);
    }
}


