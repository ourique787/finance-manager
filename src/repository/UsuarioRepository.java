package repository;

import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private List<Usuario> usuarios = new ArrayList<>();
    private Long idSequence = 1L;

    public Usuario save(Usuario usuario){
        if (usuario.getId() == null){
            usuario.setId(idSequence++);
            usuarios.add(usuario);
        }
        return usuario;
    }

    public Usuario findById(Long id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> findAll(){
        return new ArrayList<>(usuarios);
    }

    public void deleteById(Long id){
        for (int i = 0; i < usuarios.size(); i++){
            if (usuarios.get(i).getId().equals(id)) {
                usuarios.remove(i);
                break;
            }
        }
    }

    public Usuario updateById(Usuario usuario) {
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("Usuário não pode ser atualizado sem ID.");
        }

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(usuario.getId())) {
                usuarios.set(i, usuario);
                return usuario;
            }
        }

        throw new IllegalArgumentException("Usuário não encontrado para atualização.");

    }
}


