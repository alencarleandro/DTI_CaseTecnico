package org.app.service;

import org.app.dao.JogoDAO;
import org.app.model.Jogo;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

public class JogoService {
    private final JogoDAO jogoDAO;

    public JogoService() {
        this.jogoDAO = new JogoDAO();
        this.jogoDAO.criarTabela();
    }

    public void cadastrarJogo(Jogo jogo) {
        validarJogo(jogo);
        jogoDAO.inserir(jogo);
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
    }

    public void deletarJogo(int id) {
        if (jogoDAO.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Não é possível deletar. Jogo não encontrado.");
        }
        jogoDAO.deletar(id);
    }

    private void validarJogo(Jogo jogo) {
        if (jogo.getTitulo() == null || jogo.getTitulo().isBlank()) {
            throw new IllegalArgumentException("O título do jogo é obrigatório.");
        }
        if (jogo.getGenero() == null || jogo.getGenero().isBlank()) {
            throw new IllegalArgumentException("O gênero do jogo é obrigatório.");
        }
        if (jogo.getDataLancamento() == null) {
            throw new IllegalArgumentException("A data de lançamento é obrigatória.");
        }
        if (jogo.getDataLancamento().isBefore(LocalDate.of(1970, 1, 1)) ||
                jogo.getDataLancamento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de lançamento inválida: " + jogo.getDataLancamento());
        }
        if (jogo.getNotaPessoal() != null && (jogo.getNotaPessoal() < 0 || jogo.getNotaPessoal() > 10)) {
            throw new IllegalArgumentException("A nota pessoal deve estar entre 0 e 10.");
        }
    }
}
