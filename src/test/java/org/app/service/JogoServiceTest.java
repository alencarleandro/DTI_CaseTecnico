package org.app.service;

import org.app.dao.JogoDAO;
import org.app.model.Jogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JogoServiceTest {
    @Mock
    private JogoDAO jogoDAO;

    private JogoService jogoService;

    private Jogo jogoValido;

    @BeforeEach
    void setUp() {
        jogoValido = new Jogo(1, "Zelda", "Aventura", LocalDate.of(2000, 1, 1), 9.0);
        jogoService = new JogoService(jogoDAO);
    }

    @Test
    void deveCadastrarJogoComSucesso() {
        doNothing().when(jogoDAO).inserir(any(Jogo.class));
        assertDoesNotThrow(() -> jogoService.cadastrarJogo(jogoValido));
        verify(jogoDAO).inserir(jogoValido);
    }

    @Test
    void deveListarJogos() {
        when(jogoDAO.listarTodos()).thenReturn(Collections.singletonList(jogoValido));
        List<Jogo> jogos = jogoService.listarJogos();
        assertEquals(1, jogos.size());
        assertEquals("Zelda", jogos.get(0).getTitulo());
    }

    @Test
    void deveBuscarJogoPorIdComSucesso() {
        when(jogoDAO.buscarPorId(1)).thenReturn(jogoValido);
        Jogo jogo = jogoService.buscarJogoPorId(1);
        assertNotNull(jogo);
        assertEquals("Zelda", jogo.getTitulo());
    }

    @Test
    void buscarJogoPorIdNaoExistenteDeveLancarExcecao() {
        when(jogoDAO.buscarPorId(2)).thenReturn(null);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> jogoService.buscarJogoPorId(2));
        assertTrue(ex.getMessage().contains("Nenhum jogo encontrado"));
    }

    @Test
    void deveAtualizarJogoComSucesso() {
        when(jogoDAO.buscarPorId(1)).thenReturn(jogoValido);
        doNothing().when(jogoDAO).atualizar(any(Jogo.class));
        assertDoesNotThrow(() -> jogoService.atualizarJogo(jogoValido));
        verify(jogoDAO).atualizar(jogoValido);
    }

    @Test
    void atualizarJogoNaoExistenteDeveLancarExcecao() {
        when(jogoDAO.buscarPorId(1)).thenReturn(null);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> jogoService.atualizarJogo(jogoValido));
        assertTrue(ex.getMessage().contains("Jogo não encontrado"));
    }

    @Test
    void deveDeletarJogoComSucesso() {
        when(jogoDAO.buscarPorId(1)).thenReturn(jogoValido);
        doNothing().when(jogoDAO).deletar(1);
        assertDoesNotThrow(() -> jogoService.deletarJogo(1));
        verify(jogoDAO).deletar(1);
    }

    @Test
    void deletarJogoNaoExistenteDeveLancarExcecao() {
        when(jogoDAO.buscarPorId(1)).thenReturn(null);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> jogoService.deletarJogo(1));
        assertTrue(ex.getMessage().contains("Jogo não encontrado"));
    }

    @Test
    void cadastrarJogoComTituloInvalidoDeveLancarExcecao() {
        Jogo jogo = new Jogo(2, "", "Aventura", LocalDate.of(2000, 1, 1), 8.0);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> jogoService.cadastrarJogo(jogo));
        assertTrue(ex.getMessage().contains("título do jogo é obrigatório"));
    }

    @Test
    void cadastrarJogoComGeneroInvalidoDeveLancarExcecao() {
        Jogo jogo = new Jogo(2, "Mario", "", LocalDate.of(2000, 1, 1), 8.0);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> jogoService.cadastrarJogo(jogo));
        assertTrue(ex.getMessage().contains("gênero do jogo é obrigatório"));
    }

    @Test
    void cadastrarJogoComDataInvalidaDeveLancarExcecao() {
        Jogo jogo = new Jogo(2, "Mario", "Aventura", LocalDate.of(1960, 1, 1), 8.0);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> jogoService.cadastrarJogo(jogo));
        assertTrue(ex.getMessage().contains("Data de lançamento inválida"));
    }

    @Test
    void cadastrarJogoComNotaInvalidaDeveLancarExcecao() {
        Jogo jogo = new Jogo(2, "Mario", "Aventura", LocalDate.of(2000, 1, 1), 15.0);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> jogoService.cadastrarJogo(jogo));
        assertTrue(ex.getMessage().contains("nota pessoal deve estar entre 0 e 10"));
    }
}
