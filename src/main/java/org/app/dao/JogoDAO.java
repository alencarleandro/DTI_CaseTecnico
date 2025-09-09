package org.app.dao;

import org.app.config.ConexaoSQLite;
import org.app.config.LogManager;
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
                data_lancamento TEXT NOT NULL,
                nota_pessoal REAL
            );
        """;

        try (Connection conn = ConexaoSQLite.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            LogManager.log("Tabela 'jogo' criada ou já existe.");
        } catch (SQLException e) {
            LogManager.logError("Erro ao criar tabela 'jogo': " + e.getMessage());
        }
    }

    public void inserir(Jogo jogo) {
        String sql = "INSERT INTO jogo(titulo, genero, data_lancamento, nota_pessoal) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, jogo.getTitulo());
            pstmt.setString(2, jogo.getGenero());
            pstmt.setString(3, jogo.getDataLancamento().toString());
            if (jogo.getNotaPessoal() != null) {
                pstmt.setDouble(4, jogo.getNotaPessoal());
            } else {
                pstmt.setNull(4, Types.REAL);
            }

            pstmt.executeUpdate();
            LogManager.log("Jogo inserido: " + jogo.getTitulo());
        } catch (SQLException e) {
            LogManager.logError("Erro ao inserir jogo: " + e.getMessage());
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
                jogo.setDataLancamento(LocalDate.parse(rs.getString("data_lancamento")));
                double nota = rs.getDouble("nota_pessoal");
                if (!rs.wasNull()) {
                    jogo.setNotaPessoal(nota);
                }
                jogos.add(jogo);
            }
            LogManager.log("Listagem de jogos realizada. Total: " + jogos.size());
        } catch (SQLException e) {
            LogManager.logError("Erro ao listar jogos: " + e.getMessage());
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
                jogo.setDataLancamento(LocalDate.parse(rs.getString("data_lancamento")));
                double nota = rs.getDouble("nota_pessoal");
                if (!rs.wasNull()) {
                    jogo.setNotaPessoal(nota);
                }
                LogManager.log("Jogo encontrado com ID: " + id);
                return jogo;
            }
        } catch (SQLException e) {
            LogManager.logError("Erro ao buscar jogo por ID: " + e.getMessage());
        }
        return null;
    }

    public void atualizar(Jogo jogo) {
        String sql = "UPDATE jogo SET titulo=?, genero=?, data_lancamento=?, nota_pessoal=? WHERE id=?";
        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, jogo.getTitulo());
            pstmt.setString(2, jogo.getGenero());
            pstmt.setString(3, jogo.getDataLancamento().toString());
            if (jogo.getNotaPessoal() != null) {
                pstmt.setDouble(4, jogo.getNotaPessoal());
            } else {
                pstmt.setNull(4, Types.REAL);
            }
            pstmt.setInt(5, jogo.getId());

            pstmt.executeUpdate();
            LogManager.log("Jogo atualizado: ID " + jogo.getId());
        } catch (SQLException e) {
            LogManager.logError("Erro ao atualizar jogo: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM jogo WHERE id=?";
        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            LogManager.log("Jogo deletado: ID " + id);
        } catch (SQLException e) {
            LogManager.logError("Erro ao deletar jogo: " + e.getMessage());
        }
    }
}
