package br.ufscar.dc.compiladores.compilador.receitas;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;

public class Principal {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Uso: java Principal <arquivo de entrada> <arquivo de saída>");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        processarArquivo(inputFile, outputFile);
    }

    public static void processarArquivo(String inputFile, String outputFile) {
        try (InputStream is = new FileInputStream(inputFile);
             PrintWriter writer = new PrintWriter(outputFile, "UTF-8")) {

            CharStream input = CharStreams.fromStream(is);
            ReceitaLexer lexer = new ReceitaLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ReceitaParser parser = new ReceitaParser(tokens);

            CustomSyntaxErrorListener errorListener = new CustomSyntaxErrorListener(writer);
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);

            ParseTree tree = parser.receita();

            if (errorListener.hasErrors()) {
                writer.println("Erro(s) de análise sintática encontrados.");
            } else {
                writer.println("Análise sintática concluída sem erros.");
                
                // Realiza a análise semântica se a análise sintática for bem-sucedida
                AnalisadorSemantico analisadorSemantico = new AnalisadorSemantico();
                analisadorSemantico.visit(tree); // Faz a visita à árvore para a análise semântica

                // Verifica se houve erros semânticos
                if (!AnalisadorSemantico.errosSemanticos.isEmpty()) {
                    writer.println("Erro(s) de análise semântica encontrados:");
                    for (String erro : AnalisadorSemantico.errosSemanticos) {
                        writer.println(erro);
                    }
                } else {
                    writer.println("Análise semântica concluída sem erros.");
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar arquivos: " + e.getMessage());
        }
    }

    private static class CustomSyntaxErrorListener extends BaseErrorListener {
        private PrintWriter writer;
        private boolean errorOccurred = false;

        public CustomSyntaxErrorListener(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine, String msg, RecognitionException e) {
            if (!errorOccurred) {
                writer.println("Erro de sintaxe na linha " + line + ":" + charPositionInLine + " - " + msg);
                errorOccurred = true; // Para reportar apenas o primeiro erro.
            }
        }

        public boolean hasErrors() {
            return errorOccurred;
        }
    }
}

