package org.app.dao;

import org.app.config.ConexaoSQLite;
import org.app.model.Jogo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JogoDAO {

    public void criarTabela() {
        String sql = """
            CREATE TABLE IF NOT EXISTS jogo (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                genero TEXT NOT NULL,
                ano_lancamento INTEGER NOT NULL
            );
        """;

        try (Connection conn = ConexaoSQLite.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Jogo jogo) {
        String sql = "INSERT INTO jogo(titulo, genero, ano_lancamento) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, jogo.getTitulo());
            pstmt.setString(2, jogo.getGenero());
            pstmt.setInt(3, jogo.getAnoLancamento());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Jogo> listarTodos() {
        List<Jogo> jogos = new ArrayList<>();
        String sql = "SELECT * FROM jogo";

        try (Connection conn = ConexaoSQLite.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Jogo jogo = new Jogo();
                jogo.setId(rs.getInt("id"));
                jogo.setTitulo(rs.getString("titulo"));
                jogo.setGenero(rs.getString("genero"));
                jogo.setAnoLancamento(rs.getInt("ano_lancamento"));
                jogos.add(jogo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jogos;
    }

    public Jogo buscarPorId(int id) {
        String sql = "SELECT * FROM jogo WHERE id = ?";
        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Jogo jogo = new Jogo();
                jogo.setId(rs.getInt("id"));
                jogo.setTitulo(rs.getString("titulo"));
                jogo.setGenero(rs.getString("genero"));
                jogo.setAnoLancamento(rs.getInt("ano_lancamento"));
                return jogo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void atualizar(Jogo jogo) {
        String sql = "UPDATE jogo SET titulo=?, genero=?, ano_lancamento=? WHERE id=?";
        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, jogo.getTitulo());
            pstmt.setString(2, jogo.getGenero());
            pstmt.setInt(3, jogo.getAnoLancamento());
            pstmt.setInt(4, jogo.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM jogo WHERE id=?";
        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
