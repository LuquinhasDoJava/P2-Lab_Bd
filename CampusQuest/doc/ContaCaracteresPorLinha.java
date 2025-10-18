import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ContaCaracteresPorLinha {

    public static void main(String[] args) {
        // Verifica se o caminho do arquivo foi fornecido como argumento
        if (args.length != 1) {
            System.out.println("Por favor, forneça o caminho do arquivo como argumento.");
            return;
        }

        Path caminhoArquivo = Path.of(args[0]);

        try {
            // Lê todas as linhas do arquivo
            List<String> linhas = Files.readAllLines(caminhoArquivo);
            
            // Para cada linha, imprime o número de caracteres
            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i);
                System.out.println("Linha " + (i + 1) + ": " + linha.length() + " caracteres");
            }
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
