package org.app.config;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class LogManager {
    private static final List<String> logs = new ArrayList<>();

    public static void log(String message) {
        logs.add("[LOG] " + message);
    }

    public static void logError(String message) {
        logs.add("[LOG][ERRO] " + message);
    }

    public static void salvarLogsNoArquivo() {
        try (FileWriter writer = new FileWriter("logs.txt")) {
            writer.write("==== LOGS DA EXECUÇÃO ====" + System.lineSeparator());
            for (String log : logs) {
                writer.write(log + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar logs: " + e.getMessage());
        }
    }
}
