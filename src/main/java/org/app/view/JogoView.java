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
            switch (opcao) {
                case 1 -> cadastrarJogo();
                case 2 -> listarJogos();
                case 3 -> buscarJogoPorId();
                case 4 -> atualizarJogo();
                case 5 -> deletarJogo();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
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
        System.out.println("Digite 'CANCELAR' a qualquer momento para abortar a operação.\n");

        String titulo = lerTexto("Título: ");
        if (titulo == null || titulo.isBlank()){
            System.out.println("Campo obrigatorio operação cancelada!");
            return;
        }

        String genero = lerTexto("Gênero: ");
        if (genero == null || genero.isBlank()){
            System.out.println("Campo obrigatorio operação cancelada!");
            return;
        }

        LocalDate dataLancamento = lerData("Data de lançamento (dd/MM/yyyy): ");
        if (dataLancamento == null) return;

        Double notaPessoal = lerNota("Nota pessoal (0-10, opcional): ");

        try {
            controller.cadastrarJogo(titulo, genero, dataLancamento, notaPessoal);
            System.out.println("Jogo cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar jogo: " + e.getMessage());
        }
    }

    private void atualizarJogo() {
        System.out.println("\n--- Atualizar Jogo ---");

        Integer id = lerIdExistente("ID do jogo: ");
        if (id == null) {
            System.out.println("Campo obrigatorio operação cancelada!");
            return;
        }

        String titulo = lerTexto("Novo título: ");
        if (titulo == null || titulo.isBlank()) {
            System.out.println("Campo obrigatorio operação cancelada!");
            return;
        }

        String genero = lerTexto("Novo gênero: ");
        if (genero == null || genero.isBlank()) {
            System.out.println("Campo obrigatorio operação cancelada!");
            return;
        }

        LocalDate dataLancamento = lerData("Nova data de lançamento (dd/MM/yyyy): ");
        if (dataLancamento == null) return;

        Double notaPessoal = lerNota("Nova nota pessoal (0-10, opcional): ");

        try {
            controller.atualizarJogo(id, titulo, genero, dataLancamento, notaPessoal);
            System.out.println("Jogo atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao atualizar jogo: " + e.getMessage());
        }
    }

    private void listarJogos() {
        List<Jogo> jogos = controller.listarJogos();
        if (jogos.isEmpty()) {
            System.out.println("Nenhum jogo cadastrado.");
            return;
        }

        int idWidth = jogos.stream().mapToInt(j -> String.valueOf(j.getId()).length()).max().orElse(2);
        int tituloWidth = jogos.stream().mapToInt(j -> j.getTitulo().length()).max().orElse(6);
        int generoWidth = jogos.stream().mapToInt(j -> j.getGenero().length()).max().orElse(6);
        int dataWidth = jogos.stream().mapToInt(j -> FORMATADOR.format(j.getDataLancamento()).length()).max().orElse(10);
        int notaWidth = jogos.stream()
                .mapToInt(j -> j.getNotaPessoal() != null ? j.getNotaPessoal().toString().length() : 1)
                .max()
                .orElse(1);

        System.out.println("\n--- Lista de Jogos ---");
        for (Jogo j : jogos) {
            String notaStr = j.getNotaPessoal() != null ? String.format("%.1f", j.getNotaPessoal()) : "—";
            System.out.printf(
                    "ID: %-" + idWidth + "d | Título: %-" + tituloWidth + "s | Gênero: %-" + generoWidth + "s | " +
                            "Data de Lançamento: %-" + dataWidth + "s | Nota Pessoal: %" + notaWidth + "s%n",
                    j.getId(), j.getTitulo(), j.getGenero(), FORMATADOR.format(j.getDataLancamento()), notaStr
            );
        }
    }

    private void buscarJogoPorId() {
        int id = lerInteiro("ID do jogo: ");
        try {
            Jogo jogo = controller.buscarJogoPorId(id);
            System.out.println(jogo);
        } catch (IllegalArgumentException e) {
            System.out.println("" + e.getMessage());
        }
    }

    private void deletarJogo() {
        int id = lerInteiro("ID do jogo: ");
        try {
            controller.deletarJogo(id);
            System.out.println("Jogo deletado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("CANCELAR")) {
            return null;
        }
        return input;
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
        while (true) {
            System.out.print(mensagem);
            String input = scanner.nextLine().trim();
            if (input.isBlank() || input.equalsIgnoreCase("CANCELAR")) {
                System.out.println("Campo obrigatorio operação cancelada!");
                return null;
            }
            try {
                return LocalDate.parse(input, FORMATADOR);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida! Use o formato dd/MM/yyyy.");
            }
        }
    }

    private Double lerNota(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = scanner.nextLine();
            try {
                double valor = Double.parseDouble(input);
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Número inválido! Digite um valor numérico ou deixe em branco.");
            }
        }
    }

    private Integer lerIdExistente(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                return null;
            }
            try {
                int id = Integer.parseInt(input);
                try {
                    controller.buscarJogoPorId(id);
                    return id;
                } catch (IllegalArgumentException e) {
                    System.out.println("ID não encontrado. Tente novamente ou pressione Enter para cancelar.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Número inválido! Digite um ID válido ou pressione Enter para cancelar.");
            }
        }
    }
}
