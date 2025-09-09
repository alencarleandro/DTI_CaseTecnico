package org.app;

import org.app.view.JogoView;
import org.app.config.LogManager;

public class Main {
    public static void main(String[] args) {
        new JogoView().iniciar();
        LogManager.salvarLogsNoArquivo();
    }
}