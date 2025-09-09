package org.app.service;

import org.app.dao.JogoDAO;
import org.app.model.Jogo;
import org.app.config.LogManager;

import java.time.LocalDate;
import java.util.List;

public class JogoService {
    private final JogoDAO jogoDAO;

    public JogoService() {
        this.jogoDAO = new JogoDAO();
        this.jogoDAO.criarTabela();
    }

    public JogoService(JogoDAO jogoDAO) {
        this.jogoDAO = jogoDAO;
        this.jogoDAO.criarTabela();
    }

    public void cadastrarJogo(Jogo jogo) {
        LogManager.log("Cadastrando jogo: " + jogo.getTitulo());
        validarJogo(jogo);
        jogoDAO.inserir(jogo);
        LogManager.log("Jogo cadastrado com sucesso: " + jogo.getTitulo());
    }

    public List<Jogo> listarJogos() {
        LogManager.log("Listando todos os jogos.");
        return jogoDAO.listarTodos();
    }

    public Jogo buscarJogoPorId(int id) {
        LogManager.log("Buscando jogo por ID: " + id);
        Jogo jogo = jogoDAO.buscarPorId(id);
        if (jogo == null) {
            LogManager.logError("Nenhum jogo encontrado com ID " + id);
            throw new IllegalArgumentException("Nenhum jogo encontrado com ID " + id);
        }
        return jogo;
    }

    public void atualizarJogo(Jogo jogo) {
        LogManager.log("Atualizando jogo: ID " + jogo.getId());
        validarJogo(jogo);
        if (jogoDAO.buscarPorId(jogo.getId()) == null) {
            LogManager.logError("Não é possível atualizar. Jogo não encontrado. ID: " + jogo.getId());
            throw new IllegalArgumentException("Não é possível atualizar. Jogo não encontrado.");
        }
        jogoDAO.atualizar(jogo);
        LogManager.log("Jogo atualizado com sucesso: ID " + jogo.getId());
    }

    public void deletarJogo(int id) {
        LogManager.log("Deletando jogo: ID " + id);
        if (jogoDAO.buscarPorId(id) == null) {
            LogManager.logError("Não é possível deletar. Jogo não encontrado. ID: " + id);
            throw new IllegalArgumentException("Não é possível deletar. Jogo não encontrado.");
        }
        jogoDAO.deletar(id);
        LogManager.log("Jogo deletado com sucesso: ID " + id);
    }

    private void validarJogo(Jogo jogo) {
        if (jogo.getTitulo() == null || jogo.getTitulo().isBlank()) {
            LogManager.logError("O título do jogo é obrigatório.");
            throw new IllegalArgumentException("O título do jogo é obrigatório.");
        }
        if (jogo.getGenero() == null || jogo.getGenero().isBlank()) {
            LogManager.logError("O gênero do jogo é obrigatório.");
            throw new IllegalArgumentException("O gênero do jogo é obrigatório.");
        }
        if (jogo.getDataLancamento() == null) {
            LogManager.logError("A data de lançamento é obrigatória.");
            throw new IllegalArgumentException("A data de lançamento é obrigatória.");
        }
        if (jogo.getDataLancamento().isBefore(LocalDate.of(1970, 1, 1)) ||
                jogo.getDataLancamento().isAfter(LocalDate.now())) {
            LogManager.logError("Data de lançamento inválida: " + jogo.getDataLancamento());
            throw new IllegalArgumentException("Data de lançamento inválida: " + jogo.getDataLancamento());
        }
        if (jogo.getNotaPessoal() != null && (jogo.getNotaPessoal() < 0 || jogo.getNotaPessoal() > 10)) {
            LogManager.logError("A nota pessoal deve estar entre 0 e 10.");
            throw new IllegalArgumentException("A nota pessoal deve estar entre 0 e 10.");
        }
    }
}
