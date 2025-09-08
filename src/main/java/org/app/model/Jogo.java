package org.app.model;

import java.time.LocalDate;

public class Jogo {

    private int id;
    private String titulo;
    private String genero;
    private LocalDate dataLancamento;
    private Double notaPessoal;

    public Jogo() {
    }

    public Jogo(int id, String titulo, String genero, LocalDate dataLancamento, Double notaPessoal) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.dataLancamento = dataLancamento;
        this.notaPessoal = notaPessoal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Double getNotaPessoal() {
        return notaPessoal;
    }

    public void setNotaPessoal(Double notaPessoal) {
        this.notaPessoal = notaPessoal;
    }

    @Override
    public String toString() {
        return "Jogo{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", dataLancamento=" + dataLancamento +
                ", notaPessoal=" + notaPessoal +
                '}';
    }
}
