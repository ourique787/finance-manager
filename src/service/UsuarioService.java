package service;

import model.Usuario;
import repository.UsuarioRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class UsuarioService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$", Pattern.CASE_INSENSITIVE);

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public Usuario criarUsuario (String nome,String email){
        Matcher matcher = EMAIL_PATTERN.matcher(email);

        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome não pode ser vazio nem nulo!");
        }
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Email não pode estar vazio nem ser nulo!");
        }
        if(!matcher.matches()){
            throw new IllegalArgumentException("Email não possui formato válido");
        }
        for (Usuario usuario : usuarioRepository.findAll()){
            if(usuario.getEmail().equalsIgnoreCase(email)){
                throw new IllegalArgumentException("Email já cadastrado");
            }
        }
        Usuario usuario = new Usuario(nome, email);
        return usuarioRepository.save(usuario);

    }

    public Usuario buscarPorId(Long id){
        if(id == null){
            throw new IllegalArgumentException("Id nulo");
        }
        Usuario usuario = usuarioRepository.findById(id);
        if(usuario == null){
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        return usuario;
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public void removerUsuario(Long id){
        if(id == null){
            throw new IllegalArgumentException("Id inválido");
        }
        Usuario usuario = usuarioRepository.findById(id);

        if(usuario == null){
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        usuarioRepository.deleteById(id);

    }
}
