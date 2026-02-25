package main;

import model.Categoria;
import model.TipoCategoria;
import model.Transacao;
import model.Usuario;
import repository.CategoriaRepository;
import repository.TransacaoRepository;
import repository.UsuarioRepository;
import service.CategoriaService;
import service.TransacaoService;
import service.UsuarioService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        CategoriaRepository categoriaRepository = new CategoriaRepository();
        TransacaoRepository transacaoRepository = new TransacaoRepository();

        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        CategoriaService categoriaService = new CategoriaService(categoriaRepository);
        TransacaoService transacaoService = new TransacaoService(transacaoRepository, categoriaRepository, usuarioRepository);

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;


        while (continuar == true) {
            System.out.println("----MENU FINANCE MANAGER");
            System.out.println("1 - Criar Usuário");
            System.out.println("2 - Listar Usuário ");
            System.out.println("3 - Criar Categoria");
            System.out.println("4 - Criar Transação");
            System.out.println("5 - Listar Transação");
            System.out.println("0 - Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();


            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome:");
                    String nome = scanner.nextLine();
                    System.out.println("Digite o e-mail:");
                    String email = scanner.nextLine();
                    usuarioService.criarUsuario(nome, email);
                    break;
                case 2:
                    System.out.println("1 - Listar todos");
                    System.out.println("2 - Listar por ID");
                    System.out.println("0 - Voltar");
                    int subOpcao = scanner.nextInt();
                    scanner.nextLine();
                    switch (subOpcao) {
                        case 1:
                            List<Usuario> usuarios = usuarioService.listarTodos();
                            for (Usuario usuario : usuarios) {
                                System.out.println(usuario);
                            }
                            break;
                        case 2:
                            System.out.println("Digite o ID:");
                            Long id = scanner.nextLong();
                            scanner.nextLine();
                            Usuario usuario = usuarioService.buscarPorId(id);
                            System.out.println(usuario);
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Opção inválida! Digite uma opção válida");
                    } break;
                case 3:
                    System.out.println("Digite o nome da categoria: ");
                    nome = scanner.nextLine();
                    System.out.println("Escolha o tipo da categoria: ");
                    System.out.println("1 - Receita");
                    System.out.println("2 - Despesa");
                    int tipoCategoria = scanner.nextInt();
                    if (tipoCategoria == 1) {
                        categoriaService.criarCategoria(nome, TipoCategoria.RECEITA);
                    } else if (tipoCategoria == 2) {
                        categoriaService.criarCategoria(nome, TipoCategoria.DESPESA);
                    } else {
                        throw new IllegalArgumentException("Tipo de categoria inválido!");
                    }
                    break;
                case 4:
                    System.out.println("Digite o valor da transação:");
                    BigDecimal valor = scanner.nextBigDecimal();
                    scanner.nextLine();
                    System.out.println("Digite a descrição da transação:");
                    String descricao = scanner.nextLine();
                    System.out.println("Digite o id do usuário:");
                    Long usuarioId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("Digite o id da categoria:");
                    Long categoriaId = scanner.nextLong();
                    scanner.nextLine();
                    transacaoService.criarTransacao(valor, descricao, usuarioId, categoriaId);
                    break;
                case 5:
                    System.out.println("1 - Listar todos");
                    System.out.println("2 - Listar por ID");
                    System.out.println("3 - Listar por Usuário");
                    System.out.println("4 - Listar por Categoria");
                    System.out.println("0 - Voltar");
                    subOpcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (subOpcao) {
                        case 1:
                            List<Transacao> transacoes = transacaoService.listarTodos();
                            for (Transacao transacao : transacoes) {
                                System.out.println(transacao);
                            }
                            break;
                        case 2:
                            System.out.println("Digite o id");
                            Long id = scanner.nextLong();
                            scanner.nextLine();
                            Transacao transacao = transacaoService.buscarPorId(id);
                            System.out.println(transacao);
                            break;
                        case 3:
                            System.out.println("Digite o id do usuário");
                            id = scanner.nextLong();
                            scanner.nextLine();
                            List<Transacao> transacoesUsuario = transacaoService.buscarPorUsuario(id);
                            for (Transacao transacaoUsuario : transacoesUsuario) {
                                System.out.println(transacaoUsuario);
                            }
                            break;
                        case 4:
                            System.out.println("Digite o id da categoria");
                            id = scanner.nextLong();
                            scanner.nextLine();
                            List<Transacao> transacoesCategoria = transacaoService.buscarPorCategoria(id);
                            for (Transacao transacaoCategoria : transacoesCategoria) {
                                System.out.println(transacaoCategoria);
                            }
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("Opção inválida! Digite uma opção válida");
                    }
                    break;
                case 0:
                    continuar = false;
            }


        }
    }
}