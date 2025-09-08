package org.app.view;

import org.app.controller.JogoController;
import org.app.model.Jogo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class JogoView {

    private final JogoController controller;
    private final Scanner scanner;
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public JogoView() {
        this.controller = new JogoController();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            try {
                switch (opcao) {
                    case 1 -> cadastrarJogo();
                    case 2 -> listarJogos();
                    case 3 -> buscarJogoPorId();
                    case 4 -> atualizarJogo();
                    case 5 -> deletarJogo();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void exibirMenu() {
        System.out.println("\n=== MENU DE JOGOS ===");
        System.out.println("1 - Cadastrar Jogo");
        System.out.println("2 - Listar Jogos");
        System.out.println("3 - Buscar Jogo por ID");
        System.out.println("4 - Atualizar Jogo");
        System.out.println("5 - Deletar Jogo");
        System.out.println("0 - Sair");
    }

    private void cadastrarJogo() {
        System.out.println("\n--- Cadastrar Jogo ---");
        String titulo = lerTexto("Título: ");
        String genero = lerTexto("Gênero: ");
        LocalDate dataLancamento = lerData("Data de lançamento (dd/MM/yyyy): ");
        Double notaPessoal = lerNota("Nota pessoal (0-10, opcional): ");
        controller.cadastrarJogo(titulo, genero, dataLancamento, notaPessoal);
    }

    private void listarJogos() {
        System.out.println("\n--- Lista de Jogos ---");
        List<Jogo> jogos = controller.listarJogos();
        if (jogos.isEmpty()) {
            System.out.println("Nenhum jogo cadastrado.");
        } else {
            jogos.forEach(System.out::println);
        }
    }

    private void buscarJogoPorId() {
        System.out.println("\n--- Buscar Jogo por ID ---");
        int id = lerInteiro("ID do jogo: ");
        Jogo jogo = controller.buscarJogoPorId(id);
        System.out.println(jogo);
    }

    private void atualizarJogo() {
        System.out.println("\n--- Atualizar Jogo ---");
        int id = lerInteiro("ID do jogo: ");
        String titulo = lerTexto("Novo título: ");
        String genero = lerTexto("Novo gênero: ");
        LocalDate dataLancamento = lerData("Nova data de lançamento (dd/MM/yyyy): ");
        Double notaPessoal = lerNota("Nova nota pessoal (0-10, opcional): ");
        controller.atualizarJogo(id, titulo, genero, dataLancamento, notaPessoal);
    }

    private void deletarJogo() {
        System.out.println("\n--- Deletar Jogo ---");
        int id = lerInteiro("ID do jogo: ");
        controller.deletarJogo(id);
    }

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private LocalDate lerData(String mensagem) {
        LocalDate data = null;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensagem);
            String input = scanner.nextLine();
            try {
                data = LocalDate.parse(input, FORMATADOR);
                valido = true;
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida! Use o formato dd/MM/yyyy.");
            }
        }
        return data;
    }

    private Double lerNota(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = scanner.nextLine();
            if (input.isBlank()) {
                return null; // valor opcional
            }
            try {
                double valor = Double.parseDouble(input);
                if (valor < 0 || valor > 10) {
                    System.out.println("Valor inválido! Deve estar entre 0 e 10.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Número inválido! Digite um valor entre 0 e 10 ou deixe em branco.");
            }
        }
    }

}
