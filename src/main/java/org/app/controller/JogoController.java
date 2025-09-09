package org.app.controller;

import org.app.model.Jogo;
import org.app.service.JogoService;
import org.app.config.LogManager;

import java.time.LocalDate;
import java.util.List;

public class JogoController {

    private final JogoService jogoService;

    public JogoController() {
        this.jogoService = new JogoService();
    }

    public void cadastrarJogo(String titulo, String genero, LocalDate dataLancamento, Double notaPessoal) {
        LogManager.log("Recebida solicitação para cadastrar jogo: " + titulo);
        Jogo jogo = new Jogo();
        jogo.setTitulo(titulo);
        jogo.setGenero(genero);
        jogo.setDataLancamento(dataLancamento);
        jogo.setNotaPessoal(notaPessoal);
        jogoService.cadastrarJogo(jogo);
        LogManager.log("Jogo cadastrado via controller: " + titulo);
    }

    public List<Jogo> listarJogos() {
        LogManager.log("Recebida solicitação para listar jogos.");
        return jogoService.listarJogos();
    }

    public Jogo buscarJogoPorId(int id) {
        LogManager.log("Recebida solicitação para buscar jogo por ID: " + id);
        return jogoService.buscarJogoPorId(id);
    }

    public void atualizarJogo(int id, String titulo, String genero, LocalDate dataLancamento, Double notaPessoal) {
        LogManager.log("Recebida solicitação para atualizar jogo: ID " + id);
        Jogo jogo = new Jogo();
        jogo.setId(id);
        jogo.setTitulo(titulo);
        jogo.setGenero(genero);
        jogo.setDataLancamento(dataLancamento);
        jogo.setNotaPessoal(notaPessoal);
        jogoService.atualizarJogo(jogo);
        LogManager.log("Jogo atualizado via controller: ID " + id);
    }

    public void deletarJogo(int id) {
        LogManager.log("Recebida solicitação para deletar jogo: ID " + id);
        jogoService.deletarJogo(id);
        LogManager.log("Jogo deletado via controller: ID " + id);
    }

}
