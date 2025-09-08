package org.app.controller;

import org.app.model.Jogo;
import org.app.service.JogoService;

import java.time.LocalDate;
import java.util.List;

public class JogoController {

    private final JogoService jogoService;

    public JogoController() {
        this.jogoService = new JogoService();
    }

    public void cadastrarJogo(String titulo, String genero, LocalDate dataLancamento, Double notaPessoal) {
        Jogo jogo = new Jogo();
        jogo.setTitulo(titulo);
        jogo.setGenero(genero);
        jogo.setDataLancamento(dataLancamento);
        jogo.setNotaPessoal(notaPessoal);
        jogoService.cadastrarJogo(jogo);
    }

    public List<Jogo> listarJogos() {
        return jogoService.listarJogos();
    }

    public Jogo buscarJogoPorId(int id) {
        return jogoService.buscarJogoPorId(id);
    }

    public void atualizarJogo(int id, String titulo, String genero, LocalDate dataLancamento, Double notaPessoal) {
        Jogo jogo = new Jogo();
        jogo.setId(id);
        jogo.setTitulo(titulo);
        jogo.setGenero(genero);
        jogo.setDataLancamento(dataLancamento);
        jogo.setNotaPessoal(notaPessoal);
        jogoService.atualizarJogo(jogo);
    }

    public void deletarJogo(int id) {
        jogoService.deletarJogo(id);
    }

}
