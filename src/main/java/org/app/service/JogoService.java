package org.app.service;

import org.app.dao.JogoDAO;
import org.app.model.Jogo;

import java.time.Year;
import java.util.List;

public class JogoService {
    private final JogoDAO jogoDAO;

    public JogoService() {
        this.jogoDAO = new JogoDAO();
        this.jogoDAO.criarTabela(); // garante que a tabela exista
    }

    public void cadastrarJogo(Jogo jogo) {
        validarJogo(jogo);
        jogoDAO.inserir(jogo);
        System.out.println("Jogo cadastrado com sucesso!");
    }

    public List<Jogo> listarJogos() {
        return jogoDAO.listarTodos();
    }

    public Jogo buscarJogoPorId(int id) {
        Jogo jogo = jogoDAO.buscarPorId(id);
        if (jogo == null) {
            throw new IllegalArgumentException("Nenhum jogo encontrado com ID " + id);
        }
        return jogo;
    }

    public void atualizarJogo(Jogo jogo) {
        validarJogo(jogo);
        if (jogoDAO.buscarPorId(jogo.getId()) == null) {
            throw new IllegalArgumentException("Não é possível atualizar. Jogo não encontrado.");
        }
        jogoDAO.atualizar(jogo);
        System.out.println("Jogo atualizado com sucesso!");
    }

    public void deletarJogo(int id) {
        if (jogoDAO.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Não é possível deletar. Jogo não encontrado.");
        }
        jogoDAO.deletar(id);
        System.out.println("Jogo deletado com sucesso!");
    }

    private void validarJogo(Jogo jogo) {
        if (jogo.getTitulo() == null || jogo.getTitulo().isBlank()) {
            throw new IllegalArgumentException("O título do jogo é obrigatório.");
        }
        if (jogo.getGenero() == null || jogo.getGenero().isBlank()) {
            throw new IllegalArgumentException("O gênero do jogo é obrigatório.");
        }
        if (jogo.getAnoLancamento() <= 1970 || jogo.getAnoLancamento() > Year.now().getValue()) {
            throw new IllegalArgumentException("Ano de lançamento inválido: " + jogo.getAnoLancamento());
        }
    }
}
