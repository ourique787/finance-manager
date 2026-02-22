package repository;

import model.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository {

    private List<Categoria> categorias = new ArrayList<>();
    private Long idSequence = 1L;

    public Categoria save(Categoria categoria) {
        if (categoria.getId() != null) {
            throw new IllegalArgumentException("Categoria já possui ID");
        }

        for (Categoria c : categorias) {
            if (c.getNome().equalsIgnoreCase(categoria.getNome())) {
                throw new IllegalArgumentException("Já existe uma categoria com esse nome.");
            }
        }

        categoria.setId(idSequence++);
        categorias.add(categoria);
        return categoria;
    }

    public Categoria findById(Long id){
        for (Categoria categoria : categorias){
            if(categoria.getId().equals(id)){
                return categoria;
            }
        }
        return null;
    }

    public List<Categoria> findAll(){
        return new ArrayList<>(categorias);
    }

    public void deleteById(Long id){
        for(int i = 0; i < categorias.size(); i++){
            if(categorias.get(i).getId().equals(id)){
                categorias.remove(i);
                break;
            }
        }
    }

    public Categoria updateById(Categoria categoria){
        if(categoria.getId() == null){
            throw new IllegalArgumentException("Categoria não pode ser atualizada sem ID");
        }

        for (int i = 0; i < categorias.size(); i++){
            if(categorias.get(i).getId().equals(categoria.getId())){
                categorias.set(i, categoria);
                return categoria;
            }
        }

        throw new IllegalArgumentException("Categoria não encontrada para atualização");
    }


}

